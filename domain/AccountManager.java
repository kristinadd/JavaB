import java.util.Scanner;

public class AccountManager {
  Scanner scanner = new Scanner(System.in);
  
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

  public void createAccount() {
    PersonalAccount account;
    boolean keepRuning = true;

    while (keepRuning) {
      System.out.println("Choose currency");
      loadCurrencies();
      int userChoice = scanner.nextInt();

      switch (userChoice) {
        case 0:
          account = new PersonalAccount(Currency.valueOf("EUR"));
          keepRuning = false;
          System.out.println(account);
          break;
        case 1:
          account = new PersonalAccount(Currency.valueOf("GBP"));
          System.out.println(account);
          keepRuning = false;
            break;
        case 2:
          account = new PersonalAccount(Currency.valueOf("USD"));
          keepRuning = false;
          System.out.println(account);
            break;
        default:
          keepRuning = true;
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  public void loadCurrencies() {
    Currency[] currencies = Currency.values();
    for (int i=0; i<currencies.length; i++) {
      System.out.println(i + ": " + currencies[i]);
    }
  }
}
