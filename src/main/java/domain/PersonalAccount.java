public class PersonalAccount {
  private static int idCount = 1; // handle in db, upon restart it will start from 1 again
  private static int accountNumberCount = 11111;
  //
  private int id;
  private Double balance;
  private Currency currency;
  private int accountNumber;

  public PersonalAccount(Currency currency) {
    this.id = idCount++;
    this.balance = 0.00;
    if (currency == null) {
      throw new IllegalArgumentException("Currency cannot be null");
    }
    this.currency = currency;
    this.accountNumber = accountNumberCount++;
  }

  public int getId() {
    return id;
  }

  public double getBalance() {
    return balance;
  }

  public Currency getCurrency() {
    return currency;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public double deposit(double amount) {
    if (amount > 0.00) {
      balance = balance + amount;
    }
    return balance;
  }

  public double withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Withdrawal amount must be positive.");
    }
    if (amount > balance) {
        throw new InsufficientFundsException("Insufficient funds. Your current balance is: " + balance);
    }
    balance -= amount;
    return balance;
  }

  @Override
  public String toString() {
    return String.format(
      "id: %d\nbalance: %.2f\ncurrency: %s\naccountNumber: %d", 
      this.id, 
      this.balance, 
      this.currency, 
      this.accountNumber
    );
  }
}
