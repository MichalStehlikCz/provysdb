package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.Select;
import com.provys.db.query.elements.SelectColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

final class DefaultSelectBuilder extends DefaultSelectBuilderT<DefaultSelectBuilder>
    implements SelectBuilder {

  private final List<SelectColumn<?>> columns;

  DefaultSelectBuilder(ElementFactory elementFactory) {
    super(elementFactory);
    this.columns = new ArrayList<>(5);
  }

  DefaultSelectBuilder(Collection<? extends SelectColumn<?>> columns,
      Collection<FromElement> fromElements,
      @Nullable Condition condition,
      ElementFactory elementFactory) {
    super(fromElements, condition, elementFactory);
    this.columns = new ArrayList<>(columns);
  }

  @Override
  protected DefaultSelectBuilder self() {
    return this;
  }

  @Override
  public <T1> SelectBuilder column(SelectColumn<T1> column) {
    columns.add(column);
    return self();
  }

  @Override
  public Select build() {
    return getElementFactory().select(columns, getFromClause(), getConditionBuilder().build());
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
    DefaultSelectBuilder that = (DefaultSelectBuilder) o;
    return columns.equals(that.columns);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + columns.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSelectBuilder{"
        + "columns=" + columns
        + ", " + super.toString() + '}';
  }
}
