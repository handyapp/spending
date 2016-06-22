package com.github.handyapp.spending.domain;


import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Table;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Table(name = "FINANCIAL_TRANSACTION")
public class FinancialTransaction extends SugarRecord {

    public enum Type {
        DEBIT, CREDIT;
    }

    @NotNull
    private Type type;
    /**
     * FinancialTransaction value expressed in hundredth of a currency
     */
    @NotNull
    private Long amount;
    @NotNull
    private PaymentMethod source;
    @NotNull
    private PaymentMethod target;
    @NotNull
    private Date date;

    public FinancialTransaction() {
    }

}
