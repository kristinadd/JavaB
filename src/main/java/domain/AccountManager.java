package domain;

import java.util.OptionalDouble;
import java.util.Scanner;
public class AccountManager {
  private static AccountManager instance = new AccountManager();
  private PersonalAccountFactory factory = PersonalAccountFactory.getInstance();
  private Scanner scanner = new Scanner(System.in);
  private PersonalAccount account;

  private AccountManager() {}

  public static AccountManager getInstance() {
    return instance;
  }

  public void manage() {
    boolean keepRuning = true;
    while (keepRuning) {
      mainMenu();
      int userChoice = scanner.nextInt();

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
          keepRuning = false;
          System.out.println("👋 Bye");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  public void mainMenu() {
    System.out.println("-------------------------");
    System.out.println("🏧⭐️🏧 Online Bank 🏧⭐️🏧");
    System.out.println("-------------------------");

    String[] options = {
      "Create account",
      "Check balance",
      "Deposit",
      "Withdraw",
      "Exit"
    };

    for (int i=0; i<options.length; i++) {
      System.out.println(i + ": "+ options[i]);
    }
  }

  public void loadCurrencies() {
    Currency[] currencies = Currency.values();
    for (int i=0; i<currencies.length; i++) {
      System.out.println(i + ": " + currencies[i]);
    }
  }

  public void createAccount() {
    Currency currency = null;
    boolean valid = false;
    
    while (!valid) {
      System.out.println("Please select the currency for your new account:");
        loadCurrencies();
        int userChoice = scanner.nextInt();

        if (userChoice == 0) {
            currency = Currency.EUR;
            valid = true;
        } else if (userChoice == 1) {
            currency = Currency.GBP;
            valid = true;
        } else if (userChoice == 2) {
            currency = Currency.USD;
            valid = true;
        } else {
            System.out.println("Invalid selection. Please try again.");
        }
    }

    account = factory.createPersonalAccount(currency);
    System.out.println(account);
  }

  public void deposit() {
    System.out.println("Enter amount: ");
    Double userChoiceAmount = scanner.nextDouble();
    account.deposit(userChoiceAmount);
    System.out.println(account);
  }

  public void withdraw() {
    System.out.println("Enter amount");
    Double userChoiceAmount = scanner.nextDouble();
    account.withdraw(userChoiceAmount);
    System.out.println(account);
  }

  public OptionalDouble checkBalance() {
    if (account != null) {
      return OptionalDouble.of(account.getBalance());
    } else {
      System.out.println("Account has not been created yet. Please create an account first.");
      return OptionalDouble.empty();
    }
  }
}
