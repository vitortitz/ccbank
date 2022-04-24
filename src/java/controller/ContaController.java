/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PessoaDao;
import domain.Conta;
import domain.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import service.ContaService;
import utils.UtilMensagens;

/**
 *
 * @author wander
 */
@ManagedBean(name = "contaMB")
@SessionScoped
public class ContaController implements Serializable {

    private ContaService contaService;
    private Conta conta;
    private List<Pessoa> pessoas;

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public ContaController() {
        contaService = new ContaService();
        this.conta = new Conta();
    }

    public String novo() { // ir para a página para inserir uma nova cidade  
        this.conta = new Conta();
        PessoaDao pessoaDao = new PessoaDao();
        pessoas = pessoaDao.findAll();
        return "novo.xhtml";
    }

    public String inserirOuAtualizar() {
        if ((conta.getId() == null)) {
            inserir();
        } else {
            atualizar();
        }
        return "listar.xhtml";
    }

    public String inserir() { // inserir uma nova conta no Banco de Dados
        if (contaService.inserir(conta)) {      
            getTodasContas();
            UtilMensagens.mensagemSucesso("Sucesso", "Conta incluída com Sucesso");
            return "listar.xhtml";
        } else {
            UtilMensagens.mensagemErro("Erro", "Conta não foi incluída");
            return null;
        } 
    }

    public String movimentar(Conta conta) {
        this.conta = conta;
        return "editar.xhtml?faces-redirect=true";
    }

    public String atualizar() {
        contaService.editar(conta);
        UtilMensagens.mensagemSucesso("Sucesso", "Conta editado com Sucesso");
        return "listar.xhtml?faces-redirect=true";
    }

    public List<Conta> getTodasContas() {
        return contaService.getTodasContas();
    }

    public Conta getConta() {
        return this.conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String excluir(Conta conta) {
        contaService.excluir(conta);

        return "listar.xhtml?faces-redirect=true";
    }

}
