package com.github.handyapp.spending.domain;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethod extends SugarRecord {

    @Unique
    @NotNull
    private String name;
    /**
     * Contains hundredth parts of the currencyCode
     */
    @NotNull
    private Long balance;

    @NotNull
    private String currencyCode;

    private String description;

    public PaymentMethod() {
    }
}
