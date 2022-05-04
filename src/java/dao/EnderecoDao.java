package dao;

import domain.Endereco;
import java.util.List;
import org.hibernate.Session;
import utils.HibernateUtil;

public class EnderecoDao {

    private final Session session;
    
    public EnderecoDao() {
        session = HibernateUtil.getSessionFactory().openSession();
    }
     

    public List<Endereco> findAll(){
        session.beginTransaction();
        try{
            List<Endereco> enderecos = session.createQuery("from Endereco order by cidade_id").list();
            session.getTransaction().commit();
            return enderecos;
        }catch(Exception e){
            session.getTransaction().rollback();
            return null;
        }
    }

    public Endereco findById(Integer id){
        session.beginTransaction();
        try{
            Endereco endereco = (Endereco)session.createQuery("from Endereco where id = " + id).uniqueResult();
            session.getTransaction().commit();
            return endereco;
        }catch(Exception e){
            session.getTransaction().rollback();
            return null;
        }
    }

    public boolean insert(Endereco endereco){
        session.beginTransaction();
        try{            
            session.save(endereco);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(Endereco endereco){
        session.beginTransaction();
        try{
            session.update(endereco);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean delete(Endereco endereco){
        session.beginTransaction();
        try{
            session.delete(endereco);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            return false;
        }
    }
}
