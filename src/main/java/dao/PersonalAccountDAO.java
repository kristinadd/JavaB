package dao;

import java.util.UUID;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import java.util.Currency;
import domain.PersonalAccount;

public class PersonalAccountDAO {
    private CqlSession session;

    public PersonalAccountDAO() {
        this.session = CassandraSessionFactory.instance().getSession();
    }

    public PersonalAccount create(PersonalAccount account) { // handle exception 

        String insertQuery = "INSERT INTO javabank.account (id, balance, created_at, currency, updated_at) " +
                             "VALUES (?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = session.prepare(insertQuery);
        BoundStatement boundStatement = preparedStatement.bind(
            account.getId(),
            account.getBalance(),
            account.getCreatedAt(),
            account.getCurrency().toString(),
            account.getUpdatedAt()
        );

        session.execute(boundStatement);
        System.out.println("✅ Record inserted with id: " + account.getId());
        return account;
    }

    public PersonalAccount readOne(UUID id) { // exception
      String query = "SELECT * FROM javabank.account WHERE id = ?;";
      PreparedStatement preparedStatement = session.prepare(query);
      BoundStatement boundStatement = preparedStatement.bind(id);

      ResultSet resultSet = session.execute(boundStatement);
      Row row = resultSet.one();

      if (row == null) {
          return null;
      }

      PersonalAccount account = new PersonalAccount();
      account.setId(row.getUuid("id"));
      account.setBalance(row.getBigDecimal("balance"));
      account.setCurrency(Currency.getInstance(row.getString("currency")));
      account.setCreatedAt(row.getInstant("created_at"));
      account.setUpdatedAt(row.getInstant("updated_at"));

      return account;
    }
}
