package ads.pipoca.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import ads.pipoca.model.entity.Filme;
import ads.pipoca.model.entity.Genero;
import ads.pipoca.model.entity.Usuario;
import ads.pipoca.model.service.GeneroService;

public class ParametrosHelper {
	private HttpServletRequest request;

	public ParametrosHelper(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getAcao() {
		String acao = request.getParameter("acao");
		if (acao == null) {
			return "desconhecida";
		} else {
			return acao;
		}
	}
	
	public ArrayList<Integer> obterIds(){
		Enumeration<String> pars = request.getParameterNames();
		ArrayList<Integer> listaIds = new ArrayList<>();
		String par;
		String[] vals = null;
		
		try {
			while ((par = pars.nextElement()) != null) {
				if (par.startsWith("box")) {
					System.out.println(par +" = "+Arrays.toString(request.getParameterValues(par)));
					vals = request.getParameterValues(par);
					if (vals != null && vals.length > 0 && vals[0].equals("on")) {
						listaIds.add(Integer.parseInt(par.substring(3)));
					}
				}
			}
		} catch(NoSuchElementException nsee) {
		}
		return listaIds;
	}
	
	public int getIdFilme() {
		String id_filme = request.getParameter("id_filme");
		if (id_filme != null) {
			try {
				return Integer.parseInt(id_filme);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	public Filme getFilme() throws IOException {
		int idFilme = getIdFilme();
		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String diretor = request.getParameter("diretor");
		String idGenero = request.getParameter("genero");
		String data = request.getParameter("data_lancamento");
		String popularidade = request.getParameter("popularidade");
		String posterPath = request.getParameter("poster_path");
		Filme filme = new Filme();
		filme.setId(idFilme);
		filme.setTitulo(titulo);
		filme.setDescricao(descricao);
		filme.setDiretor(diretor);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date dataLanc = null;
		try {
			dataLanc = formater.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		filme.setDataLancamento(dataLanc);
		filme.setPopularidade(Double.parseDouble(popularidade));
		filme.setPosterPath(posterPath);
		GeneroService gService = new GeneroService();
		Genero genero = gService.buscarGenero(Integer.parseInt(idGenero));
		filme.setGenero(genero);
		return filme;
	}
	
	public Usuario getUsuario() {
		Usuario usuario = new Usuario();
		String username = request.getParameter("username");
		String password = request.getParameter("passwd");
		usuario.setUsername(username);
		usuario.setPassword(password);
		return usuario;
	}
}






