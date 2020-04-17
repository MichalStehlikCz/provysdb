package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT2;
import java.util.ArrayList;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

final class DefaultSelectBuilderT2<T1, T2> extends
    DefaultSelectBuilderT<DefaultSelectBuilderT2<T1, T2>> implements SelectBuilderT2<T1, T2> {

  private final SelectColumn<T1> column1;
  private final SelectColumn<T2> column2;

  DefaultSelectBuilderT2(SelectColumn<T1> column1, SelectColumn<T2> column2,
      ElementFactory elementFactory) {
    super(elementFactory);
    this.column1 = column1;
    this.column2 = column2;
  }

  DefaultSelectBuilderT2(SelectColumn<T1> column1, SelectColumn<T2> column2,
      Collection<FromElement> fromElements,
      @Nullable Condition condition,
      ElementFactory elementFactory) {
    super(fromElements, condition, elementFactory);
    this.column1 = column1;
    this.column2 = column2;
  }

  @Override
  protected DefaultSelectBuilderT2<T1, T2> self() {
    return this;
  }

  @Override
  public <T3> SelectBuilder column(SelectColumn<T3> column) {
    var columns = new ArrayList<SelectColumn<?>>(3);
    columns.add(column1);
    columns.add(column2);
    columns.add(column);
    return new DefaultSelectBuilder(columns, getFromElements(), getCondition(),
        getElementFactory());
  }

  @Override
  public SelectT2<T1, T2> build() {
    return getElementFactory().select(column1, column2, getFromClause(), getCondition());
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
    DefaultSelectBuilderT2<?, ?> that = (DefaultSelectBuilderT2<?, ?>) o;
    return column1.equals(that.column1)
        && column2.equals(that.column2);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + column1.hashCode();
    result = 31 * result + column2.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSelectBuilderT2{"
        + "column1=" + column1
        + ", column2=" + column2
        + ", " + super.toString() + '}';
  }
}
