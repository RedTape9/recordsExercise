package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankService {
    private final Map<String, Account> accounts = new HashMap<>();

    public String openAccount(Client client) {
        String accountNumber = UUID.randomUUID().toString();
        Account account = new Account(accountNumber, client);
        accounts.put(accountNumber, account);
        return accountNumber;
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Konto nicht gefunden.");
        }
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = accounts.get(accountNumber);
        if (account != null && account.getBalance().compareTo(amount) >= 0) {
            account.withdraw(amount);
        } else {
            System.out.println("Unzureichender Saldo oder Konto nicht gefunden.");
        }
    }

    public void transfer(String fromAccountNum, String toAccountNum, BigDecimal amount) {
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);

        if (fromAccount != null && toAccount != null && fromAccount.getBalance().compareTo(amount) >= 0) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        } else {
            System.out.println("Überweisung nicht möglich.");
        }
    }

    public List<String> split(String accountNumber) {
        Account jointAccount = accounts.get(accountNumber);
        if (jointAccount == null || jointAccount.getAccountHolders().size() < 2) {
            System.out.println("Gemeinschaftskonto nicht gefunden oder nur ein Kontoinhaber.");
            return null;
        }

        BigDecimal totalAmount = jointAccount.getBalance();
        int numberOfHolders = jointAccount.getAccountHolders().size();
        BigDecimal splitAmount = totalAmount.divide(BigDecimal.valueOf(numberOfHolders), 2, RoundingMode.DOWN);

        List<String> newAccountNumbers = new ArrayList<>();
        BigDecimal totalDistributed = BigDecimal.ZERO;

        for (Client client : jointAccount.getAccountHolders()) {
            String newAccountNumber = openAccount(client);
            deposit(newAccountNumber, splitAmount);
            newAccountNumbers.add(newAccountNumber);
            totalDistributed = totalDistributed.add(splitAmount);
        }

        BigDecimal remainder = totalAmount.subtract(totalDistributed);
        if (remainder.compareTo(BigDecimal.ZERO) > 0) {
            deposit(newAccountNumbers.get(0), remainder);
        }

        accounts.remove(accountNumber);
        return newAccountNumbers;
    }

    public BigDecimal getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : BigDecimal.ZERO;
    }

    public void addAccountHolder(String accountNumber, Client additionalClient) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.addAccountHolder(additionalClient);
        } else {
            System.out.println("Konto nicht gefunden.");
        }
    }

    public List<String> getAccountsOfClient(String customerNumber) {
        return accounts.values().stream()
                .filter(account -> account.getAccountHolders().stream()
                        .anyMatch(client -> client.customerNumber().equals(customerNumber)))
                .map(Account::getAccountNumber)
                .collect(Collectors.toList());
    }

    public void showAccountsOfClient(BankService bankService, Client client) {
        List<String> clientAccount = bankService.getAccountsOfClient(client.customerNumber());
        System.out.println("Konten von " + client.firstName() + " " + client.lastName() + ":");
        for (String accountNumber : clientAccount) {
            System.out.println("Kontonummer: " + accountNumber + ", Saldo: " + bankService.getBalance(accountNumber));
        }
    }
}
