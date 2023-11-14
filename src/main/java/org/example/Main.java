package org.example;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();

        // Erstellen von Kunden
        Client client1 = new Client("John", "Doe", "12345");
        Client client2 = new Client("Jane", "Doe", "12346");
        Client client3 = new Client("Max", "Mustermann", "12347");

        // Öffnen von Konten
        String accountNumber1 = bankService.openAccount(client1);
        String accountNumber2 = bankService.openAccount(client2);

        // Einzahlung auf die Konten
        bankService.deposit(accountNumber1, new BigDecimal("1000"));
        bankService.deposit(accountNumber2, new BigDecimal("500"));

        // Ausgabe der Kontostände
        System.out.println("Kontostand von " + accountNumber1 + ": " + bankService.getBalance(accountNumber1));
        System.out.println("Kontostand von " + accountNumber2 + ": " + bankService.getBalance(accountNumber2));

        // Überweisung von Konto 1 zu Konto 2
        bankService.transfer(accountNumber1, accountNumber2, new BigDecimal("200"));

        // Ausgabe der Kontostände nach der Überweisung
        System.out.println("Kontostand von " + accountNumber1 + " nach Überweisung: " + bankService.getBalance(accountNumber1));
        System.out.println("Kontostand von " + accountNumber2 + " nach Überweisung: " + bankService.getBalance(accountNumber2));

        // Erstellen eines Gemeinschaftskontos und Aufteilung
        String jointAccountNumber = bankService.openAccount(client1);
        bankService.addAccountHolder(jointAccountNumber, client3); // Fügt einen weiteren Kontoinhaber hinzu
        bankService.addAccountHolder(jointAccountNumber, client2);
        bankService.deposit(jointAccountNumber, new BigDecimal("301"));
        System.out.println("Gemeinschaftskontostand vor Aufteilung: " + bankService.getBalance(jointAccountNumber));

        // Aufteilung des Gemeinschaftskontos
        List<String> newAccountNumbers = bankService.split(jointAccountNumber);

        // Überprüfen und Ausgeben der Kontostände der neuen Konten
        for (String newAccountNumber : newAccountNumbers) {
            System.out.println("Kontostand von neuem Konto " + newAccountNumber + ": " + bankService.getBalance(newAccountNumber));
        }

        // Ausgabe der neuen Kontostände
        System.out.println("Kontostand von " + accountNumber1 + " nach Aufteilung: " + bankService.getBalance(accountNumber1));
        System.out.println("Kontostand von " + accountNumber2 + " nach Aufteilung: " + bankService.getBalance(accountNumber2));

        // Anzeigen aller Konten von client1
        bankService.showAccountsOfClient(bankService, client1);

        // Anzeigen aller Konten von client2
        bankService.showAccountsOfClient(bankService, client2);

        // Anzeigen aller Konten von client3
        bankService.showAccountsOfClient(bankService, client3);

    }


}
