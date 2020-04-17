package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT1;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

final class DefaultSelectBuilderT1<T1> extends
    DefaultSelectBuilderT<DefaultSelectBuilderT1<T1>> implements SelectBuilderT1<T1> {

  private final SelectColumn<T1> column1;

  DefaultSelectBuilderT1(SelectColumn<T1> column1, ElementFactory elementFactory) {
    super(elementFactory);
    this.column1 = column1;
  }

  DefaultSelectBuilderT1(SelectColumn<T1> column1, Collection<FromElement> fromElements,
      @Nullable Condition condition, ElementFactory elementFactory) {
    super(fromElements, condition, elementFactory);
    this.column1 = column1;
  }

  @Override
  protected DefaultSelectBuilderT1<T1> self() {
    return this;
  }

  @Override
  public <T2> SelectBuilderT2<T1, T2> column(SelectColumn<T2> column) {
    return new DefaultSelectBuilderT2<>(column1, column, getFromElements(), getCondition(),
        getElementFactory());
  }

  @Override
  public SelectT1<T1> build() {
    return getElementFactory().select(column1, getFromClause(), getCondition());
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    DefaultSelectBuilderT1<?> that = (DefaultSelectBuilderT1<?>) o;
    return column1.equals(that.column1);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + column1.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSelectBuilderT1{"
        + "column1=" + column1
        + ", " + super.toString() + '}';
  }
}
