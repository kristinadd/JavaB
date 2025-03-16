package dao;

import com.datastax.oss.driver.api.core.CqlSession;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

public class CassandraSessionFactory {
    private static CassandraSessionFactory instance = new CassandraSessionFactory("db.properties");
    private Properties props;

    private CassandraSessionFactory(String fname) {
        props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream(fname));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static CassandraSessionFactory instance() {
        return instance;
    }

    public CqlSession getSession() {
        String host = props.getProperty("CASSANDRA_HOST", "localhost");
        int port = Integer.parseInt(props.getProperty("CASSANDRA_PORT", "9042"));
        String datacenter = props.getProperty("CASSANDRA_DATACENTER", "datacenter1");
        String keyspace = props.getProperty("CASSANDRA_KEYSPACE", "javabank");

        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(host, port))
                .withLocalDatacenter(datacenter)
                .withKeyspace(keyspace) // use specific keyspace
                .build();
    }
}
