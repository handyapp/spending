package com.github.handyapp.spending.services;

import android.support.annotation.NonNull;

import com.github.handyapp.spending.R;
import com.github.handyapp.spending.domain.Balance;
import com.github.handyapp.spending.domain.Expense;
import com.github.handyapp.spending.domain.Income;
import com.github.handyapp.spending.domain.MoneyTransaction;

import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;
import org.joda.money.Money;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.GregorianCalendar;

public class BalanceService extends BusinessService {

    private static final String TAG = "BS";

    private static final MoneyFormatter MONEY_FORMATTER = new MoneyFormatterBuilder()
            .appendAmountLocalized()
            .appendLiteral(" ")
            .appendCurrencySymbolLocalized()
            .toFormatter();

    public static Balance createBalance(String name, String currencyCode, String description) {

        Balance balance = new Balance();
        balance.setName(name);
        balance.setDescription(description);
        balance.setValue(0L);
        balance.setCurrencyCode(currencyCode);
        balance.save();

        return balance;
    }

    public static String formatBalanceValue(double value, String currencyCode) {
        return MONEY_FORMATTER.print(Money.of(CurrencyUnit.of(currencyCode), value).toBigMoney());
    }

    public static String formatBalanceValue(Money money) {
        return MONEY_FORMATTER.print(money.toBigMoney());
    }

    public static void addIncome(Balance balance, BigDecimal value, String currency, BigDecimal exchangeRate) {

        validateTransactionData(balance, value, currency);

        CurrencyUnit balanceCurrency = CurrencyUnit.of(balance.getCurrencyCode());
        CurrencyUnit transactionCurrency = CurrencyUnit.getInstance(currency);

        Income income = (Income) prepareTransaction(balance, value, currency, exchangeRate);

        Money transactionMoney = normalizeTransactionMoney(value, exchangeRate, balanceCurrency, transactionCurrency);
        Money balanceMoney = Money.of(balanceCurrency, balance.getValue());
        Money total = balanceMoney.plus(transactionMoney);

        balance.setValue(total.getAmount().doubleValue());

        income.save();
        balance.save();

    }

    public static void addExpense(Balance balance, BigDecimal value, String currency, BigDecimal exchangeRate) {
        // TODO: 2016-06-24 Insert expense's category
        validateTransactionData(balance, value, currency);

        CurrencyUnit balanceCurrency = CurrencyUnit.of(balance.getCurrencyCode());
        CurrencyUnit transactionCurrency = CurrencyUnit.getInstance(currency);

        Expense transaction = (Expense) prepareTransaction(balance, value, currency, exchangeRate);

        Money transactionMoney = normalizeTransactionMoney(value, exchangeRate, balanceCurrency, transactionCurrency);
        Money balanceMoney = Money.of(balanceCurrency, balance.getValue());
        // TODO: 2016-06-24 Calculate balance's total change by a transaction, and check whether the balance has credit line (may be negative)
        Money total = balanceMoney.minus(transactionMoney);

        balance.setValue(total.getAmount().doubleValue());

        transaction.save();
        balance.save();
    }

    private static void validateTransactionData(Balance balance, BigDecimal value, String currency) {

        if (balance == null) {
            throw new IllegalArgumentException(getString(R.string.cem_undefined_target_balance));
        }

        if (value == null) {
            throw new IllegalArgumentException(getString(R.string.cem_undefined_amount));
        }

        if (value.signum() == -1) {
            throw new IllegalArgumentException(getString(R.string.cem_negative_amount));
        }

        if (!Currency.getAvailableCurrencies().contains(currency)) {
            throw new IllegalArgumentException(getString(R.string.cem_invalid_currency));
        }
    }

    @NonNull
    private static MoneyTransaction prepareTransaction(Balance balance, BigDecimal value, String currency, BigDecimal exchangeRate) {
        MoneyTransaction transaction = new MoneyTransaction();
        transaction.setTargetBalance(balance);
        transaction.setValue(value.doubleValue());
        transaction.setCurrencyCode(currency);
        transaction.setDate(GregorianCalendar.getInstance().getTime());
        transaction.setExchangeRate(exchangeRate.doubleValue());
        return transaction;
    }

    @NonNull
    private static Money normalizeTransactionMoney(BigDecimal value, BigDecimal exchangeRate, CurrencyUnit balanceCurrency, CurrencyUnit transactionCurrency) {

        Money transactionMoney;

        if (transactionCurrency.equals(balanceCurrency)) {
            transactionMoney = Money.of(transactionCurrency, value);
        } else {
            transactionMoney = Money
                    .of(transactionCurrency, value)
                    .convertedTo(balanceCurrency, exchangeRate, RoundingMode.HALF_UP);
        }

        return transactionMoney;
    }


}
