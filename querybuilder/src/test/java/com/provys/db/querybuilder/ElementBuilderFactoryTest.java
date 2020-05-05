package com.provys.db.querybuilder;

import static org.assertj.core.api.Assertions.*;

import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.functions.ConditionalOperator;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ElementBuilderFactoryTest {

  private static final ElementFactory elementFactory = ElementFactory.getInstance();
  private static final ElementBuilderFactory factory = ElementBuilderFactory.getInstance();

  @Test
  void eqTest() {
    assertThat(factory
        .literal("test").eq(factory.bind(String.class, "bind"))
        .build())
        .isEqualTo(elementFactory.condition(
            ConditionalOperator.COND_EQ_NONNULL, List.of(elementFactory.literal("test"),
            elementFactory.bind(String.class, "bind"))));
  }
}
