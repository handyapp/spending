package com.github.handyapp.spending.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.handyapp.spending.services.BalanceService;
import com.github.handyapp.spending.R;

public class AddBalanceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
    }

    public void processAddBalance(View view) {

        EditText nameValue = (EditText) findViewById(R.id.accountNameValueEdit);
        EditText currencyValue = (EditText) findViewById(R.id.accountCurrencyValueEdit);
        EditText descriptionValue = (EditText) findViewById(R.id.accountDescriptionValueEdit);

        BalanceService.createBalance(
                nameValue.getText().toString(),
                currencyValue.getText().toString(),
                descriptionValue.getText().toString()
        );

        finish();
    }
}
