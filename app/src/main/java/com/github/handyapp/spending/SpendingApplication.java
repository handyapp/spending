package com.github.handyapp.spending;

import com.github.handyapp.spending.domain.Category;
import com.github.handyapp.spending.domain.Expense;
import com.github.handyapp.spending.domain.Income;
import com.github.handyapp.spending.domain.Balance;
import com.orm.SugarApp;
import com.orm.SugarContext;

import android.content.res.Configuration;

public class SpendingApplication extends SugarApp {
    public SpendingApplication() {
        super();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());

        forceInitializeDb();
    }

    private void forceInitializeDb() {

        Category.findById(Category.class, 1L);
        Balance.findById(Balance.class, 1L);
        Income.findById(Income.class, 1L);
        Expense.findById(Expense.class, 1L);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
