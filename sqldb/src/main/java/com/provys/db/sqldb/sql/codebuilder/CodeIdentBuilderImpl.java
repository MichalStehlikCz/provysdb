package com.provys.db.sqldb.sql.codebuilder;

import com.provys.db.query.CodeIdent;
import com.provys.db.query.CodeIdentBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;

final class CodeIdentBuilderImpl implements CodeIdentBuilder {

  private @Nullable String ident = null;
  private @Nullable String firstIdent = null;
  private @Nullable CodeIdent linkedIdent = null;

  @Override
  public CodeIdentBuilder setIdent(String newIdent) {
    this.ident = newIdent;
    return this;
  }

  @Override
  public CodeIdentBuilder setFirstIdent(String newFirstIdent) {
    this.firstIdent = newFirstIdent;
    return this;
  }

  @Override
  public CodeIdentBuilder linkedIdent(CodeIdent newPreviousIdent) {
    this.linkedIdent = newPreviousIdent;
    return this;
  }

  @Override
  public CodeIdent build() {
    CodeIdent result;
    if (firstIdent == null) {
      if (ident == null) {
        result = CodeIdentVoid.getInstance();
      } else {
        result = CodeIdentSimple.of(ident);
      }
    } else {
      result = CodeIdentFirst.of(firstIdent, (ident == null) ? "" : ident);
    }
    if (linkedIdent != null) {
      return CodeIdentLinked.of(result, linkedIdent);
    }
    return result;
  }

  @Override
  public String toString() {
    return "CodeIdentBuilderImpl{"
        + "ident='" + ident + '\''
        + ", firstIdent='" + firstIdent + '\''
        + ", linkedIdent=" + linkedIdent
        + '}';
  }
}
