public class Main {
  public static void main(String args[]) {
    PersonalAccount personal = new PersonalAccount("EUR");

    System.out.println(personal.getBalance());
    System.out.println(personal.getCurrency());

    personal.deposit(100.00);
    System.out.println(personal.getBalance());
    personal.withdraw(50);
    System.out.println(personal.getBalance());

    PersonalAccount personal_1 = new PersonalAccount("BGN");
    PersonalAccount personal_2 = new PersonalAccount("eur");

    
    System.out.println(personal_1.getId());
    System.out.println(personal_2.getId());

    
  }
}
