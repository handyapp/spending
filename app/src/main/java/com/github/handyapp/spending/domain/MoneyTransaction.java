package com.github.handyapp.spending.domain;

import com.orm.dsl.NotNull;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyTransaction extends Money {
    @NotNull
    private Date date;

    private double exchangeRate;

    @NotNull
    private Balance targetBalance;
}
