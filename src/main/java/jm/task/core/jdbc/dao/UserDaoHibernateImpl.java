package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.SQLQuery;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();

    public static SessionFactory getFactory() {
        return factory;
    }

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sglCommand = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sglCommand).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sglCommand = "DROP TABLE IF EXISTS users";
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sglCommand).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User where id = id").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.openSession()) {
            List<User> userList;
            userList = session.createQuery("from User", User.class).getResultList();
            return userList;
        }
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
