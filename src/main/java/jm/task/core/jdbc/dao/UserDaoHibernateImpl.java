package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.bl.Util;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "lastName VARCHAR(100), " +
                    "age TINYINT" +
                    ")";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS users";

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            session.delete((User)session.load(User.class,id));

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> data;
        try (Session session = Util.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            data = session.createQuery(criteria).getResultList();

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            String sql = "TRUNCATE TABLE users";

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
