package com.provys.db.sqlquery.codebuilder;

import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.query.names.NamePath;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Implementation of code builder - tool to build SQL text with formatting.
 *
 * @author stehlik
 */
public class CodeBuilderImpl implements CodeBuilder {

  private final StringBuilder builder;
  private boolean newLine = true;
  private CodeIdent currentIdent = CodeIdentVoid.getInstance();
  private final Deque<CodeIdent> tempIdents = new ArrayDeque<>(5);
  private int bindPos = 0; // position of last bind
  private final Map<BindName, BindWithPosBuilder> bindsWithPos;

  /**
   * Default constructor for CodeBuilder. Sets all fields to their default values.
   */
  public CodeBuilderImpl() {
    this.builder = new StringBuilder(100);
    this.bindsWithPos = new ConcurrentHashMap<>(10);
  }

  private void beforeAppend() {
    if (newLine) {
      currentIdent.use(builder);
      newLine = false;
    }
  }

  @Override
  public CodeBuilder append(String text) {
    beforeAppend();
    builder.append(text);
    return this;
  }

  @Override
  public CodeBuilder append(char character) {
    return append(Character.toString(character));
  }

  @Override
  public CodeBuilder append(int number) {
    return append(Integer.toString(number));
  }

  @Override
  public CodeBuilder append(BigInteger number) {
    return append(number.toString());
  }

  @Override
  public CodeBuilder append(NamePath name) {
    var first = true;
    for (var segment : name.getSegments()) {
      if (first) {
        first = false;
      } else {
        builder.append('.');
      }
      builder.append(segment.getText());
    }
    return this;
  }

  @Override
  public CodeBuilder append(CodeBuilder appendBuilder) {
    appendWrapped(appendBuilder.build());
    addBindsWithPos(appendBuilder.getBindsWithPos());
    return this;
  }

  @Override
  public CodeBuilder apply(Consumer<? super CodeBuilder> appendFunction) {
    appendFunction.accept(this);
    return this;
  }

  @Override
  public CodeBuilder applyString(Consumer<? super StringBuilder> appendFunction) {
    var tempBuilder = new StringBuilder();
    appendFunction.accept(tempBuilder);
    beforeAppend();
    builder.append(tempBuilder);
    return this;
  }

  @Override
  public CodeBuilder appendWrapped(String text, int additionalIdent) {
    if (additionalIdent < -currentIdent.get().length()) {
      throw new IllegalArgumentException(
          "Negative ident for block cannot be bigger than current ident level");
    }
    boolean first = true;
    String tempIdent = null; // temporary ident; only used on second and consecutive lines
    for (var line : text.split("\n", -1)) {
      if (first) {
        first = false;
      } else {
        if (tempIdent == null) {
          var format = "%1$-" + (currentIdent.get().length() + additionalIdent + 1) + 's';
          tempIdent = String.format(format, '\n');
        }
        append(tempIdent);
      }
      append(line);
    }
    if (text.charAt(text.length() - 1) == '\n') {
      appendLine();
    }
    return this;
  }

  @Override
  public CodeBuilder appendWrapped(String text) {
    return appendWrapped(text, 0);
  }

  @Override
  public CodeBuilder appendLine() {
    if (this.newLine) {
      // if there is ident and line is empty, we must insert it to builder
      append("");
    }
    builder.append('\n');
    this.newLine = true;
    return this;
  }

  @Override
  public CodeBuilder appendLine(String line) {
    append(line);
    return appendLine();
  }

  @Override
  public CodeIdentBuilder identBuilder() {
    return new CodeIdentBuilderImpl();
  }

  @Override
  public CodeBuilder setIdent(CodeIdent ident) {
    tempIdents.add(currentIdent);
    // we create a copy of supplied ident to make sure it will not be changed externally
    currentIdent = ident.copy();
    return this;
  }

  @Override
  public CodeBuilder setIdent(String ident) {
    return setIdent(CodeIdentSimple.of(ident));
  }

  @Override
  public CodeBuilder setIdent(String ident, int chars) {
    if (chars < ident.length()) {
      throw new IllegalArgumentException(
          "Ident length cannot be smaller than length of supplied ident prefix");
    }
    var format = "%1$" + chars + 's';
    return setIdent(String.format(format, ident));
  }

  @Override
  public CodeBuilder setIdent(String firstIdent, String ident) {
    return setIdent(CodeIdentFirst.of(firstIdent, ident));
  }

  @Override
  public CodeBuilder setIdent(String firstIdent, String ident, int chars) {
    if (chars < firstIdent.length()) {
      throw new IllegalArgumentException(
          "Ident length cannot be smaller than length of supplied first line "
              + "ident prefix");
    }
    if (chars < ident.length()) {
      throw new IllegalArgumentException(
          "Ident length cannot be smaller than length of supplied ident prefix");
    }
    var format = "%1$" + chars + 's';
    return setIdent(String.format(format, firstIdent), String.format(format, ident));
  }

  @Override
  public CodeBuilder increasedIdent(int increaseBy) {
    return increasedIdent("", increaseBy);
  }

  @Override
  public CodeBuilder increasedIdent(String ident, int increaseBy) {
    return setIdent(ident, this.currentIdent.get().length() + increaseBy);
  }

  @Override
  public CodeBuilder increasedIdent(String firstIdent, String ident, int increaseBy) {
    return setIdent(firstIdent, ident, this.currentIdent.get().length() + increaseBy);
  }

  @Override
  public CodeBuilder popIdent() {
    this.currentIdent = tempIdents.pop();
    return this;
  }

  @Override
  public CodeBuilder addBind(BindVariable bind) {
    var bindWithPos = bindsWithPos
        .computeIfAbsent(bind.getName(), key -> new BindWithPosBuilder(bind));
    bindWithPos.addPosition(++bindPos)
        .combineVariable(bind);
    return this;
  }

  @Override
  public CodeBuilder addBinds(Collection<? extends BindVariable> binds) {
    for (var bind : binds) {
      addBind(bind);
    }
    return this;
  }

  @Override
  public CodeBuilder addBindsWithPos(Collection<BindWithPos> binds) {
    int count = 0;
    for (var bind : binds) {
      var bindWithPos = bindsWithPos.computeIfAbsent(bind.getName(),
          key -> new BindWithPosBuilder(bind.getName(), bind.getType()));
      for (var position : bind.getPositions()) {
        bindWithPos.addPosition(position + bindPos);
        count++;
      }
    }
    bindPos += count;
    return this;
  }

  /**
   * Build Sql string from code builder.
   *
   * @return String that has been built
   */
  @Override
  public String build() {
    return builder.toString();
  }

  @Override
  public Collection<BindWithPos> getBindsWithPos() {
    return bindsWithPos.values().stream()
        .map(BindWithPosBuilder::build)
        .collect(Collectors.toList());
  }

  /**
   * Retrieve list of values, assigned to bind variables during build.
   *
   * @return list of values, assigned to bind variables during build
   */
  @Override
  public Map<BindName, Object> getBindValues() {
    return bindsWithPos.entrySet().stream()
        .filter(entry -> (entry.getValue() != null) && (entry.getValue().getValue() != null))
        .map(entry -> Map.entry(entry.getKey(), entry.getValue().getValue()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  @Override
  public String toString() {
    return "CodeBuilderImpl{"
        + "builder=" + builder
        + ", newLine=" + newLine
        + ", currentIdent=" + currentIdent
        + ", tempIdents=" + tempIdents
        + '}';
  }
}
