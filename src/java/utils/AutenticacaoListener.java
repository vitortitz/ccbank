/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import controller.UsuarioController;
import domain.Usuario;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.omnifaces.util.Faces;

/**
 *
 * @author vitor
 */
@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {

        String paginaAtual = Faces.getViewId();
        boolean paginaAutenticacao = paginaAtual.contains("login.xhtml");
        if (!paginaAutenticacao) {
            UsuarioController autenticacao = Faces.getSessionAttribute("usuarioMB");
            if (autenticacao == null) {
                Faces.navigate("/login.xhtml");
                return;
            }
            Usuario usuario = autenticacao.getUsuarioaLogar();
            if (usuario == null) {
                Faces.navigate("/login.xhtml");
                return;
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
