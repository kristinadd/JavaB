// package dao;

// import com.datastax.oss.driver.api.core.CqlSession;
// import java.net.InetSocketAddress;

// public class CassandraConnection {
//     public static void main(String[] args) {
//         try (CqlSession session = CqlSession.builder()
//                 .addContactPoint(new InetSocketAddress("localhost", 9042))
//                 .withLocalDatacenter("datacenter1")
//                 .build()) {

//             System.out.println("Connected to Cassandra!");

//             session.execute("DESCRIBE KEYSPACES").forEach(row -> {
//                 System.out.println(row);
//             });
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }


package dao;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import java.net.InetSocketAddress;

public class CassandraConnection {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .build()) {

            System.out.println("Connected to Cassandra!");

            // Query the system_schema.keyspaces table to get keyspace names
            session.execute("SELECT keyspace_name FROM system_schema.keyspaces").forEach((Row row) -> {
                System.out.println(row.getString("keyspace_name"));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
