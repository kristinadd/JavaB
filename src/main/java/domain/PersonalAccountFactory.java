public class PersonalAccountFactory {
  private static PersonalAccountFactory instance = new PersonalAccountFactory();

  private PersonalAccountFactory() {}

  public static PersonalAccountFactory getInstance() {
    return instance;
  }

  public PersonalAccount createPersonalAccount(Currency currency) {
    switch (currency) {
      case EUR:
        return new PersonalAccount(Currency.EUR);
      case GBP:
        return new PersonalAccount(Currency.GBP);
      case USD:
        return new PersonalAccount(Currency.USD);
      default:
        throw new IllegalArgumentException("Currency must be from the supported currencies.");
    }
  }
}
