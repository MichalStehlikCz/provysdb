package sqlparser.impl;

import sqlparser.SqlParsedToken;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents single token parsed from SQL source. Gives access to type of token and its position in
 * original file, derived classes add additional information (like parsed text)
 */
abstract class ParsedTokenBase implements SqlParsedToken {

  private final int line;
  private final int pos;

  ParsedTokenBase(int line, int pos) {
    this.line = line;
    this.pos = pos;
  }

  @Override
  public int getLine() {
    return line;
  }

  @Override
  public int getPos() {
    return pos;
  }

  @SuppressWarnings("EqualsGetClass")
  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParsedTokenBase that = (ParsedTokenBase) o;
    return line == that.line
        && pos == that.pos;
  }

  @Override
  public int hashCode() {
    int result = line;
    result = 31 * result + pos;
    return result;
  }

  @Override
  public String toString() {
    return "ParsedTokenBase{"
        + "line=" + line
        + ", pos=" + pos
        + '}';
  }
}
