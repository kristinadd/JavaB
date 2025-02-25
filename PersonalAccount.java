public class PersonalAccount {
  private int id;
  private Double balance;
  private String currency; // String up to ten characters
  private int accountNumber;

  public PersonalAccount(String currency) {
    this.balance = 0.00;
    try {
      currency = currency.toUpperCase();
      SupportedCurrencies supported = SupportedCurrencies.valueOf(currency);
      this.currency = supported.name();
    } catch (IllegalArgumentException ex) {
      System.out.println("❌ Currency " + currency + " is not supported.");
    }
  }

  public int getId() {
    return id;
  }

  public double getBalance() {
    return balance;
  }

  public String getCurrency() {
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
    if (amount > balance) {
      System.out.println("❌ Insufficient funds. Your current balance is: " + balance);
      return balance;
    } else {
      balance = balance - amount;
    }

    return balance;
  }
}
