import java.util.Scanner;

public class AccountManager {
  private static AccountManager instance = new AccountManager();
  private PersonalAccountService service = new PersonalAccountService();
  private Scanner scanner = new Scanner(System.in);

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
            System.out.println("Option is not available");
            break;
        case 2:
          System.out.println("Option is not available");
            break;
        case 3:
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

    PersonalAccount account = service.createAccount(currency);
    System.out.println(account);
  }
}
