package domain;
import java.util.Currency;
public class PersonalAccountFactory {
  private static PersonalAccountFactory instance = new PersonalAccountFactory();

  private PersonalAccountFactory() {}

  public static PersonalAccountFactory getInstance() {
    return instance;
  }

  public PersonalAccount createPersonalAccount(Currency currency) {
    switch (currency.getCurrencyCode()) {
      case "EUR":
        return new PersonalAccount(Currency.getInstance("EUR"));
      case "GBP":
        return new PersonalAccount(Currency.getInstance("GBP"));
      case "USD":
        return new PersonalAccount(Currency.getInstance("USD"));
      default:
        throw new IllegalArgumentException("Currency must be from the supported currencies.");
    }
  }
}
