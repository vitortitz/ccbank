package dao;

import domain.Conta;
import domain.Historico;
import java.util.List;
import org.hibernate.Session;
import utils.HibernateUtil;

public class HistoricoDao {

    private final Session session;

    public HistoricoDao() {
        session = HibernateUtil.getSessionFactory().openSession();
    }


    public List<Historico> findAll(Conta conta) {
        session.beginTransaction();
        try {
            List<Historico> historicos = session.createQuery("from Historico h where h.conta_id = " + conta.getId()).list();
            session.getTransaction().commit();
            return historicos;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public Historico findById(Integer id) {
        session.beginTransaction();
        try {
            Historico historico = (Historico) session.createQuery("from Historico where id = " + id).uniqueResult();
            session.getTransaction().commit();
            return historico;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public boolean insert(Historico historico) {
        session.beginTransaction();
        try {
            session.save(historico);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println("DEU ERRADO");
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(Historico historico) {
        session.beginTransaction();
        try {
            session.update(historico);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean delete(Historico historico) {
        session.beginTransaction();
        try {
            session.delete(historico);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

}
