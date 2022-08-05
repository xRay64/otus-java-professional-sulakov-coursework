package ru.sulakov.exchangeservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ExchangePointData {
    private final String bankName;
    private final float USDRate;
    private final float RUBRate;
    private final int branchesCount;
    private final ExchangePointType exchangePointType;
}
