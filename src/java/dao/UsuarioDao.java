package dao;

import domain.Cidade;
import domain.Usuario;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.HibernateUtil;

public class UsuarioDao {

    private final Session session;

    public UsuarioDao() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void inicializarHibernate() {
        Session atual = HibernateUtil.getSessionFactory().getCurrentSession();
        atual.beginTransaction();
        atual.getTransaction().commit();
    }

    public List<Usuario> findAll() {
        session.beginTransaction();
        try {
            List<Usuario> usuarios = session.createQuery("from Usuario order by id").list();
            session.getTransaction().commit();
            return usuarios;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public Usuario findById(Integer id) {
        session.beginTransaction();
        try {
            Usuario usuario = (Usuario) session.createQuery("from Usuario where id = " + id).uniqueResult();
            session.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public Usuario findByLoginAndPasswd(String login, String senha) {
        session.beginTransaction();
        try {
            Query query = session.createQuery("from Usuario where login=:login and senha=:senha");
            query.setParameter("login", login);
            query.setParameter("senha", senha);
            Usuario usuario=(Usuario)query.uniqueResult();
            session.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public boolean insert(Usuario usuario) {
        session.beginTransaction();
        try {
            session.save(usuario);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(Usuario usuario) {
        session.beginTransaction();
        try {
            session.update(usuario);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean delete(Usuario usuario) {
        session.beginTransaction();
        try {
            session.delete(usuario);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

}
