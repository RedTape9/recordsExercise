package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private BigDecimal balance;
    private List<Client> accountHolders;

    public Account(String accountNumber, Client client) {
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
        this.accountHolders = new ArrayList<>();
        this.accountHolders.add(client);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Client> getAccountHolders() {
        return accountHolders;
    }

    public void addAccountHolder(Client client) {
        accountHolders.add(client);
    }
}