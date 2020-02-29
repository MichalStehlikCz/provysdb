package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;
import com.provys.provysdb.sqlbuilder.CodeIdentBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;

class CodeIdentBuilderImpl implements CodeIdentBuilder {

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
