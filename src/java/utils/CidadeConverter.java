package utils;


import dao.UsuarioDao;
import domain.Usuario;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("usuarioConverter")
public class CidadeConverter implements Converter,Serializable {

    @Override
    public Object getAsObject(FacesContext faces, UIComponent component, String valor) {

       try {
            int codigo = Integer.parseInt(valor);
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario usuario = usuarioDao.findById(codigo);
            return usuario;
        } catch (RuntimeException ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext faces, UIComponent component, Object objeto) {
        if (!objeto.equals("")) {
            System.out.println(objeto);
            try {
                Usuario usuario = (Usuario) objeto;
                int codigo = usuario.getId();
                System.out.println(codigo);
                return usuario.toString();
            } catch (RuntimeException ex) {
                return null;
            }
        }
        return "";
    }

}