package com.provys.db.querybuilder;

import static org.assertj.core.api.Assertions.*;

import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;

// we intentionally suppress immutable warnings to make construction of test cases easier
@SuppressWarnings("Immutable")
class BindVariableCombinerTest {

  @Test
  void combineEmpty() {
    var bind1 = new BindVariable(BindName.valueOf("name"));
    var bind2 = new BindVariable(BindName.valueOf("name"));
    var combiner = new BindVariableCombiner()
        .add(bind1)
        .add(bind2);
    assertThat(combiner.getBindMap()).isEqualTo(new BindMap(List.of(bind1)));
  }

  @Test
  void combineEmptyWithTyped() {
    var bind1 = new BindVariable(BindName.valueOf("name"));
    var bind2 = new BindVariable(BindName.valueOf("name"), Integer.class, null);
    var combiner = new BindVariableCombiner()
        .add(bind1)
        .add(bind2);
    assertThat(combiner.getBindMap()).isEqualTo(new BindMap(List.of(bind2)));
  }

  @Test
  void combineTypedWithEmpty() {
    var bind1 = new BindVariable(BindName.valueOf("name"), Integer.class, null);
    var bind2 = new BindVariable(BindName.valueOf("name"));
    var combiner = new BindVariableCombiner()
        .add(bind1)
        .add(bind2);
    assertThat(combiner.getBindMap()).isEqualTo(new BindMap(List.of(bind1)));
  }

  @Test
  void combineSpecificWithValue() {
    var bind1 = new BindVariable(BindName.valueOf("name"), BigInteger.class, BigInteger.ONE);
    var bind2 = new BindVariable(BindName.valueOf("name"), Integer.class, 1);
    var combiner = new BindVariableCombiner()
        .add(bind1)
        .add(bind2);
    assertThat(combiner.getBindMap())
        .isEqualTo(new BindMap(List.of(
            new BindVariable(BindName.valueOf("name"), Integer.class, 1))));
  }

  @Test
  void combineValueWithSpecific() {
    var bind1 = new BindVariable(BindName.valueOf("name"), Double.class, null);
    var bind2 = new BindVariable(BindName.valueOf("name"), BigDecimal.class, BigDecimal.ONE);
    var combiner = new BindVariableCombiner()
        .add(bind1)
        .add(bind2);
    assertThat(combiner.getBindMap())
        .isEqualTo(new BindMap(List.of(
            new BindVariable(BindName.valueOf("name"), Double.class, 1.0))));
  }

  @Test
  void combineFailValue() {
    var bind1 = new BindVariable(BindName.valueOf("name"), Integer.class, null);
    var bind2 = new BindVariable(BindName.valueOf("name"), BigInteger.class,
        BigInteger.valueOf(125246784687456L));
    var combiner = new BindVariableCombiner()
        .add(bind1);
    assertThatThrownBy(() -> combiner.add(bind2))
        .hasMessageFindingMatch("cannot retype");
  }

  @Test
  void combineFailType() {
    var bind1 = new BindVariable(BindName.valueOf("name"), String.class, null);
    var bind2 = new BindVariable(BindName.valueOf("name"), Double.class, null);
    var combiner = new BindVariableCombiner()
        .add(bind1);
    assertThatThrownBy(() -> combiner.add(bind2))
        .hasMessageFindingMatch("types.*are not compatible");
  }

  @Test
  void combineFailValues() {
    var bind1 = new BindVariable(BindName.valueOf("name"), String.class, "a");
    var bind2 = new BindVariable(BindName.valueOf("name"), String.class, "b");
    var combiner = new BindVariableCombiner()
        .add(bind1);
    assertThatThrownBy(() -> combiner.add(bind2))
        .hasMessageFindingMatch("values.*are not compatible");
  }
}