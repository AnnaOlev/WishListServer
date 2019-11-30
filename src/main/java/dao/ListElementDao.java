package dao;

import models.ListElement;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class ListElementDao {
    public ListElement findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(ListElement.class, id);
    }

    public void save(ListElement listElement) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(listElement);
        tx1.commit();
        session.close();
    }

    public void update(ListElement listElement) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(listElement);
        tx1.commit();
        session.close();
    }

    public void delete(ListElement listElement) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(listElement);
        tx1.commit();
        session.close();
    }

    public List<ListElement> findAll() {
        Query<ListElement> query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from listElements l", ListElement.class);
        return query.getResultList();
    }
}
