package dao;

import models.User;
import models.WishList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDao {

    public User findByLogin(String login) {
    return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, login);
    }

    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public WishList findListById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(WishList.class, id);
    }

    public List<User> findAll() {
        Query<User> query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from users u", User.class);
        return query.getResultList();
    }
}
