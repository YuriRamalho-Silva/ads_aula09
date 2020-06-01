package ads.pipoca.model.service;

import java.io.IOException;

import ads.pipoca.model.dao.UsuarioDAO;
import ads.pipoca.model.entity.Usuario;

public class UsuarioService {
	
	public boolean validar(Usuario usuario) throws IOException{
		UsuarioDAO dao = new UsuarioDAO();
		return dao.validar(usuario);
	}
}
