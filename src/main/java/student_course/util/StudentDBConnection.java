package student_course.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StudentDBConnection {
    private static final Properties properties = new Properties();
    private static final HikariDataSource dataSource;

    static {
        try (InputStream is = StudentDBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("student.db.url"));
        config.setUsername(properties.getProperty("student.db.usr").trim());
        config.setPassword(properties.getProperty("student.db.pwd").trim());
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setPoolName("StudentCoursePool");
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
