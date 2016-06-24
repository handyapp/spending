package com.github.handyapp.spending.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.handyapp.spending.R;
import com.github.handyapp.spending.domain.Balance;
import com.github.handyapp.spending.domain.Expense;
import com.github.handyapp.spending.domain.Income;
import com.github.handyapp.spending.domain.MoneyTransaction;
import com.github.handyapp.spending.services.BalanceService;

import org.joda.money.CurrencyUnit;

import java.text.SimpleDateFormat;
import java.util.List;

public class BalanceHistoryListAdapter extends ArrayAdapter<MoneyTransaction> {

    public BalanceHistoryListAdapter(Context context, int resource, List<MoneyTransaction> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MoneyTransaction transaction = getItem(position);
        if (transaction == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            return inflater.inflate(R.layout.empty_list_item, parent, false);
        }

        View row = convertView;
        Holder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(R.layout.balance_history_list_item, parent, false);

            holder = new Holder();
            holder.value = (TextView) row.findViewById(R.id.transaction_value);
            holder.date = (TextView) row.findViewById(R.id.transaction_date);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        // TODO: 240616064455 Preferences -> Add configurable data format
        holder.date.setText(SimpleDateFormat.getInstance().format(transaction.getDate()));
        holder.value.setText(
                BalanceService.formatBalanceValue(transaction.getValue(), transaction.getCurrencyCode())
        );

        if(transaction instanceof Expense) {
            holder.value.setTextColor(Resources.getSystem().getColor(android.R.color.holo_blue_dark));
            holder.icon.setImageResource(R.drawable.ic_expense);
        } else if (transaction instanceof Income) {
            holder.value.setTextColor(Resources.getSystem().getColor(android.R.color.holo_green_dark));
            holder.icon.setImageResource(R.drawable.ic_income);
        } else {
            holder.value.setTextColor(Resources.getSystem().getColor(android.R.color.holo_red_dark));
            holder.icon.setImageResource(R.drawable.ic_error);
        }

        return row;
    }

    private class Holder {
        public ImageView icon;
        public TextView value;
        public TextView date;
    }
}