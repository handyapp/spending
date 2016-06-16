package com.github.handyapp.spending.activities;

import android.app.Activity;
import android.os.Bundle;

import com.github.handyapp.spending.R;

public class TransactionHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
    }
}
