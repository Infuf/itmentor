package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    private static final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        logger.info("Trying to create table users with hibernate in method : createUsersTable()");

        try (var session = factory.openSession()) {
            session.beginTransaction();

            NativeQuery query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS test.users (" +
                    "id BIGSERIAL PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "lastname VARCHAR(100) NOT NULL," +
                    "age SMALLINT CHECK (age >= 0 AND age <= 120)" +
                    ");");

            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warning("Error creating table users in method : createUsersTable()");
        }
    }

    @Override
    public void dropUsersTable() {
        logger.info("Trying to drop table users with hibernate in method : dropUsersTable()");

        try (var session = factory.openSession()) {
            session.beginTransaction();

            NativeQuery query = session.createSQLQuery("DROP TABLE IF EXISTS test.users");

            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warning("Error dropping table users in method : dropUsersTable()");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        logger.info("Trying to save user with Hibernate in method : saveUser");

        User user = new User(name, lastName, age);


        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();

            session.save(user);

            transaction.commit();

        } catch (Exception e) {
            logger.info("Some problem occur while saving user in method : saveUser");
            e.printStackTrace();

        }
    }

    @Override
    public void removeUserById(long id) {
        logger.info("Trying to delete user with Hibernate in method : removeUserById()");

        User user = new User();
        user.setId(id);

        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();

            session.delete(user);

            transaction.commit();

        } catch (Exception e) {
            logger.info("Some problem occur while deleting user in method : removeUserById()");
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Trying to get all users in method : getAllUsers()");

        List<User> users = new ArrayList<>();

        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();

            String hql = "FROM User";

            Query<User> query = session.createQuery(hql, User.class);
            users = query.list();

            transaction.commit();
        } catch (Exception e) {
            logger.info("Something went wrong while getting all users in method : getAllUsers()");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        logger.info("Trying to clean all users in method : cleanUsersTable()");

        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();

            String hql = "DELETE FROM User";

            Query query = session.createQuery(hql);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            logger.info("Something went wrong while cleaning all users in method : cleanUsersTable()");
        }
    }
}
