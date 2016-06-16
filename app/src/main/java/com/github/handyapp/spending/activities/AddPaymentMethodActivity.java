package com.github.handyapp.spending.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.handyapp.spending.services.BusinessService;
import com.github.handyapp.spending.R;

import java.util.Currency;

public class AddPaymentMethodActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_method);
    }

    public void processAddPaymentMethod(View view) {

        EditText nameValue = (EditText) findViewById(R.id.accountNameValueEdit);
        EditText currencyValue = (EditText) findViewById(R.id.accountCurrencyValueEdit);
        EditText descriptionValue = (EditText) findViewById(R.id.accountDescriptionValueEdit);

        BusinessService.createPaymentMethod(
                nameValue.getText().toString(),
                Currency.getInstance(currencyValue.getText().toString()),
                descriptionValue.getText().toString()
        );

        finish();
    }
}
