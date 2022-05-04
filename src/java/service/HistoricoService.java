/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.HistoricoDao;
import domain.Conta;
import domain.Historico;
import java.util.List;

/**
 *
 * @author wander
 */
public class HistoricoService {
    
    private HistoricoDao historicoDao;
    
    public HistoricoService() {
        // TODO: Construtor padrão
        historicoDao = new HistoricoDao();
    }
    
    public boolean inserir(Historico historico) {
        return historicoDao.insert(historico);
    }
    
    public boolean editar(Historico historico) {
        return historicoDao.update(historico);
    }
    
    public List<Historico> getTodosHistorico(Conta conta) {
        return historicoDao.findAll(conta);
    }
    
    public boolean excluir(Historico historico) {
        return historicoDao.delete(historico);
    }
}
