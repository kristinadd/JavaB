package domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public class PersonalAccount {
  private UUID id;
  private BigDecimal balance;
  private Currency currency;
  private Instant createdAt;
  private Instant updatedAt;

  public PersonalAccount(Currency currency) {
    this.id = UUID.randomUUID();
    this.balance = BigDecimal.ZERO;
    this.currency = currency;
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }

  public PersonalAccount() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public BigDecimal deposit(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) > 0) {
      balance = balance.add(amount);
    }
    return balance;
  }

  public BigDecimal withdraw(BigDecimal amount) throws InsufficientFundsException {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Withdrawal amount must be positive.");
    }
    if (amount.compareTo(balance) > 0) {
      throw new InsufficientFundsException("Insufficient funds. Your current balance is: " + balance);
    }
    balance = balance.subtract(amount);
    return balance;
  }

  @Override
  public String toString() {
    return String.format(
      "PersonalAccount \n" +
      "  id: %s,\n" +
      "  balance: %.2f,\n" +
      "  currency: %s,\n" +
      "  createdAt: %s,\n" +
      "  updatedAt: %s\n",
      id,
      balance.doubleValue(),
      currency != null ? currency.getCurrencyCode() : "null",
      createdAt,
      updatedAt
    );
  }  
}
