package com.example.naplo.forex;

import com.oanda.v20.account.AccountID;
import com.oanda.v20.primitives.Currency;

public class AccountSummaryData {

    private AccountID id;
    private String alias;
    private Currency currency;
    private String balance;
    private String pl;
    private String marginUsed;
    private String marginAvailable;
    private String withdrawalLimit;

    public AccountSummaryData(AccountID id, String alias, Currency currency, String balance,
                              String pl, String marginUsed, String marginAvailable, String withdrawalLimit) {
        this.id = id;
        this.alias = alias;
        this.currency = currency;
        this.balance = balance;
        this.pl = pl;
        this.marginUsed = marginUsed;
        this.marginAvailable = marginAvailable;
        this.withdrawalLimit = withdrawalLimit;
    }

    public AccountID getId() { return id; }
    public String getAlias() { return alias; }
    public Currency getCurrency() { return currency; }
    public String getBalance() { return balance; }
    public String getPl() { return pl; }
    public String getMarginUsed() { return marginUsed; }
    public String getMarginAvailable() { return marginAvailable; }
    public String getWithdrawalLimit() { return withdrawalLimit; }
}
