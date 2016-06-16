package com.github.handyapp.spending.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.github.handyapp.spending.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class CurrencyPicker extends DialogFragment {

    private List<String> selectedCodes = Collections.emptyList();
    private List<String> allCodes = Collections.emptyList();

    private EditText searchEditText;
    private ListView currencyCodesListView;

    private CurrencyPickerAdapter adapter;
    private CurrencyPickerListener listener;

    public static CurrencyPicker newInstance() {
        CurrencyPicker picker = new CurrencyPicker();
        return picker;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fetchAllCodes();

        View view = inflater.inflate(R.layout.currency_picker_list, null);

        searchEditText = (EditText) view.findViewById(R.id.currencyPickerCodeSearch);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchInCodes(s.toString());
            }
        });

        currencyCodesListView = (ListView) view.findViewById(R.id.currencyPickerCodesList);
        currencyCodesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener == null) return;
                listener.onSelectCurrencyCode(selectedCodes.get(position));
            }
        });

        adapter = new CurrencyPickerAdapter(getActivity(), selectedCodes);
        currencyCodesListView.setAdapter(adapter);

        return view;
    }

    public CurrencyPicker setListener(CurrencyPickerListener listener) {
        this.listener = listener;
        return this;
    }

    private void fetchAllCodes() {

        allCodes = new ArrayList<>();
        for (Currency c : Currency.getAvailableCurrencies()) {
            allCodes.add(c.getCurrencyCode());
        }

        Collections.sort(allCodes);

        selectedCodes = new ArrayList<>();
        selectedCodes.addAll(allCodes);

    }

    private void searchInCodes(String text) {
        selectedCodes.clear();

        if (text == null || text.isEmpty()) {
            selectedCodes.addAll(allCodes);
            return;
        }

        for (String code : allCodes) {
            if (code.toLowerCase().contains(text.toLowerCase())) {
                selectedCodes.add(code);
            }
        }

        adapter.notifyDataSetChanged();

    }
}
