package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectColumn;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

final class DefaultSelectBuilderT0 extends DefaultSelectBuilderT<DefaultSelectBuilderT0>
    implements SelectBuilderT0 {

  DefaultSelectBuilderT0(ElementFactory elementFactory) {
    super(elementFactory);
  }

  DefaultSelectBuilderT0(Collection<FromElement> fromElements,
      @Nullable Condition condition,
      ElementFactory elementFactory) {
    super(fromElements, condition, elementFactory);
  }

  @Override
  protected DefaultSelectBuilderT0 self() {
    return this;
  }

  // method is needed here to make it public and implement interface
  @Override
  public void appendBinds(BindVariableCombiner combiner) {
    super.appendBinds(combiner);
  }

  @Override
  public <T1> SelectBuilderT1<T1> column(SelectColumn<T1> column) {
    return new DefaultSelectBuilderT1<>(column, getFromElements(), getCondition(),
        getElementFactory());
  }

  @Override
  public String toString() {
    return "DefaultSelectBuilderT0{" + super.toString() + '}';
  }
}
