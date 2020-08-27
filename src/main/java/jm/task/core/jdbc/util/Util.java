package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Properties properties;

    public static Connection getConnection() throws ClassNotFoundException, IOException {
        getProperties();

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getProperties() throws IOException {
        properties = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException(propFileName + " not found!");
        }
    }

    private static SessionFactory sessionFactory = null;

    /**
     * Open session.
     *
     * @return the session
     */
    public static Session openSession() {
        if (sessionFactory == null) {
            final Configuration configuration = new Configuration();

            sessionFactory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
        }
        return sessionFactory.openSession();
       /* try {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();

            return sessionFactory.openSession();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }*/
    }
}
