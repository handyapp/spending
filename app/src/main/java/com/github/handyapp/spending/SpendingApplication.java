package com.github.handyapp.spending;

import com.github.handyapp.spending.domain.Category;
import com.github.handyapp.spending.domain.FinancialTransaction;
import com.github.handyapp.spending.domain.PaymentMethod;
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

        Category.findById(Category.class, (long) 1);
        PaymentMethod.findById(PaymentMethod.class, (long) 1);
        FinancialTransaction.findById(FinancialTransaction.class, (long) 1);

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
