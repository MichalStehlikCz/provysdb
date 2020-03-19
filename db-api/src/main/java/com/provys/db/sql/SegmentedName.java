package com.provys.db.sql;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.common.exception.InternalException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Name, built from several segments, delimited by ..
 */
@JsonSerialize(using = SegmentedNameSerializer.class)
@JsonDeserialize(using = SegmentedNameDeserializer.class)
public final class SegmentedName extends NamePathBase {

  /**
   * Create segmented name from segments.
   *
   * @param segments is collection of segments
   * @return segmented name built from supplied segments
   */
  public static SegmentedName ofSegments(Collection<SimpleName> segments) {
    return new SegmentedName(segments);
  }

  /**
   * Build segmented name from text, representing this segmented name.
   *
   * @param text is text, representing segmented name, with individual segments delimited by .
   * @return segmented name, representing supplied text
   */
  public static SegmentedName valueOf(String text) {
    if (text.charAt(text.length() - 1) == '.') {
      throw new IllegalArgumentException("Segmented name cannot end with . (" + text + ')');
    }
    var stringSegments = text.split("\\.", 0);
    if (stringSegments.length == 0) {
      throw new InternalException("Invalid parameter " + text
          + " for segmented name - no segments found");
    }
    var segments = new ArrayList<SimpleName>(stringSegments.length);
    for (var stringSegment : stringSegments) {
      segments.add(SimpleName.valueOf(stringSegment));
    }
    return ofSegments(segments);
  }

  private final List<SimpleName> segments;

  private SegmentedName(Collection<SimpleName> segments) {
    if (segments.isEmpty()) {
      throw new IllegalArgumentException("Segmented name must have at least one segment");
    }
    this.segments = List.copyOf(segments);
  }

  @Override
  public String getText() {
    var builder = new StringBuilder();
    var first = true;
    for (var segment : segments) {
      if (first) {
        first = false;
      } else {
        builder.append('.');
      }
      builder.append(segment.getText());
    }
    return builder.toString();
  }

  @Override
  public List<String> getDbNames() {
    return segments.stream().map(SimpleName::getDbName).collect(Collectors.toList());
  }

  @Override
  public void append(CodeBuilder builder) {
    for (var segment : segments) {
      builder.append(segment.getText());
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SegmentedName that = (SegmentedName) o;
    return Objects.equals(segments, that.segments);
  }

  @Override
  public int hashCode() {
    return segments != null ? segments.hashCode() : 0;
  }

  @Override
  public String toString() {
    return getText();
  }
}
