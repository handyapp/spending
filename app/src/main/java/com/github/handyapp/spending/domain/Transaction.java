package com.github.handyapp.spending.domain;


import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Transaction extends SugarRecord {

    @NotNull
    private Date date;
    @NotNull
    private Type type;
    /**
     * Transaction value expressed in hundredth of a currency
     */
    @NotNull
    private Long amount;
    @NotNull
    private PaymentMethod target;
    @NotNull
    @Unique
    private String fingerPrint;

    public Transaction() {
    }

    public enum Type {
        DEBIT, CREDIT;
    }

}
