package sqlparser;

/**
 * Interface represents Sql token - name, symbol, ...
 */
public interface SqlToken {

  /**
   * Token type.
   *
   * @return token type
   */
  SqlTokenType getTokenType();

  /**
   * Defines if space before is needed.
   *
   * @return if space before is needed
   */
  SpaceMode spaceBefore();

  /**
   * Defines if space after is needed.
   *
   * @return if space after is needed
   */
  SpaceMode spaceAfter();
}
