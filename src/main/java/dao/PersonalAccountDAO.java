package dao;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
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
}
