import java.util.Scanner;

public class AccountManager {
  Scanner scanner = new Scanner(System.in);
  Scanner currencyInput = new Scanner(System.in);
  
  public void manage() {
    while (true) {
      menu();
      int input = scanner.nextInt();

      switch (input) {
        case 0:
          optionZero();
          break;
        case 1:
            System.out.println("Option is not available");
            break;
        case 2:
          System.out.println("Option is not available");
            break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  public void menu() {
    System.out.println("-------------------------");
    System.out.println("🏧⭐️🏧 Online Bank 🏧⭐️🏧");
    System.out.println("-------------------------");
    
    String[] options = {
      "Create account",
      "Some other option",
      "And another option"
    };

    for (int i=0; i<options.length; i++) {
      System.out.println(i + ": "+ options[i]);
    }
  }

  public void optionZero() {
    System.out.println("Choose currency");
    Currency[] currencies = Currency.values();
    for (int i=0; i<currencies.length; i++) {
      System.out.println(i + ": " + currencies[i]);
    }

    int input = scanner.nextInt();

    if (input == 2) {
      PersonalAccount account = new PersonalAccount("USD");
      System.out.println(account);
    }
  }
}
