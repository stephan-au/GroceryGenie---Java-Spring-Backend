package org.assertj;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class Assertions {

  /**
   * Creates a new instance of <code>{@link com.yougrocery.yougrocery.models.GrocerylistAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.yougrocery.yougrocery.models.GrocerylistAssert assertThat(com.yougrocery.yougrocery.models.Grocerylist actual) {
    return new com.yougrocery.yougrocery.models.GrocerylistAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.yougrocery.yougrocery.models.ProductAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.yougrocery.yougrocery.models.ProductAssert assertThat(com.yougrocery.yougrocery.models.Product actual) {
    return new com.yougrocery.yougrocery.models.ProductAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.yougrocery.yougrocery.models.ProductOnGrocerylistAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.yougrocery.yougrocery.models.ProductOnGrocerylistAssert assertThat(com.yougrocery.yougrocery.models.ProductOnGrocerylist actual) {
    return new com.yougrocery.yougrocery.models.ProductOnGrocerylistAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.yougrocery.yougrocery.models.ProductOnGrocerylistProductOnGrocerylistBuilderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.yougrocery.yougrocery.models.ProductOnGrocerylistProductOnGrocerylistBuilderAssert assertThat(com.yougrocery.yougrocery.models.ProductOnGrocerylist.ProductOnGrocerylistBuilder actual) {
    return new com.yougrocery.yougrocery.models.ProductOnGrocerylistProductOnGrocerylistBuilderAssert(actual);
  }

  /**
   * Creates a new <code>{@link Assertions}</code>.
   */
  protected Assertions() {
    // empty
  }
}
