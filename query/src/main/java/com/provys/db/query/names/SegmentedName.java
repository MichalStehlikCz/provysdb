package com.provys.db.query.names;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.common.exception.InternalException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
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

  private static final long serialVersionUID = 361395813659206613L;

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
  public boolean isSimple() {
    return segments.size() == 1;
  }

  @Override
  public List<SimpleName> getSegments() {
    return segments;
  }

  @Override
  public List<String> getDbNames() {
    return segments.stream().map(SimpleName::getDbName).collect(Collectors.toList());
  }

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this SegmentedName
   */
  private Object writeReplace() {
    return new SerializationProxy(this);
  }

  /**
   * Should be serialized via proxy, thus no direct deserialization should occur.
   *
   * @param stream is stream from which object is to be read
   * @throws InvalidObjectException always
   */
  private void readObject(ObjectInputStream stream) throws InvalidObjectException {
    throw new InvalidObjectException("Use Serialization Proxy instead.");
  }

  private static final class SerializationProxy implements Serializable {

    private static final long serialVersionUID = 4943844360741067489L;
    private @Nullable List<SimpleName> segments;

    SerializationProxy() {
    }

    SerializationProxy(NamePath value) {
      this.segments = value.getSegments();
    }

    private Object readResolve() {
      return SegmentedName.ofSegments(Objects.requireNonNull(segments));
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
