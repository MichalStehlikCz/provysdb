package com.provys.db.query.functions;

final class RepeatingBuiltInFunction extends RepeatingBuiltInBase implements BuiltInFunctionInt {

  RepeatingBuiltInFunction(String name, Class<?> returnType, Class<?> secondArgumentType) {
    super(name, returnType, secondArgumentType);
  }
}
