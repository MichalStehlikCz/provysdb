package com.provys.db.querybuilder;

import static org.assertj.core.api.Assertions.*;

import com.provys.db.query.elements.QueryFactory;
import org.junit.jupiter.api.Test;

public class ElementBuilderFactoryTest {

  private static final QueryFactory elementFactory = QueryFactory.getInstance();
  private static final ElementBuilderFactory factory = ElementBuilderFactory.getInstance();

  @Test
  void eqTest() {
    assertThat(factory
        .literal("test").eq(factory.bind(String.class, "bind"))
        .build())
        .isEqualTo(elementFactory.eq(QueryFactory.getInstance().literal("test"),
            QueryFactory.getInstance().bind(String.class, "bind")));
  }
}
