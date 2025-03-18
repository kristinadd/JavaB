package dao;

import java.util.UUID;

import domain.PersonalAccount;

public class PersonalAccountService {
  private PersonalAccountDAO dao;

  public PersonalAccountService() {
    this.dao = new PersonalAccountDAO();
  }

  public PersonalAccount readOne(UUID id) {
    return dao.readOne(id);
  }

}
