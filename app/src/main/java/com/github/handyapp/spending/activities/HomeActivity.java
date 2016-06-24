package com.github.handyapp.spending.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.handyapp.spending.R;
import com.github.handyapp.spending.domain.Balance;
import com.github.handyapp.spending.ui.adapters.BalancesListAdapter;
import com.github.handyapp.spending.ui.components.CurrencyPicker;
import com.github.handyapp.spending.ui.components.CurrencyPickerListener;
import com.orm.query.Select;

import java.util.List;

public class HomeActivity extends Activity {

    private static final int NAV_ADD_ACCOUNT = 201;
    private static final int NAV_SHOW_HISTORY = 202;

    private List<Balance> balances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updateAccountsList();
    }

    public void processSelectCurrency(View view) {
        CurrencyPicker picker = CurrencyPicker.newInstance();
        picker.setListener(new CurrencyPickerListener() {
            @Override
            public void onSelectCurrencyCode(String currencyCode) {
                Toast.makeText(HomeActivity.this, currencyCode, Toast.LENGTH_SHORT).show();
            }
        });
        picker.show(getFragmentManager(), "dialog");

    }

    public void processAddBalance(View view) {
        Intent addAccountIntent = new Intent(this, AddBalanceActivity.class);
        startActivityForResult(addAccountIntent, NAV_ADD_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case NAV_ADD_ACCOUNT:
                updateAccountsList();
                break;
            default:
        }
    }

    private void updateAccountsList() {

        balances = Select.from(Balance.class).list();

        ListView listOfBalances = (ListView) findViewById(R.id.availableBalances);
        listOfBalances.setAdapter(
                new BalancesListAdapter(this, R.layout.balance_list_item, balances)
        );
        listOfBalances.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showHistoryIntent = new Intent(HomeActivity.this, BalanceHistoryActivity.class);
                showHistoryIntent.putExtra(
                        "balances",
                        new String[]{String.valueOf(HomeActivity.this.balances.get(position).getId())}
                );
                startActivity(showHistoryIntent);
            }
        });

    }


}
