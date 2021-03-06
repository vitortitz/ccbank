/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CidadeDao;
import dao.UsuarioDao;
import domain.Cidade;
import domain.Pessoa;
import domain.Usuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import service.PessoaService;
import utils.UtilMensagens;

/**
 *
 * @author wander
 */
@ManagedBean(name = "pessoaMB")
@SessionScoped
public class PessoaController implements Serializable {
    
    private PessoaService pessoaService;
    private Pessoa pessoa;
    private List<Pessoa> pessoas;
    private List<Usuario> usuarios;
    private List<Cidade> cidades;

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
     public Pessoa getPessoa() {
        return this.pessoa;
    }
    
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
    
    public PessoaController () {
        pessoaService = new PessoaService();
        this.pessoa = new Pessoa();
    }
    
    public Date getMinAge() {
	    Calendar currentDate = Calendar.getInstance();
	    currentDate.add(Calendar.YEAR, -18);	   
	    return currentDate.getTime();
	}
	
	public Date getMaxAge() {
	Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.YEAR, -120);  
        return currentDate.getTime();
	}
    
   
    public String novo() { // ir para a página para inserir uma nova cidade  
        this.pessoa = new Pessoa();
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarios = usuarioDao.findAll();
        CidadeDao cidadeDao = new CidadeDao();
        cidades = cidadeDao.findAll();
        return "novo.xhtml";
    }
    
    public String inserirOuAtualizar(){
        if((pessoa.getId() == null)){
            inserir();
        }else{
            atualizar();
        }
        return "listar.xhtml";
    }
    
    public String inserir() { // inserir uma nova cidade no Banco de Dados        
        if (pessoaService.inserir(pessoa)) {      
            getTodasPessoas();
            UtilMensagens.mensagemSucesso("Sucesso", "Pessoa incluída com Sucesso");
            return "listar.xhtml";
        } else {
            UtilMensagens.mensagemErro("Erro", "Pessoa não foi incluída");
            return null;
        }      
    }
    public String editar(Pessoa pessoa) { 
        this.pessoa = pessoa;
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarios = usuarioDao.findAll();
        CidadeDao cidadeDao = new CidadeDao();
        cidades = cidadeDao.findAll();
        return "editar.xhtml?faces-redirect=true";
    }
    
    public String atualizar() {
        if(pessoaService.editar(pessoa)){
            UtilMensagens.mensagemSucesso("Sucesso", "Pessoa editada com Sucesso");
        return "listar.xhtml?faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro", "Pessoa não foi excluida");
            return null;
        }     
    }
    
    public List<Pessoa> getListaPessoas(Usuario usuario) {
        if (null == usuario) {
            return getTodasPessoas();
        } else {
            return pessoaService.listarPessoas(usuario);
        }
    }
    
    public List<Pessoa> getTodasPessoas() {
        return pessoaService.getTodasPessoas();
    }
    
   
    
    
    public String excluir(Pessoa pessoa) {
        pessoaService.excluir(pessoa);
        
        return "listar.xhtml?faces-redirect=true";
    }
}
