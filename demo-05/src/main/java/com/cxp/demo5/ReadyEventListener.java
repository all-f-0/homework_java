package com.cxp.demo5;

import com.cxp.demo05.starter.demo.School;
import com.cxp.demo5.demo.Demo1;
import com.cxp.demo5.demo.Demo2;
import com.cxp.demo5.demo.Demo3;
import com.cxp.demo5.demo.Demo4;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

@Component
public class ReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext context = event.getApplicationContext();
        System.out.println(context.getBean(Demo1.class));
        System.out.println(context.getBean(Demo2.class));
        System.out.println(context.getBean(Demo3.class));
        System.out.println(context.getBean(Demo4.class));

        if (context.containsBean("school")) {
            School school = context.getBean(School.class);
            school.ding();
            school.getStudent100().print();
            school.getClass1().dong();
        }

        try {
            jdbcDemo();
            preparedStatementDemo();
            hikariDemo(context);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void hikariDemo(ApplicationContext context) throws SQLException {
        HikariDataSource dataSource = context.getBean(HikariDataSource.class);
        System.out.println(dataSource.getClass());
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection.getClass());
            try (Statement statement = connection.createStatement();) {
                statement.execute("truncate table datas.demo");
            }
            try (PreparedStatement insertStatement
                         = connection.prepareStatement("insert into datas.demo (id, demo) values (?, ?)")) {
                insertStatement.setInt(1, 1);
                insertStatement.setString(2, "demo");
                insertStatement.addBatch();
                insertStatement.setInt(1, 2);
                insertStatement.setString(2, "demo");
                insertStatement.addBatch();
                insertStatement.executeBatch();
            }

            try (PreparedStatement prepareStatement
                         = connection.prepareStatement("update datas.demo set demo = ? where id = ?")) {
                prepareStatement.setString(1, "demo1");
                prepareStatement.setInt(2, 1);
                prepareStatement.execute();
            }

            try (PreparedStatement statement
                         = connection.prepareStatement("delete from datas.demo where id = ?")) {
                statement.setInt(1, 1);
                statement.execute();
            }

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("select * from datas.demo")) {
                while (resultSet.next()) {
                    printResultSet(resultSet);
                }
            }
        }
    }

    private void preparedStatementDemo() throws SQLException {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement();) {
                statement.execute("truncate table datas.demo");
            }
            try (PreparedStatement insertStatement
                         = connection.prepareStatement("insert into datas.demo (id, demo) values (?, ?)")) {
                insertStatement.setInt(1, 1);
                insertStatement.setString(2, "demo");
                insertStatement.addBatch();
                insertStatement.setInt(1, 2);
                insertStatement.setString(2, "demo");
                insertStatement.addBatch();
                insertStatement.executeBatch();
            }

            try (PreparedStatement prepareStatement
                         = connection.prepareStatement("update datas.demo set demo = ? where id = ?")) {
                prepareStatement.setString(1, "demo1");
                prepareStatement.setInt(2, 1);
                prepareStatement.execute();
            }

            try (PreparedStatement statement
                    = connection.prepareStatement("delete from datas.demo where id = ?")) {
                statement.setInt(1, 1);
                statement.execute();
            }

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("select * from datas.demo")) {
                while (resultSet.next()) {
                    printResultSet(resultSet);
                }
            }
        }
    }

    private void jdbcDemo() throws SQLException {
        try (Connection connection = getConnection()) {

            try (Statement statement = connection.createStatement();) {
                statement.execute("truncate table datas.demo");
            }
            try (Statement statement = connection.createStatement();) {
                statement.execute("insert into datas.demo (id, demo) values (1, 'demo')");
            }
            try (Statement statement = connection.createStatement();) {
                statement.execute("insert into datas.demo (id, demo) values (2, 'demo')");
            }
            try (Statement statement = connection.createStatement();) {
                statement.execute("update datas.demo set demo = 'demo1' where id = 1");
            }
            try (Statement statement = connection.createStatement();) {
                statement.execute("delete from datas.demo where id = 2");
            }

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("select * from datas.demo")) {
                while (resultSet.next()) {
                    printResultSet(resultSet);
                }
            }
        }
    }

    private Connection getConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "123456");
        properties.setProperty("useSSL", "false");
        Connection connection
                = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.237.129:3306", properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private void printResultSet(ResultSet resultSet) throws SQLException {
        for (int i = 1; i <= resultSet.getMetaData()
                .getColumnCount(); i++) {
            System.out.println(String.format("%s:%s",
                    resultSet.getMetaData().getColumnName(i),
                    resultSet.getString(i)));;
        }
    }
}
