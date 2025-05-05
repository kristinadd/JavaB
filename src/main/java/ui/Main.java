package ui;

import dao.PersonalAccountDAO;
import dao.PersonalAccountService;
import domain.PersonalAccountFactory;

public class Main {
  public static void main(String args[]) {

  PersonalAccountDAO dao = new PersonalAccountDAO();
   PersonalAccountService service = new PersonalAccountService();

   PersonalAccountFactory factory = PersonalAccountFactory.getInstance();

    // AccountManager accountManager = AccountManager.initialize(dao, service).getInstance();
    // accountManager.manage();

    AccountManager.initialize(dao, service, factory);
    AccountManager.getInstance().manage();
  }
}
