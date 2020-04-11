package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromElement;
import java.util.ArrayList;
import java.util.Collection;

class DefaultSelectBuilderT0 extends DefaultSelectBuilderT<DefaultSelectBuilderT0>
    implements SelectBuilderT0 {

  DefaultSelectBuilderT0(Collection<FromElement> fromElements,
      Collection<Condition> conditions,
      ElementFactory elementFactory) {
    super(fromElements, conditions, elementFactory);
  }

  @Override
  public DefaultSelectBuilderT0 from(FromElement fromElement) {
    var newFromElements = new ArrayList<>(getFromElements());
    newFromElements.add(fromElement);
    return new DefaultSelectBuilderT0(newFromElements, getConditions(), getElementFactory());
  }

  @Override
  public DefaultSelectBuilderT0 where(Condition condition) {
    var newConditions = new ArrayList<>(getConditions());
    newConditions.add(condition);
    return new DefaultSelectBuilderT0(getFromElements(), newConditions, getElementFactory());
  }
}
