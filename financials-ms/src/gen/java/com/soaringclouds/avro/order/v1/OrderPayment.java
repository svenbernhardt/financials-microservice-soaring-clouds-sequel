/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.soaringclouds.avro.order.v1;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class OrderPayment extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 6416275715285312882L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OrderPayment\",\"namespace\":\"com.soaringclouds.avro.order.v1\",\"fields\":[{\"name\":\"cardType\",\"type\":{\"type\":\"enum\",\"name\":\"CardTypeEnum\",\"doc\":\"Credit Card payment methods\",\"symbols\":[\"VISA_CREDIT\",\"VISA_DEBIT\",\"MASTER_CREDIT\",\"MASTER_DEBIT\",\"AMEX_CREDIT\"]},\"doc\":\"Credit card payment methods supported\"},{\"name\":\"cardNumber\",\"type\":[\"string\",\"null\"]},{\"name\":\"startYear\",\"type\":[\"int\",\"null\"]},{\"name\":\"startMonth\",\"type\":[\"int\",\"null\"]},{\"name\":\"expiryYear\",\"type\":[\"int\",\"null\"]},{\"name\":\"expiryMonth\",\"type\":[\"int\",\"null\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OrderPayment> ENCODER =
      new BinaryMessageEncoder<OrderPayment>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OrderPayment> DECODER =
      new BinaryMessageDecoder<OrderPayment>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<OrderPayment> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<OrderPayment> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OrderPayment>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this OrderPayment to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a OrderPayment from a ByteBuffer. */
  public static OrderPayment fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  /** Credit card payment methods supported */
  @Deprecated public com.soaringclouds.avro.order.v1.CardTypeEnum cardType;
  @Deprecated public java.lang.CharSequence cardNumber;
  @Deprecated public java.lang.Integer startYear;
  @Deprecated public java.lang.Integer startMonth;
  @Deprecated public java.lang.Integer expiryYear;
  @Deprecated public java.lang.Integer expiryMonth;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OrderPayment() {}

  /**
   * All-args constructor.
   * @param cardType Credit card payment methods supported
   * @param cardNumber The new value for cardNumber
   * @param startYear The new value for startYear
   * @param startMonth The new value for startMonth
   * @param expiryYear The new value for expiryYear
   * @param expiryMonth The new value for expiryMonth
   */
  public OrderPayment(com.soaringclouds.avro.order.v1.CardTypeEnum cardType, java.lang.CharSequence cardNumber, java.lang.Integer startYear, java.lang.Integer startMonth, java.lang.Integer expiryYear, java.lang.Integer expiryMonth) {
    this.cardType = cardType;
    this.cardNumber = cardNumber;
    this.startYear = startYear;
    this.startMonth = startMonth;
    this.expiryYear = expiryYear;
    this.expiryMonth = expiryMonth;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return cardType;
    case 1: return cardNumber;
    case 2: return startYear;
    case 3: return startMonth;
    case 4: return expiryYear;
    case 5: return expiryMonth;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: cardType = (com.soaringclouds.avro.order.v1.CardTypeEnum)value$; break;
    case 1: cardNumber = (java.lang.CharSequence)value$; break;
    case 2: startYear = (java.lang.Integer)value$; break;
    case 3: startMonth = (java.lang.Integer)value$; break;
    case 4: expiryYear = (java.lang.Integer)value$; break;
    case 5: expiryMonth = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'cardType' field.
   * @return Credit card payment methods supported
   */
  public com.soaringclouds.avro.order.v1.CardTypeEnum getCardType() {
    return cardType;
  }

  /**
   * Sets the value of the 'cardType' field.
   * Credit card payment methods supported
   * @param value the value to set.
   */
  public void setCardType(com.soaringclouds.avro.order.v1.CardTypeEnum value) {
    this.cardType = value;
  }

  /**
   * Gets the value of the 'cardNumber' field.
   * @return The value of the 'cardNumber' field.
   */
  public java.lang.CharSequence getCardNumber() {
    return cardNumber;
  }

  /**
   * Sets the value of the 'cardNumber' field.
   * @param value the value to set.
   */
  public void setCardNumber(java.lang.CharSequence value) {
    this.cardNumber = value;
  }

  /**
   * Gets the value of the 'startYear' field.
   * @return The value of the 'startYear' field.
   */
  public java.lang.Integer getStartYear() {
    return startYear;
  }

  /**
   * Sets the value of the 'startYear' field.
   * @param value the value to set.
   */
  public void setStartYear(java.lang.Integer value) {
    this.startYear = value;
  }

  /**
   * Gets the value of the 'startMonth' field.
   * @return The value of the 'startMonth' field.
   */
  public java.lang.Integer getStartMonth() {
    return startMonth;
  }

  /**
   * Sets the value of the 'startMonth' field.
   * @param value the value to set.
   */
  public void setStartMonth(java.lang.Integer value) {
    this.startMonth = value;
  }

  /**
   * Gets the value of the 'expiryYear' field.
   * @return The value of the 'expiryYear' field.
   */
  public java.lang.Integer getExpiryYear() {
    return expiryYear;
  }

  /**
   * Sets the value of the 'expiryYear' field.
   * @param value the value to set.
   */
  public void setExpiryYear(java.lang.Integer value) {
    this.expiryYear = value;
  }

  /**
   * Gets the value of the 'expiryMonth' field.
   * @return The value of the 'expiryMonth' field.
   */
  public java.lang.Integer getExpiryMonth() {
    return expiryMonth;
  }

  /**
   * Sets the value of the 'expiryMonth' field.
   * @param value the value to set.
   */
  public void setExpiryMonth(java.lang.Integer value) {
    this.expiryMonth = value;
  }

  /**
   * Creates a new OrderPayment RecordBuilder.
   * @return A new OrderPayment RecordBuilder
   */
  public static com.soaringclouds.avro.order.v1.OrderPayment.Builder newBuilder() {
    return new com.soaringclouds.avro.order.v1.OrderPayment.Builder();
  }

  /**
   * Creates a new OrderPayment RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OrderPayment RecordBuilder
   */
  public static com.soaringclouds.avro.order.v1.OrderPayment.Builder newBuilder(com.soaringclouds.avro.order.v1.OrderPayment.Builder other) {
    return new com.soaringclouds.avro.order.v1.OrderPayment.Builder(other);
  }

  /**
   * Creates a new OrderPayment RecordBuilder by copying an existing OrderPayment instance.
   * @param other The existing instance to copy.
   * @return A new OrderPayment RecordBuilder
   */
  public static com.soaringclouds.avro.order.v1.OrderPayment.Builder newBuilder(com.soaringclouds.avro.order.v1.OrderPayment other) {
    return new com.soaringclouds.avro.order.v1.OrderPayment.Builder(other);
  }

  /**
   * RecordBuilder for OrderPayment instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OrderPayment>
    implements org.apache.avro.data.RecordBuilder<OrderPayment> {

    /** Credit card payment methods supported */
    private com.soaringclouds.avro.order.v1.CardTypeEnum cardType;
    private java.lang.CharSequence cardNumber;
    private java.lang.Integer startYear;
    private java.lang.Integer startMonth;
    private java.lang.Integer expiryYear;
    private java.lang.Integer expiryMonth;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.soaringclouds.avro.order.v1.OrderPayment.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.cardType)) {
        this.cardType = data().deepCopy(fields()[0].schema(), other.cardType);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.cardNumber)) {
        this.cardNumber = data().deepCopy(fields()[1].schema(), other.cardNumber);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.startYear)) {
        this.startYear = data().deepCopy(fields()[2].schema(), other.startYear);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.startMonth)) {
        this.startMonth = data().deepCopy(fields()[3].schema(), other.startMonth);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.expiryYear)) {
        this.expiryYear = data().deepCopy(fields()[4].schema(), other.expiryYear);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.expiryMonth)) {
        this.expiryMonth = data().deepCopy(fields()[5].schema(), other.expiryMonth);
        fieldSetFlags()[5] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing OrderPayment instance
     * @param other The existing instance to copy.
     */
    private Builder(com.soaringclouds.avro.order.v1.OrderPayment other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.cardType)) {
        this.cardType = data().deepCopy(fields()[0].schema(), other.cardType);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.cardNumber)) {
        this.cardNumber = data().deepCopy(fields()[1].schema(), other.cardNumber);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.startYear)) {
        this.startYear = data().deepCopy(fields()[2].schema(), other.startYear);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.startMonth)) {
        this.startMonth = data().deepCopy(fields()[3].schema(), other.startMonth);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.expiryYear)) {
        this.expiryYear = data().deepCopy(fields()[4].schema(), other.expiryYear);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.expiryMonth)) {
        this.expiryMonth = data().deepCopy(fields()[5].schema(), other.expiryMonth);
        fieldSetFlags()[5] = true;
      }
    }

    /**
      * Gets the value of the 'cardType' field.
      * Credit card payment methods supported
      * @return The value.
      */
    public com.soaringclouds.avro.order.v1.CardTypeEnum getCardType() {
      return cardType;
    }

    /**
      * Sets the value of the 'cardType' field.
      * Credit card payment methods supported
      * @param value The value of 'cardType'.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder setCardType(com.soaringclouds.avro.order.v1.CardTypeEnum value) {
      validate(fields()[0], value);
      this.cardType = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'cardType' field has been set.
      * Credit card payment methods supported
      * @return True if the 'cardType' field has been set, false otherwise.
      */
    public boolean hasCardType() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'cardType' field.
      * Credit card payment methods supported
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder clearCardType() {
      cardType = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'cardNumber' field.
      * @return The value.
      */
    public java.lang.CharSequence getCardNumber() {
      return cardNumber;
    }

    /**
      * Sets the value of the 'cardNumber' field.
      * @param value The value of 'cardNumber'.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder setCardNumber(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.cardNumber = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'cardNumber' field has been set.
      * @return True if the 'cardNumber' field has been set, false otherwise.
      */
    public boolean hasCardNumber() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'cardNumber' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder clearCardNumber() {
      cardNumber = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'startYear' field.
      * @return The value.
      */
    public java.lang.Integer getStartYear() {
      return startYear;
    }

    /**
      * Sets the value of the 'startYear' field.
      * @param value The value of 'startYear'.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder setStartYear(java.lang.Integer value) {
      validate(fields()[2], value);
      this.startYear = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'startYear' field has been set.
      * @return True if the 'startYear' field has been set, false otherwise.
      */
    public boolean hasStartYear() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'startYear' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder clearStartYear() {
      startYear = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'startMonth' field.
      * @return The value.
      */
    public java.lang.Integer getStartMonth() {
      return startMonth;
    }

    /**
      * Sets the value of the 'startMonth' field.
      * @param value The value of 'startMonth'.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder setStartMonth(java.lang.Integer value) {
      validate(fields()[3], value);
      this.startMonth = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'startMonth' field has been set.
      * @return True if the 'startMonth' field has been set, false otherwise.
      */
    public boolean hasStartMonth() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'startMonth' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder clearStartMonth() {
      startMonth = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'expiryYear' field.
      * @return The value.
      */
    public java.lang.Integer getExpiryYear() {
      return expiryYear;
    }

    /**
      * Sets the value of the 'expiryYear' field.
      * @param value The value of 'expiryYear'.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder setExpiryYear(java.lang.Integer value) {
      validate(fields()[4], value);
      this.expiryYear = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'expiryYear' field has been set.
      * @return True if the 'expiryYear' field has been set, false otherwise.
      */
    public boolean hasExpiryYear() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'expiryYear' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder clearExpiryYear() {
      expiryYear = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'expiryMonth' field.
      * @return The value.
      */
    public java.lang.Integer getExpiryMonth() {
      return expiryMonth;
    }

    /**
      * Sets the value of the 'expiryMonth' field.
      * @param value The value of 'expiryMonth'.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder setExpiryMonth(java.lang.Integer value) {
      validate(fields()[5], value);
      this.expiryMonth = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'expiryMonth' field has been set.
      * @return True if the 'expiryMonth' field has been set, false otherwise.
      */
    public boolean hasExpiryMonth() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'expiryMonth' field.
      * @return This builder.
      */
    public com.soaringclouds.avro.order.v1.OrderPayment.Builder clearExpiryMonth() {
      expiryMonth = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OrderPayment build() {
      try {
        OrderPayment record = new OrderPayment();
        record.cardType = fieldSetFlags()[0] ? this.cardType : (com.soaringclouds.avro.order.v1.CardTypeEnum) defaultValue(fields()[0]);
        record.cardNumber = fieldSetFlags()[1] ? this.cardNumber : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.startYear = fieldSetFlags()[2] ? this.startYear : (java.lang.Integer) defaultValue(fields()[2]);
        record.startMonth = fieldSetFlags()[3] ? this.startMonth : (java.lang.Integer) defaultValue(fields()[3]);
        record.expiryYear = fieldSetFlags()[4] ? this.expiryYear : (java.lang.Integer) defaultValue(fields()[4]);
        record.expiryMonth = fieldSetFlags()[5] ? this.expiryMonth : (java.lang.Integer) defaultValue(fields()[5]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OrderPayment>
    WRITER$ = (org.apache.avro.io.DatumWriter<OrderPayment>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OrderPayment>
    READER$ = (org.apache.avro.io.DatumReader<OrderPayment>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
