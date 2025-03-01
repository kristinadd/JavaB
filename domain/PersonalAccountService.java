public class PersonalAccountService {
  private PersonalAccount account;
  
  public PersonalAccountService(PersonalAccount account) {
    this.account = account;
  }

  public void deposit(double amount) {
    account.deposit(amount);
  }

  public void withdraw(double amount) {
    account.withdraw(amount);
  }

}
