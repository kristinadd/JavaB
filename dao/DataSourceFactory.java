package dao;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
// import com.mysql.cj.jdbc.MysqlDataSource;


public class DataSourceFactory {
  private static DataSourceFactory instance = new DataSourceFactory("db.properties");
  private Properties properties;

  private DataSourceFactory(String file) {
    properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream(file));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static DataSourceFactory instance() {
    return instance;
  }

  // public DataSource getDataSource() {
  //   MysqlDataSource datasource = new MysqlDataSource();
  //     datasource.setURL(properties.getProperty(("DB_URL")));
  //     datasource.setUser(properties.getProperty(("DB_USER")));
  //     datasource.setPassword(properties.getProperty(("DB_PASSWORD")));

  //     return datasource;
  // }
}

// add maven 