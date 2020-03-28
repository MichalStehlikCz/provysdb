package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.provys.db.sql.BindVariable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class defines mapping of Sql element classes to their MixIns for proper Jackson serialisation /
 * deserialization.
 */
public class JacksonModule extends SimpleModule {

  private static final long serialVersionUID = -3608341023608753351L;

  /**
   * Constructor for JacksonModule class - activates mix-in annotation interfaces.
   */
  @SuppressWarnings("nullness")
  public JacksonModule() {
    super("ProvysSqlElementModule");
    setMixInAnnotation(BindVariable.class, BindVariableMixIn.class);
    setMixInAnnotation(SqlSelectColumn.class, SqlSelectColumnMixIn.class);
    setMixInAnnotation(SqlFromElement.class, SqlFromElementMixIn.class);
    setMixInAnnotation(SqlExpression.class, SqlExpressionMixIn.class);
    setDeserializerModifier(new XmlBeanDeserializerModifier());
  }

  /**
   * Used to fix problem when element wrapper is reported as being in conflict with property.
   */
  private static class XmlBeanDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public List<BeanPropertyDefinition> updateProperties(DeserializationConfig config,
        BeanDescription beanDesc, List<BeanPropertyDefinition> propDefs) {
      if (beanDesc.getBeanClass() == SqlFunction.class) {
        return propDefs.stream().filter(p -> p.getConstructorParameter() != null).collect(
            Collectors.toList());
      }
      return super.updateProperties(config, beanDesc, propDefs);
    }
  }
}
