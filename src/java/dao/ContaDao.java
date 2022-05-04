package dao;

import domain.Cidade;
import domain.Conta;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utils.HibernateUtil;

public class ContaDao {

    private final Session session;

    public ContaDao() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public List<Conta> findAll() {
        session.beginTransaction();
        try {
            List<Conta> contas = session.createQuery("from Conta order by id").list();
            session.getTransaction().commit();
            return contas;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public Conta findById(Integer id) {
        session.beginTransaction();
        try {
            Conta conta = (Conta) session.createQuery("from Conta where id = " + id).uniqueResult();
            session.getTransaction().commit();
            return conta;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public List findByIdDifferent(Integer id) {
        session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(
                    Conta.class);
            criteria.add(Restrictions.not(Restrictions
                    .eq("id", id)));
            session.getTransaction().commit();
            List<Conta> contas = criteria.list();
            return contas;  
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public boolean insert(Conta conta) {
        session.beginTransaction();
        try {
            session.save(conta);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(Conta conta) {
        session.beginTransaction();
        try {
            session.update(conta);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean delete(Conta conta) {
        session.beginTransaction();
        try {
            session.delete(conta);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }
}
