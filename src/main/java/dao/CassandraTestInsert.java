package dao;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.UUID;

public class CassandraTestInsert {
    public static void main(String[] args) {
        // Connect to Cassandra using the keyspace "javabank"
        try (CqlSession session = CqlSession.builder()
                .withKeyspace("javabank")
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .build()) {

            System.out.println("Connected to Cassandra!");

            // Create the table "account" if it doesn't already exist.
            String createTableQuery = "CREATE TABLE IF NOT EXISTS account (" +
                                      "account_id UUID PRIMARY KEY, " +
                                      "account_holder text, " +
                                      "balance decimal, " +
                                      "opened_date timestamp" +
                                      ");";
            session.execute(createTableQuery);
            System.out.println("Table 'account' created (if it did not exist).");

            // Insert a record into the "account" table using a prepared statement.
            String insertQuery = "INSERT INTO account (account_id, account_holder, balance, opened_date) " +
                                 "VALUES (?, ?, ?, ?);";
            PreparedStatement preparedInsert = session.prepare(insertQuery);
            UUID accountId = UUID.randomUUID();
            BoundStatement boundInsert = preparedInsert.bind(
                    accountId,
                    "Test Account",
                    BigDecimal.valueOf(1234.56),
                    Instant.now()
            );
            session.execute(boundInsert);
            System.out.println("Record inserted with account_id: " + accountId);

            // Optionally, query the record to verify it was inserted.
            String selectQuery = "SELECT account_id, account_holder, balance, opened_date FROM account WHERE account_id = ?;";
            PreparedStatement preparedSelect = session.prepare(selectQuery);
            BoundStatement boundSelect = preparedSelect.bind(accountId);
            ResultSet resultSet = session.execute(boundSelect);
            Row row = resultSet.one();
            if (row != null) {
                System.out.println("Retrieved record:");
                System.out.println("Account ID: " + row.getUuid("account_id"));
                System.out.println("Account Holder: " + row.getString("account_holder"));
                System.out.println("Balance: " + row.getBigDecimal("balance"));
                System.out.println("Opened Date: " + row.getInstant("opened_date"));
            } else {
                System.out.println("Record not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
