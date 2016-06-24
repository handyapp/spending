package com.github.handyapp.spending.domain;

import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Balance extends Money {

    @Unique
    @NotNull
    private String name;

    private String description;

}
