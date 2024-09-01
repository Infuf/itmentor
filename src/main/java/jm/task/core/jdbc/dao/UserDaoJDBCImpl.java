package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@NoArgsConstructor
public class UserDaoJDBCImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());


    public void createUsersTable() {
        logger.info("Trying to create table in method : createUsersTable()");

        String createSqlTable = "CREATE TABLE IF NOT EXISTS test.users (" +
                "id BIGSERIAL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "lastname VARCHAR(100) NOT NULL," +
                "age SMALLINT CHECK (age >= 0 AND age <= 120)" +
                ");";

        try (var connection = Util.connectJDBC()) {
            var statement = connection.createStatement();
            statement.execute(createSqlTable);

        } catch (SQLException e) {
            logger.info("Failed to create table in method : createUsersTable()");
        }
    }

    public void dropUsersTable() {
        logger.info("Trying to drop users table in method : dropUsersTable()");

        String dropUsers = "DROP TABLE IF EXISTS test.users";

        try (var connection = Util.connectJDBC()) {
            var statement = connection.createStatement();
            statement.execute(dropUsers);

        } catch (SQLException e) {
            logger.warning("Failed to drop users table in method : dropUsersTable()");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        logger.info("Trying to add user in method : saveUser()");

        String insertUser = "INSERT INTO test.users (name, lastname, age) VALUES(?, ?, ?)";

        try (var connection = Util.connectJDBC()) {
            var prepareStatement = connection.prepareStatement(insertUser);
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setInt(3, age);
            prepareStatement.execute();

        } catch (SQLException e) {
            logger.warning("Failed to add user in method : saveUser()");
        }

    }

    public void removeUserById(long id) {
        logger.info("Trying to delete user in method : removeUserById()");

        String deleteUser = "DELETE FROM test.users WHERE id = ?";

        try (var connnection = Util.connectJDBC()) {
            var prepareStatement = connnection.prepareStatement(deleteUser);
            prepareStatement.setLong(1, id);
            prepareStatement.execute();

        } catch (SQLException e) {
            logger.warning("Failed to delete user in method : removeUserById()");
        }

    }

    public List<User> getAllUsers() {
        logger.info("Trying to get all users in method : getAllUsers()");

        List<User> users = new ArrayList<>();
        String queryToGetUsers = "SELECT * FROM test.users";

        try (var connnection = Util.connectJDBC()) {
            var statement = connnection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryToGetUsers);

            while (resultSet.next()) {
                User user = new User();

                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");

                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                users.add(user);
            }

        } catch (SQLException e) {
            logger.warning("Failed to get all users in method : getAllUsers()");
        }
        return users;
    }

    public void cleanUsersTable() {
        logger.info("Trying to clean users table in method : cleanUsersTable()");

        String queryToCleanUsersTable = "TRUNCATE TABLE test.users";

        try (var connnection = Util.connectJDBC()) {
            var statement = connnection.createStatement();
            statement.execute(queryToCleanUsersTable);

        } catch (SQLException e) {
            logger.warning("Failed to clean users table in method : cleanUsersTable()");
        }
    }
}
