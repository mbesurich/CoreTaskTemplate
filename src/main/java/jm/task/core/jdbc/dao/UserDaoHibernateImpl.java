package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name VARCHAR(45) NOT NULL,\n" +
                "    last_name VARCHAR(45) NOT NULL,\n" +
                "    age TINYINT NOT NULL)";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
//        session.delete(session.get(User.class, id));
//        при удалении из БД лучше использовать не get, а load. Так как load взаимодействует с БД через proxy. То есть
//        обращается не к БД, а к persistence context, если там нет нужного объекта, то обращается к БД только тогда,
//        когда с эим объектом нужно что-тосделать. То есть 1 обращение с БД; против 2 при get (1 - при get, 2 - при delete)
        session.delete(session.load(User.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> list = session.createQuery("FROM User").list();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
        session.close();
    }
}
