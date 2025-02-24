public class PersonalAccount {
  private int id;
  private Double balance;
  private String currency;
  // add account number

  public PersonalAccount(String currency) {
    this.balance = 0.0;
    try {
      currency = currency.toUpperCase();
      SupportedCurrencies supported = SupportedCurrencies.valueOf(currency);
      this.currency = supported.name();
    } catch (IllegalArgumentException ex) {
      System.out.println("❌ Currency " + currency + " is not supported.");
    }
  }

  public double getBalance() {
    return balance;
  }

  public String getCurrency() {
    return currency;
  }

  public double deposit(double amount) {
    if (amount > 0.0) {
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
