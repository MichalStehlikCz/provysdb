package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ConditionBuilder {

  /**
   * Build condition prepared by this builder.
   *
   * @return condition prepared by this builder, null if builder contains no condition
   */
  @Nullable Condition build();

  /**
   * Build condition prepared by this builder; variant returning Optional.
   *
   * @return condition prepared by this builder
   */
  default Optional<Condition> buildOpt() {
    return Optional.ofNullable(build());
  }
}
