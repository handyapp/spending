package com.github.handyapp.spending.domain;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Category extends SugarRecord {

    private Category parent;

    @Unique
    @NotNull
    private String name;

    public Category() {
    }

    public List<Category> getChildren() {
        List<Category> categories = Select.from(Category.class)
                .where(Condition.prop("parent").eq(this.getId()))
                .list();
        return categories;
    }
}
