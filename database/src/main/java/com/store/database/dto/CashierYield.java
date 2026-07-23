package com.store.database.dto;

import java.math.BigDecimal;

public interface CashierYield {
    Integer getCashierId();
    String getCashierName();
    BigDecimal getTotalProfit();
}