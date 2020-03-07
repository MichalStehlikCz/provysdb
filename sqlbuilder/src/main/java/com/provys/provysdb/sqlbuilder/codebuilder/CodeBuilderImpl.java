package com.provys.provysdb.sqlbuilder.codebuilder;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.CodeIdent;
import com.provys.provysdb.sqlbuilder.CodeIdentBuilder;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of code builder - tool to build SQL text with formatting.
 *
 * @author stehlik
 */
public class CodeBuilderImpl implements CodeBuilder {

  private final StringBuilder builder;
  private final List<BindName> binds = new ArrayList<>(1);
  private boolean newLine = true;
  private CodeIdent currentIdent = CodeIdentVoid.getInstance();
  private final Deque<CodeIdent> tempIdents = new ArrayDeque<>(5);

  /**
   * Default constructor for CodeBuilder. Sets all fields to their default values.
   */
  public CodeBuilderImpl() {
    this.builder = new StringBuilder(100);
  }

  @Override
  public CodeBuilder append(String text) {
    if (newLine) {
      currentIdent.use(builder);
      newLine = false;
    }
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
  public CodeBuilder append(SqlIdentifier name) {
    return append(name.getText());
  }

  @Override
  public CodeBuilder append(SqlTableAlias alias) {
    return append(alias.getAlias());
  }

  @Override
  public CodeBuilder apply(Consumer<? super CodeBuilder> appendFunction) {
    appendFunction.accept(this);
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
  public CodeBuilder addBind(BindName addBind) {
    binds.add(Objects.requireNonNull(addBind));
    return this;
  }

  @Override
  public CodeBuilder addBinds(Collection<? extends BindName> addBinds) {
    binds.addAll(addBinds);
    return this;
  }

  private static BindName replaceBindName(Map<String, ? extends BindValue> bindMap,
      BindName bindName) {
    var bindValue = bindMap.get(bindName.getName());
    return (bindValue == null) ? bindName : bindName.combine(bindValue);
  }

  @Override
  public CodeBuilder applyBindValues(Collection<? extends BindValue> bindValues) {
    final var bindMap = bindValues.stream()
        .collect(Collectors.toConcurrentMap(BindName::getName, Function.identity()));
    binds.replaceAll(bindName -> replaceBindName(bindMap, bindName));
    return this;
  }

  @Override
  public String build() {
    return this.builder.toString();
  }

  @Override
  public List<BindName> getBinds() {
    return Collections.unmodifiableList(binds);
  }

  @Override
  public String toString() {
    return "CodeBuilderImpl{"
        + "builder=" + builder
        + ", binds=" + binds
        + ", newLine=" + newLine
        + ", currentIdent=" + currentIdent
        + ", tempIdents=" + tempIdents
        + '}';
  }
}
