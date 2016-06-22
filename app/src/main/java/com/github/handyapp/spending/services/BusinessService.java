package com.github.handyapp.spending.services;

import android.util.Log;

import com.github.handyapp.spending.domain.FinancialTransaction;
import com.github.handyapp.spending.domain.PaymentMethod;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

public final class BusinessService {

    private static final String TAG = "BS";

    public static PaymentMethod createPaymentMethod(String name, Currency currency, String description) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(name);
        paymentMethod.setCurrencyCode(currency.getCurrencyCode());
        paymentMethod.setDescription(description);
        paymentMethod.setBalance(0L);

        Long pmId = paymentMethod.save();

        return paymentMethod;
    }

    public static String formatBalanceValue(Long balance, String currencyCode) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setCurrency(Currency.getInstance(currencyCode));

        return numberFormat.format(balance / 100);
    }

    public static void addIncome(PaymentMethod target, long funds) {
        moveFunds(target, FinancialTransaction.Type.CREDIT, funds);
    }

    public static void addExpense(PaymentMethod target, long funds) {
        moveFunds(target, FinancialTransaction.Type.DEBIT, funds);
    }

    private static void moveFunds(PaymentMethod target, FinancialTransaction.Type type, long funds) {
        if (target == null || type == null) {
            Log.e(TAG, "A payment method or a transaction type are undefined");
        }
        if (funds < 0L) {
            Log.e(TAG, "Negative transaction is not allowed");
        }

        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setFingerPrint(UUID.randomUUID().toString());
        transaction.setTarget(target);
        transaction.setType(type);
        transaction.setAmount(funds);
        transaction.setDate(new Date());

        long newBalanceValue = target.getBalance();
        switch (type) {
            case CREDIT:
                newBalanceValue += funds;
                break;
            case DEBIT:
                newBalanceValue -= funds;
                break;
        }

        target.setBalance(newBalanceValue);
        transaction.save();

        target.save();
    }
}
