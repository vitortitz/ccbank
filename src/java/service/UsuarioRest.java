package service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;


import dao.UsuarioDao;
import domain.Usuario;

@Path("usuario")
public class UsuarioRest {
	// http://127.0.0.1:8080/ccbank-master/rest/usuario
	@GET
	public String listar() {
		UsuarioDao usuarioDao = new UsuarioDao();
		List<Usuario> usuarios = usuarioDao.findAll();

		Gson gson = new Gson();
		String json = gson.toJson(usuarios);

		return json;
	}

	// http://127.0.0.1:8080/Drogaria/rest/fabricante/{codigo}
	// http://127.0.0.1:8080/Drogaria/rest/fabricante/10
	@GET
	@Path("{codigo}")
	public String buscar(@PathParam("codigo") int codigo) {
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.findById(codigo);

		Gson gson = new Gson();
		String json = gson.toJson(usuario);

		return json;
	}

	// http://127.0.0.1:8080/Drogaria/rest/fabricante
	@POST
	public String salvar(String json) {
		Gson gson = new Gson();
		Usuario usuario = gson.fromJson(json, Usuario.class);

		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.insert(usuario);

		String jsonSaida = gson.toJson(usuario);
		return jsonSaida;
	}

	// http://127.0.0.1:8080/Drogaria/rest/fabricante
	@PUT
	public String editar(String json) {
		Gson gson = new Gson();
		Usuario usuario = gson.fromJson(json, Usuario.class);

		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.update(usuario);

		String jsonSaida = gson.toJson(usuario);
		return jsonSaida;
	}
	
	// http://127.0.0.1:8080/Drogaria/rest/fabricante
	@DELETE
	public String excluir(String json){
		Gson gson = new Gson();
		Usuario usuario = gson.fromJson(json, Usuario.class);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		usuario = usuarioDao.findById(usuario.getId());
		usuarioDao.delete(usuario);
		
		String saida = gson.toJson(usuario);
		return saida;
	}
}
