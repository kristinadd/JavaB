package dao;

import com.datastax.oss.driver.api.core.CqlSession;
import java.net.InetSocketAddress;

public class CassandraConnection {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .build()) {

            System.out.println("Connected to Cassandra!");

            session.execute("DESCRIBE KEYSPACES").forEach(row -> {
                System.out.println(row);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

