package org.assertj;

/**
 * Entry point for BDD assertions of different data types.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class BddAssertions {

  /**
   * Creates a new instance of <code>{@link com.yougrocery.yougrocery.models.GrocerylistAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.yougrocery.yougrocery.models.GrocerylistAssert then(com.yougrocery.yougrocery.models.Grocerylist actual) {
    return new com.yougrocery.yougrocery.models.GrocerylistAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.yougrocery.yougrocery.models.ProductAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.yougrocery.yougrocery.models.ProductAssert then(com.yougrocery.yougrocery.models.Product actual) {
    return new com.yougrocery.yougrocery.models.ProductAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.yougrocery.yougrocery.models.ProductOnGrocerylistAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static com.yougrocery.yougrocery.models.ProductOnGrocerylistAssert then(com.yougrocery.yougrocery.models.ProductOnGrocerylist actual) {
    return new com.yougrocery.yougrocery.models.ProductOnGrocerylistAssert(actual);
  }

  /**
   * Creates a new <code>{@link BddAssertions}</code>.
   */
  protected BddAssertions() {
    // empty
  }
}
