package com.lancestack.entities;

public enum PaymentMethod {
	BANK_TRANSFER("Bank Transafer"), PAYPAL("PayPal"), CREDIT_CARD("Credit Card");

	private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
