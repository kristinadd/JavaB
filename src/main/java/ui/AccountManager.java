package ui;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;
import java.util.Currency;
import java.util.List;

import dao.PersonalAccountDAO;
import dao.PersonalAccountService;
import domain.PersonalAccount;
import domain.PersonalAccountFactory;

public class AccountManager {

  private PersonalAccountFactory factory = PersonalAccountFactory.getInstance();
  private Scanner scanner = new Scanner(System.in);
  private PersonalAccount account;

  private static AccountManager instance;
  private PersonalAccountDAO dao;
  private PersonalAccountService service;

  private AccountManager(PersonalAccountDAO dao, PersonalAccountService service) {
    this.dao = dao;
    this.service = service;
  }

  public static void initialize(PersonalAccountDAO dao, PersonalAccountService service) {
    if (instance == null) {
      instance = new AccountManager(dao, service);
    }
  }

  public static AccountManager getInstance() {
    if (instance == null) {
      throw new IllegalStateException("AccountManager not initialized");
    }
    return instance;
  }

  public void manage() {
    boolean keepRuning = true;
    while (keepRuning) {
      mainMenu();
      int userChoice = scanner.nextInt();
      scanner.nextLine(); // consume the remaining newline

      switch (userChoice) {
        case 0:
            create();
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
        case 5:
          System.out.println("Provide account id");
          String userChoice2 = scanner.nextLine();
          readOne(userChoice2);
          break;
          case 6:
            readAll();
            break;
            case 7:
            System.out.println("Provide account id");
            userChoice2 = scanner.nextLine();
            delete(userChoice2);
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
      "Exit",
      "Read account",
      "Read all",
      "Delete",
      "Update"
    };

    for (int i=0; i<options.length; i++) {
      System.out.println(i + ": "+ options[i]);
    }
  }

  public void loadCurrencies() {
    String[] currencyCodes = {"EUR", "GBP", "USD"};
    for (int i = 0; i < currencyCodes.length; i++) {
      System.out.println(i + ": " + currencyCodes[i]);
    }
  }

  public void create() {
    Currency currency = null;
    boolean valid = false;
    
    while (!valid) {
      System.out.println("Please select the currency for your new account:");
        loadCurrencies();
        int userChoice = scanner.nextInt();

        if (userChoice == 0) {
            currency = Currency.getInstance("EUR");
            valid = true;
        } else if (userChoice == 1) {
            currency = Currency.getInstance("GBP");
            valid = true;
        } else if (userChoice == 2) {
            currency = Currency.getInstance("USD");
            valid = true;
        } else {
            System.out.println("Invalid selection. Please try again.");
        }
    }

    // just create the account don't set any fields
    // AccountManager doesn't need to hold an instance of the account
    account = factory.createPersonalAccount(currency);
    System.out.println(account);
    dao.create(account);
  }

  // should require account id
  public void deposit() {
    System.out.println("Enter amount: ");
    BigDecimal userChoiceAmount = scanner.nextBigDecimal();
    account.deposit(userChoiceAmount);
    // layered architecture ; separation of concern
    service.update(account);
    System.out.println(account);
  }

  // should require account id
  public void withdraw() {
    System.out.println("Enter amount");
    BigDecimal userChoiceAmount = scanner.nextBigDecimal();
    account.withdraw(userChoiceAmount);
    service.update(account);
    System.out.println(account);
  }

  public void checkBalance() {
    if (account != null) {
        // return Optional.of(account.getBalance());
        System.out.println(account.getBalance());
    } else {
        System.out.println("Account has not been created yet. Please create an account first.");
        // return Optional.empty();
    }
  }

  public PersonalAccount readOne(String id) {
    UUID uuid = UUID.fromString(id);

    account = service.readOne(uuid);
    System.out.println(account);
    return account;
  }

  public List<PersonalAccount> readAll() {
    List<PersonalAccount> accounts = dao.readAll();

    for (PersonalAccount account : accounts) {
      System.out.println(account);
    }
    return accounts;
  }

  public void delete(String id) {
    UUID uuid = UUID.fromString(id);
    dao.delete(uuid);
  }

  public PersonalAccount update(PersonalAccount account) {
    return dao.update(account);
  }
}


// 1. Hidden state
// The account field is shared across the whole application via the singleton.
// If create(), readOne(), or any method mutates it, that change is global and affects other code using the singleton.

// 2. Unpredictable side effects
// If the user runs readOne() to load another account, it silently replaces account.
// The user may then run deposit() — but it works on the new account, which could be unexpected.

// 3. Thread safety problems
// If this singleton were used in a multi-threaded environment (e.g. a web server), the shared account would be a race condition nightmare.
// Multiple users’ actions could overwrite the same field concurrently.

// 4. Poor scalability and testability
// You can’t easily test methods like deposit() in isolation unless you’ve already initialized the account field beforehand.
// This tightly couples user interaction flow with internal object state.

