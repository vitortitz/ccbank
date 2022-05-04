/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ContaDao;
import domain.Conta;
import java.util.List;

/**
 *
 * @author wander
 */
public class ContaService {
    
    private ContaDao contaDao;
    
    public ContaService() {
        // TODO: Construtor padrão
        contaDao = new ContaDao();
    }
    
    public boolean inserir(Conta conta) {
        return contaDao.insert(conta);
    }
    
    public boolean editar(Conta conta) {
        return contaDao.update(conta);
    }
    
    public List<Conta> getTodasContas() {
        return contaDao.findAll();
    }
    
    public List<Conta> getOutrasContas(int id){
        return  contaDao.findByIdDifferent(id);
    }
    
    public boolean excluir(Conta conta) {
        return contaDao.delete(conta);
    }
}
