package dao;

import java.util.List;
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

  public PersonalAccount create(PersonalAccount account) {
    return dao.create(account);
  }

  public List<PersonalAccount> readAll() {
    return dao.readAll();
  }

  public void delete(UUID id) {
    dao.delete(id);
  }

  public PersonalAccount update(PersonalAccount account) {
    return dao.update(account);
  }
}
