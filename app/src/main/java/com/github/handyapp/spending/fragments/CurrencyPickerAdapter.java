package com.github.handyapp.spending.fragments;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.handyapp.spending.R;

import java.util.List;

public class CurrencyPickerAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final Context context;

    private List<String> isoCodes;

    public CurrencyPickerAdapter(Context context, List<String> isoCodes) {
        super();
        this.context = context;
        this.isoCodes = isoCodes;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return isoCodes.size();
    }

    @Override
    public String getItem(int position) {
        if (isoCodes == null || isoCodes.isEmpty()) {
            return null;
        }
        return isoCodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String isoCode = getItem(position);

        if (isoCode == null) {
            return inflater.inflate(R.layout.empty_list_item, parent, false);
        }

        View row = convertView;

        Holder holder;

        if (convertView == null) {
            row = inflater.inflate(R.layout.currency_picker_list_item, parent, false);
            holder = new Holder();
            holder.isoCodeText = (TextView) row.findViewById(R.id.currencyIsoCode);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        holder.isoCodeText.setText(isoCode);

        return row;
    }

    private static class Holder {
        public TextView isoCodeText;
    }
}
