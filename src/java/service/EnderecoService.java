/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EnderecoDao;
import domain.Cidade;
import domain.Endereco;
import java.util.List;

/**
 *
 * @author wander
 */
public class EnderecoService {
    
    private EnderecoDao enderecoDao;
    
    public EnderecoService() {
        // TODO: Construtor padrão
        enderecoDao = new EnderecoDao();
    }
    
    public boolean inserir(Endereco endereco) {
        return enderecoDao.insert(endereco);
    }
    
    public boolean editar(Endereco endereco) {
        return enderecoDao.update(endereco);
    }
    
    public List<Endereco> getTodosEnderecos() {
        return enderecoDao.findAll();
    }
    
    public boolean excluir(Endereco endereco) {
        return enderecoDao.delete(endereco);
    }
}
