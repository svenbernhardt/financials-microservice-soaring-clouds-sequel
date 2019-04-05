/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.soaringclouds.avro.shoppingCartItem.v1;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ShoppingCartItem extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -4243512789019597665L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ShoppingCartItem\",\"namespace\":\"com.soaringclouds.avro.shoppingCartItem.v1\",\"fields\":[{\"name\":\"sessionId\",\"type\":\"string\"},{\"name\":\"customerId\",\"type\":\"string\",\"doc\":\"the unique identifier of the a Customer\"},{\"name\":\"quantity\",\"type\":\"int\"},{\"name\":\"priceInCurrency\",\"type\":\"double\"},{\"name\":\"currency\",\"type\":{\"type\":\"enum\",\"name\":\"CurrencyEnum\",\"doc\":\"Valid currencies\",\"symbols\":[\"USD\",\"GBP\",\"EUR\"]},\"doc\":\"the currency used\"},{\"name\":\"product\",\"type\":{\"type\":\"record\",\"name\":\"Product\",\"fields\":[{\"name\":\"productId\",\"type\":\"string\"},{\"name\":\"productCode\",\"type\":[\"string\",\"null\"],\"default\":\"\"},{\"name\":\"productName\",\"type\":[\"string\",\"null\"],\"default\":\"\"},{\"name\":\"description\",\"type\":[\"string\",\"null\"],\"default\":\"\"},{\"name\":\"imageUrl\",\"type\":[\"string\",\"null\"],\"default\":\"\"},{\"name\":\"price\",\"type\":[\"double\",\"null\"]},{\"name\":\"size\",\"type\":[\"int\",\"null\"]},{\"name\":\"weight\",\"type\":[\"double\",\"null\"]},{\"name\":\"categories\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"tags\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"dimension\",\"type\":{\"type\":\"record\",\"name\":\"Dimension\",\"fields\":[{\"name\":\"unit\",\"type\":[\"string\",\"null\"]},{\"name\":\"length\",\"type\":[\"double\",\"null\"]},{\"name\":\"height\",\"type\":[\"double\",\"null\"]},{\"name\":\"width\",\"type\":[\"double\",\"null\"]}]}},{\"name\":\"color\",\"type\":[\"string\",\"null\"]}]}}],\"description\":\"the representation of a product added to the Shopping Cart\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<ShoppingCartItem> ENCODER =
      new BinaryMessageEncoder<ShoppingCartItem>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<ShoppingCartItem> DECODER =
      new BinaryMessageDecoder<ShoppingCartItem>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<ShoppingCartItem> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<ShoppingCartItem> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<ShoppingCartItem>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this ShoppingCartItem to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a ShoppingCartItem from a ByteBuffer. */
  public static ShoppingCartItem fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence sessionId;
  /** the unique identifier of the a Customer */
  @Deprecated public java.lang.CharSequence customerId;
  @Deprecated public int quantity;
  @Deprecated public double priceInCurrency;
  /** the currency used */
  @Deprecated public com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum currency;
  @Deprecated public com.soaringclouds.avro.shoppingCartItem.v1.Product product;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public ShoppingCartItem() {}

  /**
   * All-args constructor.
   * @param sessionId The new value for sessionId
   * @param customerId the unique identifier of the a Customer
   * @param quantity The new value for quantity
   * @param priceInCurrency The new value for priceInCurrency
   * @param currency the currency used
   * @param product The new value for product
   */
  public ShoppingCartItem(java.lang.CharSequence sessionId, java.lang.CharSequence customerId, java.lang.Integer quantity, java.lang.Double priceInCurrency, com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum currency, com.soaringclouds.avro.shoppingCartItem.v1.Product product) {
    this.sessionId = sessionId;
    this.customerId = customerId;
    this.quantity = quantity;
    this.priceInCurrency = priceInCurrency;
    this.currency = currency;
    this.product = product;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return sessionId;
    case 1: return customerId;
    case 2: return quantity;
    case 3: return priceInCurrency;
    case 4: return currency;
    case 5: return product;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: sessionId = (java.lang.CharSequence)value$; break;
    case 1: customerId = (java.lang.CharSequence)value$; break;
    case 2: quantity = (java.lang.Integer)value$; break;
    case 3: priceInCurrency = (java.lang.Double)value$; break;
    case 4: currency = (com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum)value$; break;
    case 5: product = (com.soaringclouds.avro.shoppingCartItem.v1.Product)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'sessionId' field.
   * @return The value of the 'sessionId' field.
   */
  public java.lang.CharSequence getSessionId() {
    return sessionId;
  }

  /**
   * Sets the value of the 'sessionId' field.
   * @param value the value to set.
   */
  public void setSessionId(java.lang.CharSequence value) {
    this.sessionId = value;
  }

  /**
   * Gets the value of the 'customerId' field.
   * @return the unique identifier of the a Customer
   */
  public java.lang.CharSequence getCustomerId() {
    return customerId;
  }

  /**
   * Sets the value of the 'customerId' field.
   * the unique identifier of the a Customer
   * @param value the value to set.
   */
  public void setCustomerId(java.lang.CharSequence value) {
    this.customerId = value;
  }

  /**
   * Gets the value of the 'quantity' field.
   * @return The value of the 'quantity' field.
   */
  public java.lang.Integer getQuantity() {
    return quantity;
  }

  /**
   * Sets the value of the 'quantity' field.
   * @param value the value to set.
   */
  public void setQuantity(java.lang.Integer value) {
    this.quantity = value;
  }

  /**
   * Gets the value of the 'priceInCurrency' field.
   * @return The value of the 'priceInCurrency' field.
   */
  public java.lang.Double getPriceInCurrency() {
    return priceInCurrency;
  }

  /**
   * Sets the value of the 'priceInCurrency' field.
   * @param value the value to set.
   */
  public void setPriceInCurrency(java.lang.Double value) {
    this.priceInCurrency = value;
  }

  /**
   * Gets the value of the 'currency' field.
   * @return the currency used
   */
  public com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum getCurrency() {
    return currency;
  }

  /**
   * Sets the value of the 'currency' field.
   * the currency used
   * @param value the value to set.
   */
  public void setCurrency(com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum value) {
    this.currency = value;
  }

  /**
   * Gets the value of the 'product' field.
   * @return The value of the 'product' field.
   */
  public com.soaringclouds.avro.shoppingCartItem.v1.Product getProduct() {
    return product;
  }

  /**
   * Sets the value of the 'product' field.
   * @param value the value to set.
   */
  public void setProduct(com.soaringclouds.avro.shoppingCartItem.v1.Product value) {
    this.product = value;
  }

  /**
   * Creates a new ShoppingCartItem RecordBuilder.
   * @return A new ShoppingCartItem RecordBuilder
   */
  public static com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder newBuilder() {
    return new com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder();
  }

  /**
   * Creates a new ShoppingCartItem RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new ShoppingCartItem RecordBuilder
   */
  public static com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder newBuilder(com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder other) {
    return new com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder(other);
  }

  /**
   * Creates a new ShoppingCartItem RecordBuilder by copying an existing ShoppingCartItem instance.
   * @param other The existing instance to copy.
   * @return A new ShoppingCartItem RecordBuilder
   */
  public static com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder newBuilder(com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem other) {
    return new com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder(other);
  }

  /**
   * RecordBuilder for ShoppingCartItem instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ShoppingCartItem>
    implements org.apache.avro.data.RecordBuilder<ShoppingCartItem> {

    private java.lang.CharSequence sessionId;
    /** the unique identifier of the a Customer */
    private java.lang.CharSequence customerId;
    private int quantity;
    private double priceInCurrency;
    /** the currency used */
    private com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum currency;
    private com.soaringclouds.avro.shoppingCartItem.v1.Product product;
    private com.soaringclouds.avro.shoppingCartItem.v1.Product.Builder productBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.sessionId)) {
        this.sessionId = data().deepCopy(fields()[0].schema(), other.sessionId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.customerId)) {
        this.customerId = data().deepCopy(fields()[1].schema(), other.customerId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.quantity)) {
        this.quantity = data().deepCopy(fields()[2].schema(), other.quantity);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.priceInCurrency)) {
        this.priceInCurrency = data().deepCopy(fields()[3].schema(), other.priceInCurrency);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.currency)) {
        this.currency = data().deepCopy(fields()[4].schema(), other.currency);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.product)) {
        this.product = data().deepCopy(fields()[5].schema(), other.product);
        fieldSetFlags()[5] = true;
      }
      if (other.hasProductBuilder()) {
        this.productBuilder = com.soaringclouds.avro.shoppingCartItem.v1.Product.newBuilder(other.getProductBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing ShoppingCartItem instance
     * @param other The existing instance to copy.
     */
    private Builder(com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.sessionId)) {
        this.sessionId = data().deepCopy(fields()[0].schema(), other.sessionId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.customerId)) {
        this.customerId = data().deepCopy(fields()[1].schema(), other.customerId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.quantity)) {
        this.quantity = data().deepCopy(fields()[2].schema(), other.quantity);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.priceInCurrency)) {
        this.priceInCurrency = data().deepCopy(fields()[3].schema(), other.priceInCurrency);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.currency)) {
        this.currency = data().deepCopy(fields()[4].schema(), other.currency);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.product)) {
        this.product = data().deepCopy(fields()[5].schema(), other.product);
        fieldSetFlags()[5] = true;
      }
      this.productBuilder = null;
    }

    /**
      * Gets the value of the 'sessionId' field.
      * @return The value.
      */
    public java.lang.CharSequence getSessionId() {
      return sessionId;
    }

    /**
      * Sets the value of the 'sessionId' field.
      * @param value The value of 'sessionId'.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder setSessionId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.sessionId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'sessionId' field has been set.
      * @return True if the 'sessionId' field has been set, false otherwise.
      */
    public boolean hasSessionId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'sessionId' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder clearSessionId() {
      sessionId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'customerId' field.
      * the unique identifier of the a Customer
      * @return The value.
      */
    public java.lang.CharSequence getCustomerId() {
      return customerId;
    }

    /**
      * Sets the value of the 'customerId' field.
      * the unique identifier of the a Customer
      * @param value The value of 'customerId'.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder setCustomerId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.customerId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'customerId' field has been set.
      * the unique identifier of the a Customer
      * @return True if the 'customerId' field has been set, false otherwise.
      */
    public boolean hasCustomerId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'customerId' field.
      * the unique identifier of the a Customer
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder clearCustomerId() {
      customerId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'quantity' field.
      * @return The value.
      */
    public java.lang.Integer getQuantity() {
      return quantity;
    }

    /**
      * Sets the value of the 'quantity' field.
      * @param value The value of 'quantity'.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder setQuantity(int value) {
      validate(fields()[2], value);
      this.quantity = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'quantity' field has been set.
      * @return True if the 'quantity' field has been set, false otherwise.
      */
    public boolean hasQuantity() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'quantity' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder clearQuantity() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'priceInCurrency' field.
      * @return The value.
      */
    public java.lang.Double getPriceInCurrency() {
      return priceInCurrency;
    }

    /**
      * Sets the value of the 'priceInCurrency' field.
      * @param value The value of 'priceInCurrency'.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder setPriceInCurrency(double value) {
      validate(fields()[3], value);
      this.priceInCurrency = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'priceInCurrency' field has been set.
      * @return True if the 'priceInCurrency' field has been set, false otherwise.
      */
    public boolean hasPriceInCurrency() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'priceInCurrency' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder clearPriceInCurrency() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'currency' field.
      * the currency used
      * @return The value.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum getCurrency() {
      return currency;
    }

    /**
      * Sets the value of the 'currency' field.
      * the currency used
      * @param value The value of 'currency'.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder setCurrency(com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum value) {
      validate(fields()[4], value);
      this.currency = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'currency' field has been set.
      * the currency used
      * @return True if the 'currency' field has been set, false otherwise.
      */
    public boolean hasCurrency() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'currency' field.
      * the currency used
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder clearCurrency() {
      currency = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'product' field.
      * @return The value.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.Product getProduct() {
      return product;
    }

    /**
      * Sets the value of the 'product' field.
      * @param value The value of 'product'.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder setProduct(com.soaringclouds.avro.shoppingCartItem.v1.Product value) {
      validate(fields()[5], value);
      this.productBuilder = null;
      this.product = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'product' field has been set.
      * @return True if the 'product' field has been set, false otherwise.
      */
    public boolean hasProduct() {
      return fieldSetFlags()[5];
    }

    /**
     * Gets the Builder instance for the 'product' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.soaringclouds.avro.shoppingCartItem.v1.Product.Builder getProductBuilder() {
      if (productBuilder == null) {
        if (hasProduct()) {
          setProductBuilder(com.soaringclouds.avro.shoppingCartItem.v1.Product.newBuilder(product));
        } else {
          setProductBuilder(com.soaringclouds.avro.shoppingCartItem.v1.Product.newBuilder());
        }
      }
      return productBuilder;
    }

    /**
     * Sets the Builder instance for the 'product' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder setProductBuilder(com.soaringclouds.avro.shoppingCartItem.v1.Product.Builder value) {
      clearProduct();
      productBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'product' field has an active Builder instance
     * @return True if the 'product' field has an active Builder instance
     */
    public boolean hasProductBuilder() {
      return productBuilder != null;
    }

    /**
      * Clears the value of the 'product' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.shoppingCartItem.v1.ShoppingCartItem.Builder clearProduct() {
      product = null;
      productBuilder = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ShoppingCartItem build() {
      try {
        ShoppingCartItem record = new ShoppingCartItem();
        record.sessionId = fieldSetFlags()[0] ? this.sessionId : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.customerId = fieldSetFlags()[1] ? this.customerId : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.quantity = fieldSetFlags()[2] ? this.quantity : (java.lang.Integer) defaultValue(fields()[2]);
        record.priceInCurrency = fieldSetFlags()[3] ? this.priceInCurrency : (java.lang.Double) defaultValue(fields()[3]);
        record.currency = fieldSetFlags()[4] ? this.currency : (com.soaringclouds.avro.shoppingCartItem.v1.CurrencyEnum) defaultValue(fields()[4]);
        if (productBuilder != null) {
          record.product = this.productBuilder.build();
        } else {
          record.product = fieldSetFlags()[5] ? this.product : (com.soaringclouds.avro.shoppingCartItem.v1.Product) defaultValue(fields()[5]);
        }
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<ShoppingCartItem>
    WRITER$ = (org.apache.avro.io.DatumWriter<ShoppingCartItem>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<ShoppingCartItem>
    READER$ = (org.apache.avro.io.DatumReader<ShoppingCartItem>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
