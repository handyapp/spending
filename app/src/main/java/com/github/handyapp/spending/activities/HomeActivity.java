package com.github.handyapp.spending.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.handyapp.spending.R;
import com.github.handyapp.spending.domain.PaymentMethod;
import com.github.handyapp.spending.fragments.CurrencyPicker;
import com.github.handyapp.spending.fragments.CurrencyPickerListener;
import com.github.handyapp.spending.services.BusinessService;
import com.orm.query.Select;

import java.util.List;

public class HomeActivity extends Activity {

    private static final int NAV_ADD_ACCOUNT = 201;

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

    public void processAddPaymentMethod(View view) {
        Intent addAccountIntent = new Intent(this, AddPaymentMethodActivity.class);
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

        List<PaymentMethod> paymentMethods = Select.from(PaymentMethod.class).list();

        ListView paymentMethodsView = (ListView) findViewById(R.id.availablePaymentMethods);
        paymentMethodsView.setAdapter(
                new PaymentMethodsListAdapter(
                        this,
                        R.layout.payment_methods_list_item,
                        paymentMethods)
        );

    }

    class PaymentMethodsListAdapter extends ArrayAdapter<PaymentMethod> {

        private class Holder {
            public TextView name;
            public TextView balance;
        }

        public PaymentMethodsListAdapter(Context context, int resource, List<PaymentMethod> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            PaymentMethod paymentMethod = getItem(position);
            if (paymentMethod == null) {
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                return inflater.inflate(R.layout.empty_list_item, parent, false);
            }

            View row = convertView;
            Holder holder;

            if (convertView == null) {
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                row = inflater.inflate(R.layout.payment_methods_list_item, parent, false);

                holder = new Holder();
                holder.name = (TextView) row.findViewById(R.id.emptyList);
                holder.balance = (TextView) row.findViewById(R.id.accountBalance);

                row.setTag(holder);
            } else {
                holder = (Holder) row.getTag();
            }

            holder.name.setText(paymentMethod.getName());

            String balanceValue = BusinessService.formatBalanceValue(
                    paymentMethod.getBalance(),
                    paymentMethod.getCurrencyCode()
            );

            holder.balance.setText(balanceValue);

            return row;
        }
    }

}
