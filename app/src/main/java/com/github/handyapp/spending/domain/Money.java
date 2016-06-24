package com.github.handyapp.spending.domain;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Money extends SugarRecord {

    @NotNull
    private double value;

    @NotNull
    private String currencyCode;

}
