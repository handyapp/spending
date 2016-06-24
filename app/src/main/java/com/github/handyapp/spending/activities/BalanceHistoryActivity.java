package com.github.handyapp.spending.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.github.handyapp.spending.R;
import com.github.handyapp.spending.domain.Balance;
import com.github.handyapp.spending.domain.Expense;
import com.github.handyapp.spending.domain.Income;
import com.github.handyapp.spending.domain.MoneyTransaction;
import com.github.handyapp.spending.ui.adapters.BalanceHistoryListAdapter;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BalanceHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_history);

        String[] balances = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            balances = (String[]) extras.get("balances");
        }

        ListView historyList = (ListView) findViewById(R.id.balanceHistoryList);
        historyList.setAdapter(
                new BalanceHistoryListAdapter(this, R.layout.balance_history_list_item, loadTransactionsHistory(balances))
        );
    }

    private List<MoneyTransaction> loadTransactionsHistory(String... balancesFilter) {
        Set<MoneyTransaction> transactions = new TreeSet<>(new Comparator<MoneyTransaction>() {
            @Override
            public int compare(MoneyTransaction lhs, MoneyTransaction rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });

        List<Balance> balances = (balancesFilter == null)
                ? Balance.listAll(Balance.class)
                : Balance.findById(Balance.class, balancesFilter);
        for (Balance balance : balances) {
            transactions.addAll(
                    Select.from(Expense.class).where(Condition.prop("TARGET_BALANCE").eq(balance.getId())).list()
            );
            transactions.addAll(
                    Select.from(Income.class).where(Condition.prop("TARGET_BALANCE").eq(balance.getId())).list()
            );
        }

        return new ArrayList<MoneyTransaction>(transactions);
    }
}
