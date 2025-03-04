public class PersonalAccountFactory {
  private static PersonalAccountFactory instance = new PersonalAccountFactory();

  private PersonalAccountFactory() {}

  public static PersonalAccountFactory getInstance() {
    return instance;
  }

  public PersonalAccount createPersonalAccount(Currency currency) {
    switch (currency) {
      case Currency.EUR:
        return new PersonalAccount(Currency.EUR);
      case Currency.GBP:
        return new PersonalAccount(Currency.GBP);
      case Currency.USD:
        return new PersonalAccount(Currency.USD);
      default:
        throw new IllegalArgumentException("Currency must be from the supported currencies.");
    }
  }
}
