import java.util.Scanner;

public class AccountManager {
  
  public void manage() {
    System.out.println("-------------------------");
    System.out.println("🏧⭐️🏧 Online Bank 🏧⭐️🏧");
    System.out.println("-------------------------");


    Scanner scanner = new Scanner(System.in);
    Scanner currencyInput = new Scanner(System.in);

    String[] options = {
      "Create account"
    };

    for (int i=0; i<options.length; i++) {
      System.out.println(i + ": "+ options[i]);
    }


    int input = scanner.nextInt();

    switch (input) {
      case 0:
        System.out.println("Choose currency");
        Currency[] currencies = Currency.values();
        for (int i=0; i<currencies.length; i++) {
          System.out.println(i + ": " + currencies[i]);
        }

        input = scanner.nextInt();

        if (input == 2) {
          PersonalAccount account = new PersonalAccount("USD");
          System.out.println(account);
        }

        break;
      default:
        System.out.println("Invalid choice. Please try again.");
    }
  }
}
