package ui;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import dao.PersonalAccountDAO;
import dao.PersonalAccountService;
import domain.PersonalAccount;
import domain.PersonalAccountFactory;
import domain.InsufficientFundsException;

public class AccountManagerV2 {
    private final PersonalAccountFactory factory;
    private final Scanner scanner;
    private final PersonalAccountDAO dao;
    private final PersonalAccountService service;

    public AccountManagerV2(PersonalAccountDAO dao, PersonalAccountService service) {
        this.factory = PersonalAccountFactory.getInstance();
        this.scanner = new Scanner(System.in);
        this.dao = dao;
        this.service = service;
    }

    public void manage() {
        boolean keepRunning = true;
        while (keepRunning) {
            mainMenu();
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // consume the remaining newline

            switch (userChoice) {
                case 0:
                    createAccount();
                    break;
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    keepRunning = false;
                    System.out.println("👋 Bye");
                    break;
                case 5:
                    System.out.println("Provide account id");
                    String accountId = scanner.nextLine();
                    readAccount(accountId);
                    break;
                case 6:
                    readAllAccounts();
                    break;
                case 7:
                    System.out.println("Provide account id");
                    accountId = scanner.nextLine();
                    deleteAccount(accountId);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void mainMenu() {
        System.out.println("-------------------------");
        System.out.println("🏧⭐️🏧 Online Bank 🏧⭐️🏧");
        System.out.println("-------------------------");

        String[] options = {
            "Create account",
            "Check balance",
            "Deposit",
            "Withdraw",
            "Exit",
            "Read account",
            "Read all",
            "Delete"
        };

        for (int i = 0; i < options.length; i++) {
            System.out.println(i + ": " + options[i]);
        }
    }

    private void createAccount() {
        Currency currency = selectCurrency();
        if (currency == null) {
            System.out.println("Invalid currency selection. Account creation cancelled.");
            return;
        }

        PersonalAccount account = factory.createPersonalAccount(currency);
        dao.create(account);
        System.out.println("✅ Account created successfully:");
        System.out.println(account);
    }

    private Currency selectCurrency() {
        System.out.println("Please select the currency for your new account:");
        String[] currencyCodes = {"EUR", "GBP", "USD"};
        for (int i = 0; i < currencyCodes.length; i++) {
            System.out.println(i + ": " + currencyCodes[i]);
        }

        int userChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            return Currency.getInstance(currencyCodes[userChoice]);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            return null;
        }
    }

    private Optional<PersonalAccount> getAccountById() {
        System.out.println("Enter account ID:");
        String accountId = scanner.nextLine();
        try {
            UUID uuid = UUID.fromString(accountId);
            PersonalAccount account = service.readOne(uuid);
            if (account == null) {
                System.out.println("❌ Account not found");
                return Optional.empty();
            }
            return Optional.of(account);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid account ID format");
            return Optional.empty();
        }
    }

    private void checkBalance() {
        getAccountById().ifPresent(account -> 
            System.out.println("💰 Current balance: " + account.getBalance())
        );
    }

    private void deposit() {
        getAccountById().ifPresent(account -> {
            System.out.println("Enter amount to deposit:");
            try {
                BigDecimal amount = scanner.nextBigDecimal();
                scanner.nextLine(); // consume newline
                
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("❌ Deposit amount must be positive");
                    return;
                }

                account.deposit(amount);
                service.update(account);
                System.out.println("✅ Deposit successful. New balance: " + account.getBalance());
            } catch (Exception e) {
                System.out.println("❌ Invalid amount entered");
            }
        });
    }

    private void withdraw() {
        getAccountById().ifPresent(account -> {
            System.out.println("Enter amount to withdraw:");
            try {
                BigDecimal amount = scanner.nextBigDecimal();
                scanner.nextLine(); // consume newline

                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("❌ Withdrawal amount must be positive");
                    return;
                }

                try {
                    account.withdraw(amount);
                    service.update(account);
                    System.out.println("✅ Withdrawal successful. New balance: " + account.getBalance());
                } catch (InsufficientFundsException e) {
                    System.out.println("❌ " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid amount entered");
            }
        });
    }

    private void readAccount(String accountId) {
        try {
            UUID uuid = UUID.fromString(accountId);
            PersonalAccount account = service.readOne(uuid);
            if (account != null) {
                System.out.println(account);
            } else {
                System.out.println("❌ Account not found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid account ID format");
        }
    }

    private void readAllAccounts() {
        List<PersonalAccount> accounts = dao.readAll();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    private void deleteAccount(String accountId) {
        try {
            UUID uuid = UUID.fromString(accountId);
            dao.delete(uuid);
            System.out.println("✅ Account deleted successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid account ID format");
        }
    }
} 