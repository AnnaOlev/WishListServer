package dao;

import models.ListElement;
import models.WishList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class WishListDao {

    public WishList findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(WishList.class, id);
    }

    public void save(WishList wishList) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(wishList);
        tx1.commit();
        session.close();
    }

    public void update(WishList wishList) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(wishList);
        tx1.commit();
        session.close();
    }

    public void delete(WishList wishList) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(wishList);
        tx1.commit();
        session.close();
    }

    public ListElement findElementById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(ListElement.class, id);
    }

    public List<WishList> findAll() {
        Query<WishList> query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from wishLists w", WishList.class);
        return query.getResultList();
    }
}
