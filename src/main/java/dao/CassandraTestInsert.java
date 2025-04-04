package dao;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class CassandraTestInsert {
    public static void main(String[] args) {
        try (CqlSession session = CassandraSessionFactory.instance().getSession()) {
            System.out.println("✅ Connected to Cassandra!");

            // Create the table "account" in the 'javabank' keyspace if it doesn't already exist.
            // Here we use the fully qualified table name (keyspace.table) since the session may not have a default keyspace.
            String createTableQuery = "CREATE TABLE IF NOT EXISTS javabank.account (" +
                                      "id UUID PRIMARY KEY, " +
                                      "balance decimal, " +
                                      "currency text," +
                                      "created_at timestamp," +
                                      "updated_at timestamp" +
                                      ");";
            session.execute(createTableQuery);
            System.out.println("Table 'account' created (if it did not exist).");

            // Insert a record into the "account" table using a prepared statement.
            String insertQuery = "INSERT INTO javabank.account (id, balance, currency, created_at, updated_at) " +
                                 "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedInsert = session.prepare(insertQuery);
            UUID accountId = UUID.randomUUID();
            BoundStatement boundInsert = preparedInsert.bind(
                    accountId,
                    BigDecimal.valueOf(77.56),
                    "USD",
                    Instant.now(),
                    Instant.now()
            );
            session.execute(boundInsert);
            System.out.println("Record inserted with id: " + accountId);

        } catch (Exception e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
        }
    }
}
