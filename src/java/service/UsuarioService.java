/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CidadeDao;
import dao.UsuarioDao;
import domain.Cidade;
import domain.Usuario;
import java.util.List;

/**
 *
 * @author wander
 */
public class UsuarioService {
    
    private UsuarioDao usuarioDao;
    
    public UsuarioService() {
        // TODO: Construtor padrão
        usuarioDao = new UsuarioDao();
    }
    
    public boolean inserir(Usuario usuario) {
        return usuarioDao.insert(usuario);
    }
    
    public boolean editar(Usuario usuario) {
        return usuarioDao.update(usuario);
    }
    
    public List<Usuario> getTodosUsuarios() {
        return usuarioDao.findAll();
    }
    
    public boolean excluir(Usuario usuario) {
        return usuarioDao.delete(usuario);
    }
    
    public void inicializarHibernate() {
        usuarioDao.inicializarHibernate();
    }
    
    public Usuario getUsuarioALogar(String login, String senha){
     return usuarioDao.findByLoginAndPasswd(login, senha);
    }
}
