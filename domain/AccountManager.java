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

    for() {

    }

    int input = scanner.nextInt();
    int currency =  currencyInput.nextInt();

    if (input == 0) {
      PersonalAccount personalAccount = new PersonalAccount();
    }

  }
}
