package com.github.handyapp.spending.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.handyapp.spending.R;
import com.github.handyapp.spending.domain.Balance;
import com.github.handyapp.spending.services.BalanceService;

import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BalancesListAdapter extends ArrayAdapter<Balance> {

    public BalancesListAdapter(Context context, int resource, List<Balance> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Balance balance = getItem(position);
        if (balance == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            return inflater.inflate(R.layout.empty_list_item, parent, false);
        }

        View row = convertView;
        Holder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(R.layout.balance_list_item, parent, false);

            holder = new Holder();
            holder.name = (TextView) row.findViewById(R.id.emptyList);
            holder.balance = (TextView) row.findViewById(R.id.accountBalance);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        holder.name.setText(balance.getName());

        int decimalPlaces = CurrencyUnit.getInstance(balance.getCurrencyCode()).getDecimalPlaces();
        holder.balance.setText(
                BalanceService.formatBalanceValue(balance.getValue(), balance.getCurrencyCode())
        );

        return row;
    }

    private class Holder {
        public TextView name;
        public TextView balance;
    }
}