package dao;

import java.util.UUID;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DriverException;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.servererrors.QueryExecutionException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

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

    public List<PersonalAccount> readAll() { // exception
        String query = "SELECT * FROM javabank.account;";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind();
        ResultSet resultSet = session.execute(boundStatement);
        
        List<PersonalAccount> accounts = new ArrayList<>();
        for (Row row : resultSet) {
            PersonalAccount account = new PersonalAccount();
            account.setId(row.getUuid("id"));
            account.setBalance(row.getBigDecimal("balance"));
            account.setCurrency(Currency.getInstance(row.getString("currency")));
            account.setCreatedAt(row.getInstant("created_at"));
            account.setUpdatedAt(row.getInstant("updated_at"));
            accounts.add(account);
        }
        return accounts;
    }

    public void delete(UUID id) {
        String query = "DELETE FROM javabank.account WHERE id = ?";

        try {
            PreparedStatement preparedStatement = session.prepare(query);
            BoundStatement boundStatement = preparedStatement.bind(id);
            session.execute(boundStatement);
            System.out.println("✅ Delete account with is: " + id);
        } catch (DriverException e) {
            System.err.println("❌ Error executing delete: " + e.getMessage());
        }
    }

    // In Cassandra, an update is performed using an UPDATE statement (which works as an upsert) rather than a traditional SQL transaction. 
    public PersonalAccount update(PersonalAccount account) { // exception
        String updateQuery = "UPDATE javabank.account SET balance = ?, updated_at = ? WHERE id = ?;";
        PreparedStatement preparedStatement = session.prepare(updateQuery);
        
        BoundStatement boundStatement = preparedStatement.bind(
            account.getBalance(),
            Instant.now(),
            account.getId()
        );
        
        session.execute(boundStatement);
        System.out.println("✅ Record updated with id: " + account.getId());
        
        return account;
    }

}

