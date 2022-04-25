/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;
import service.PessoaService;
import service.UsuarioService;
import utils.UtilMensagens;

/**
 *
 * @author wander
 */
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioController implements Serializable {

    private UsuarioService usuarioService;
    private PessoaService pessoaService;
    private Usuario usuario;
    private Usuario usuarioaLogar;

    public Usuario getUsuarioaLogar() {
        return usuarioaLogar;
    }

    public void setUsuarioaLogar(Usuario usuarioaLogar) {
        this.usuarioaLogar = usuarioaLogar;
    }

    public UsuarioController() {
        usuarioService = new UsuarioService();
        pessoaService = new PessoaService();
        usuario = new Usuario();
    }

    public String novo() { // ir para a página para inserir uma nova cidade  
        this.usuario = new Usuario();
        return "novo.xhtml";
    }

    public String inserirOuAtualizar() {
        if ((usuario.getId() == null)) {
            inserir();
        } else {
            atualizar();
        }
        return "listar.xhtml";
    }

    public String inserir() { // inserir uma nova cidade no Banco de Dados        
        if (usuarioService.inserir(usuario)) {
            getTodosUsuarios();
            UtilMensagens.mensagemSucesso("Sucesso", "Usuario incluído com Sucesso");
            return "listar.xhtml";
        } else {
            UtilMensagens.mensagemErro("Erro", "Usuario não foi incluída");
            return null;
        }
    }

    public String editar(Usuario usuario) {
        this.usuario = usuario;
        return "editar.xhtml?faces-redirect=true";
    }

    public String atualizar() {
        usuarioService.editar(usuario);
        UtilMensagens.mensagemSucesso("Sucesso", "Usuario alterado com Sucesso");
        return "listar.xhtml?faces-redirect=true";
    }

    public List<Usuario> getTodosUsuarios() {
        return usuarioService.getTodosUsuarios();
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String excluir(Usuario usuario) {
        usuarioService.excluir(usuario);

        return "listar.xhtml?faces-redirect=true";
    }

    public void inicializarHibernate() {
        usuarioService.inicializarHibernate();
    }

    

    public void autenticar() {
        usuarioaLogar = usuarioService.getUsuarioALogar(usuario.getLogin(), usuario.getSenha());
        
        if (usuarioaLogar == null) {
            PrimeFaces.current().executeScript("swal('ERRO', 'Senha/Usuário Incorretos!', 'error');");
            return;
        }
        PrimeFaces.current().executeScript("swal({\r\n"
                + "	    title: 'Sucesso!',\r\n"
                + "	    text: 'Logado Com Sucesso!',\r\n"
                + "	    type: 'success',	    \r\n"
                + "	}, function() {\r\n"
                + "	    location.href = '../../ccbank-master/index.xhtml';\r\n"
                + "	});");
    }
    public String sair() {
		usuarioaLogar = null;
		return "/login.xhtml?faces-redirect=true";
	}

}
