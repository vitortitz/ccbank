/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CidadeDao;
import dao.UsuarioDao;
import domain.Cidade;
import domain.Endereco;
import domain.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import service.EnderecoService;
import utils.UtilMensagens;

/**
 *
 * @author wander
 */
@ManagedBean(name = "enderecoMB")
@SessionScoped
public class EnderecoController implements Serializable {

    private EnderecoService enderecoService;
    private Endereco endereco;
    private List<Usuario> usuarios;
    private List<Cidade> cidades;

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
    
    

    public EnderecoController() {
        enderecoService = new EnderecoService();
        this.endereco = new Endereco();
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String novo() { // ir para a página para inserir uma nova cidade  
        this.endereco = new Endereco();
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarios = usuarioDao.findAll();
        CidadeDao cidadeDao = new CidadeDao();
        cidades = cidadeDao.findAll();
        return "novo.xhtml";
    }

    public String inserirOuAtualizar() {
        if ((endereco.getId() == null)) {
            inserir();
        } else {
            atualizar();
        }
        return "listar.xhtml";
    }

    public String inserir() { // inserir uma nova cidade no Banco de Dados        
        if (enderecoService.inserir(endereco)) {
            UtilMensagens.mensagemSucesso("Sucesso", "Endereço incluído com Sucesso");
            return "listar.xhtml";
        } else {
            UtilMensagens.mensagemErro("Erro", "Endereço não foi incluído");
            return null;
        }
    }

    public String editar(Endereco endereco) {
        this.endereco = endereco;
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarios = usuarioDao.findAll();
        CidadeDao cidadeDao = new CidadeDao();
        cidades = cidadeDao.findAll();
        return "editar.xhtml?faces-redirect=true";
    }

    public String atualizar() {
        if (enderecoService.editar(endereco)) {
            UtilMensagens.mensagemSucesso("Sucesso", "Endereço editado com Sucesso");
            return "listar.xhtml?faces-redirect=true";
        } else {
            UtilMensagens.mensagemErro("Erro", "Endereç não foi editada");
            return null;
        }
    }

    public String excluir(Endereco endereco) {
        if (enderecoService.excluir(endereco)) {
            UtilMensagens.mensagemSucesso("Sucesso", "Endereço excluido com Sucesso");
            return "listar.xhtml?faces-redirect=true";
        } else {
            UtilMensagens.mensagemErro("Erro", "Endereço não foi excluido");
            return null;
        }
    }

    public List<Endereco> getTodosEnderecos() {
        return enderecoService.getTodosEnderecos();
    }
}
