/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Cidade;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import service.CidadeService;
import utils.UtilMensagens;

/**
 *
 * @author wander
 */
@ManagedBean(name = "cidadeMB")
@SessionScoped
public class CidadeController implements Serializable {
    
    private CidadeService cidadeService;
    private Cidade cidade;
    
    public CidadeController () {
        cidadeService = new CidadeService();
        this.cidade = new Cidade();
    }
    
    public Cidade getCidade() {
        return this.cidade;
    }
    
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
   
    
    public String novo() { // ir para a página para inserir uma nova cidade  
        this.cidade = new Cidade();
        return "novo.xhtml";
    }
    
    public String inserirOuAtualizar(){
        if((cidade.getId() == null)){
            inserir();
        }else{
            atualizar();
        }
        return "listar.xhtml";
    }
    
    public String inserir() { // inserir uma nova cidade no Banco de Dados        
        if (cidadeService.inserir(cidade)) {      
            UtilMensagens.mensagemSucesso("Sucesso", "Cidade incluída com Sucesso");
            return "listar.xhtml";
        } else {
            UtilMensagens.mensagemErro("Erro", "Cidade não foi incluída");
            return null;
        }      
    }
    
    public String editar(Cidade cidade) { 
        this.cidade = cidade;
        return "editar.xhtml?faces-redirect=true";
    }
    
    public String atualizar() {
        if (cidadeService.editar(cidade)){
            UtilMensagens.mensagemSucesso("Sucesso", "Cidade editada com Sucesso");
            return "listar.xhtml?faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro", "Cidade não foi editada");
            return null;
        }      
    }
    
    public String excluir(Cidade cidade) {
        if(cidadeService.excluir(cidade)){
            UtilMensagens.mensagemSucesso("Sucesso", "Cidade excluida com Sucesso");
            return "listar.xhtml?faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro", "Cidade não foi excluida");
            return null;
        }       
    }
    
    public List<Cidade> getTodasCidades() {
        return cidadeService.getTodasCidades();
    }
}
