package ui;

import dao.PersonalAccountDAO;
import dao.PersonalAccountService;

public class Main {
  public static void main(String args[]) {

  PersonalAccountDAO dao = new PersonalAccountDAO();
   PersonalAccountService service = new PersonalAccountService();

    // AccountManager accountManager = AccountManager.initialize(dao, service).getInstance();
    // accountManager.manage();

    AccountManager.initialize(dao, service);
    AccountManager.getInstance().manage();
  }
}
