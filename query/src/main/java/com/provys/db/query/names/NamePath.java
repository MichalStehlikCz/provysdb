package com.provys.db.query.names;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.List;

/**
 * Path to database object, consisting of multiple identifiers chained together. Identifier itself
 * is special case of identifier path.
 */
@JsonSerialize(using = NamePathSerializer.class)
@JsonDeserialize(using = NamePathDeserializer.class)
@Immutable
public interface NamePath extends Serializable {

  /**
   * Textual representation of identifier(s) delimited by .
   *
   * @return textual representation of name (e.g. name itself, including delimiter)
   */
  String getText();

  /**
   * Identifies if this name is simple (has exactly one segment).
   *
   * @return true if name path has one segment, false if it has more segments
   */
  boolean isSimple();

  /**
   * List of segments this path consists of.
   *
   * @return list of segments this path consists of
   */
  List<SimpleName> getSegments();

  /**
   * List of identifiers forming this path in their base (database) form.
   *
   * @return list of identifiers forming this path in their base (database) form
   */
  List<String> getDbNames();

  /**
   * Reports if other identifier path can match this path. True if other path matches item by item
   * this path's items from the end until we run out of other identifiers.
   *
   * @param other is path we verify match against ours
   * @return true if other can be used as reference to this, false otherwise
   */
  boolean match(NamePath other);
}
