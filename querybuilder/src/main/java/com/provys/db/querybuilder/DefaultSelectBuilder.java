package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.Select;
import com.provys.db.query.elements.SelectColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultSelectBuilder extends DefaultSelectBuilderT<DefaultSelectBuilder>
    implements SelectBuilder {

  private final List<SelectColumn<?>> columns;

  DefaultSelectBuilder(Collection<? extends SelectColumn<?>> columns,
      Collection<FromElement> fromElements,
      Collection<Condition> conditions,
      ElementFactory elementFactory) {
    super(fromElements, conditions, elementFactory);
    this.columns = List.copyOf(columns);
  }

  @Override
  public DefaultSelectBuilder from(FromElement fromElement) {
    var newFromElements = new ArrayList<>(getFromElements());
    newFromElements.add(fromElement);
    return new DefaultSelectBuilder(columns, newFromElements, getConditions(), getElementFactory());
  }

  @Override
  public DefaultSelectBuilder where(Condition condition) {
    var newConditions = new ArrayList<>(getConditions());
    newConditions.add(condition);
    return new DefaultSelectBuilder(columns, getFromElements(), newConditions, getElementFactory());
  }

  /**
   * Build select statement from content of this builder.
   *
   * @return select statement built from content of this builder
   */
  @Override
  public Select build() {
    return getElementFactory().select(columns, getElementFactory().from(getFromElements()),
        getConditions());
  }
}
