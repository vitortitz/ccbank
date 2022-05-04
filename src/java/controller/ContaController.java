/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PessoaDao;
import domain.Conta;
import domain.Historico;
import domain.Pessoa;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import service.ContaService;
import service.HistoricoService;
import utils.UtilMensagens;

/**
 *
 * @author wander
 */
@ManagedBean(name = "contaMB")
@SessionScoped
public class ContaController implements Serializable {

    private ContaService contaService;
    private HistoricoService historicoService;
    private Conta conta;
    private List<Pessoa> pessoas;
    private Historico historico, historicoRecebedor;
    private List<Conta> contasDiferentes;
    private List<Historico> historicoConta;
    private List<Conta> contasSemRepetir;

    public List<Conta> getContasSemRepetir() {
        return contasSemRepetir;
    }

    public void setContasSemRepetir(List<Conta> contasSemRepetir) {
        this.contasSemRepetir = contasSemRepetir;
    }

    public List<Historico> getHistoricoConta() {
        return historicoConta;
    }

    public void setHistoricoConta(List<Historico> historicoConta) {
        this.historicoConta = historicoConta;
    }

    public Historico getHistoricoRecebedor() {
        return historicoRecebedor;
    }

    public void setHistoricoRecebedor(Historico historicoRecebedor) {
        this.historicoRecebedor = historicoRecebedor;
    }

    public List<Conta> getContasDiferentes() {
        return contasDiferentes;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
    public Conta getConta() {
        return this.conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public ContaController() {
        contaService = new ContaService();
        historicoService = new HistoricoService();
        this.conta = new Conta();
        this.historico = new Historico();
        this.historicoRecebedor = new Historico();
        this.historicoConta = new ArrayList<>();
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
            UtilMensagens.mensagemSucesso("Sucesso", "Conta incluída com Sucesso");
            return "listar.xhtml";
        } else {
            UtilMensagens.mensagemErro("Erro", "Conta não foi incluída");
            return null;
        }
    }
    
        public String atualizar() {
        contaService.editar(conta);
        UtilMensagens.mensagemSucesso("Sucesso", "Conta editado com Sucesso");
        return "listar.xhtml?faces-redirect=true";
    }

    public String transferir(Conta contaDestino, Double valor, String observacao) {
        if (conta.getSaldo() < valor) {
            UtilMensagens.mensagemErro("Erro", "Saldo indispónivel");
            return null;
        } else {
            conta.setSaldo(conta.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
            if ((contaService.editar(conta)) && (contaService.editar(contaDestino))) {
                salvarHistoricoTransf(contaDestino, valor, observacao);
                UtilMensagens.mensagemSucesso("Sucesso", "Transfêrencia realizada com Sucesso");
                return "lsitar.xhtml";
            } else {
                UtilMensagens.mensagemErro("Erro", "Ocorreu um erro inesperado");
                return null;
            }
        }
    }
    public void salvarHistoricoTransf(Conta contaDestino, Double valor, String observacao) {
        this.historico = new Historico();
        historico.setContaId(conta);
        historico.setTipoMovimento("Saída");
        historico.setOperacao("T");
        historico.setValor(valor);
        historico.setDataHora(new Date());
        historico.setObservacao(observacao);
        this.historicoRecebedor = new Historico();
        historicoRecebedor.setContaId(contaDestino);
        historicoRecebedor.setTipoMovimento("Entrada");
        historicoRecebedor.setOperacao("T");
        historicoRecebedor.setValor(valor);
        historicoRecebedor.setDataHora(new Date());
        historicoRecebedor.setObservacao(observacao);
        if (historicoService.inserir(historico) && historicoService.inserir(historicoRecebedor)) {
            UtilMensagens.mensagemSucesso("Sucesso", "Historico Salvo com Sucesso");
        } else {
            UtilMensagens.mensagemErro("Erro", "Ocorreu um erro ao tentar salvar o histórico");
        }
    }

    public String sacar(Double valor) {
        if (valor > conta.getSaldo()) {
            UtilMensagens.mensagemErro("Erro", "Saldo insuficiente");
            return null;
        } else {
            conta.setSaldo(conta.getSaldo() - valor);
            salvarHistoricoSaque(valor);
            if (contaService.editar(conta)) {
                UtilMensagens.mensagemSucesso("Sucesso", "Saque realizado com Sucesso");
                return "listar.xhtml";
            } else {
                UtilMensagens.mensagemErro("Erro", "Ocorreu um erro inesperado");
                return null;
            }
        }
    }

    public void salvarHistoricoSaque(Double valor) {
        historico = new Historico();
        historico.setContaId(conta);
        historico.setTipoMovimento("Saída");
        historico.setOperacao("S");
        historico.setValor(valor);
        historico.setDataHora(new Date());
        if (historicoService.inserir(historico)) {
            UtilMensagens.mensagemSucesso("Sucesso", "Historico Salvo com Sucesso");
        } else {
            UtilMensagens.mensagemErro("Erro", "Ocorreu um erro ao tentar salvar o histórico");
        }
    }
    

    public String depositar(Double valor) throws ParseException {
        double valorEsperado = conta.getSaldo() + valor;
        if (valorEsperado <= conta.getLimite()) {
            conta.setSaldo(valorEsperado);
            contaService.editar(conta);
            salvarHistoricoDeposito(valor);
            UtilMensagens.mensagemSucesso("Sucesso", "Depósito realizado com Sucesso");
            return "listar.xhtml";
        } else {
            UtilMensagens.mensagemErro("Erro", "Valor depositado excederá o limite.");
            return null;
        }
    }
    
    public void salvarHistoricoDeposito(Double valor) {
        historico = new Historico();
        historico.setContaId(conta);
        historico.setTipoMovimento("Entrada");
        historico.setOperacao("D");
        historico.setValor(valor);
        historico.setDataHora(new Date());
        if (historicoService.inserir(historico)) {
            UtilMensagens.mensagemSucesso("Sucesso", "Historico Salvo com Sucesso");
        } else {
            UtilMensagens.mensagemErro("Erro", "Ocorreu um erro ao tentar salvar o histórico");
        }
    }



    public List<Conta> getTodasContas() {
        List<Conta> contas = contaService.getTodasContas();
        return contas;
    }

    public String excluir(Conta conta) {
        contaService.excluir(conta);
        return "listar.xhtml?faces-redirect=true";
    }

    public String editar(Conta conta) {
        this.conta = conta;
        contasDiferentes = contaService.getOutrasContas(conta.getId());
        Map<String, Conta> map = new HashMap();
        contasDiferentes.forEach(d -> map.put(String.valueOf(conta.getId()), d));
        contasSemRepetir = new ArrayList<>(map.values());
        return "editar.xhtml?faces-redirect=true";
    }
}
