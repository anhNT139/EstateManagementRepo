package com.kepler.enums;

public enum TransactionTypesEnum {
    TU_VAN("Tư vấn"),
    DAN_DI_XEM("Dẫn đi xem");

    private final String transactionName;

    TransactionTypesEnum(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionName() {
        return transactionName;
    }
}
