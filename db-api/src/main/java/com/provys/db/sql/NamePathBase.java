package com.provys.db.sql;

/**
 * Implements common methods that can have default implementation.
 */
abstract class NamePathBase implements NamePath {

  @Override
  public boolean match(NamePath other) {
    var thisDbNames = getDbNames();
    var otherDbNames = other.getDbNames();
    // we cannot match others string if it is longer than ours
    if (thisDbNames.size() < otherDbNames.size()) {
      return false;
    }
    var thisIndex = thisDbNames.size() - 1;
    var otherIndex = otherDbNames.size() - 1;
    while (otherIndex >= 0) {
      if (!otherDbNames.get(otherIndex--).equals(thisDbNames.get(thisIndex--))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void append(CodeBuilder builder) {
    builder.append(getText());
  }

  @Override
  public String toString() {
    return getText();
  }
}
