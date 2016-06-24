package com.github.handyapp.spending.domain;


import com.orm.dsl.NotNull;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Expense extends MoneyTransaction {

    private Category category;

    public Expense() {
    }

}
