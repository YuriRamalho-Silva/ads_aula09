package ads.pipoca.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ads.pipoca.model.entity.Filme;
import ads.pipoca.model.entity.Genero;
import ads.pipoca.model.service.FilmeService;
import ads.pipoca.model.service.GeneroService;

@WebServlet("/manter_filmes.do")
public class ManterFilmesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ParametrosHelper helper = new ParametrosHelper(request);
		
		String acao = helper.getAcao();
		
		Filme filme = null;
		FilmeService fService = new FilmeService();
		GeneroService gService = new GeneroService();
		ArrayList<Genero> generos = null;
		String saida = "index.jsp";
		int idFilme = -1;
		ArrayList<Integer> listaIds = null;
		ArrayList<Filme> filmes = null;
		
	
		switch(acao) {
		case "menu-novo-menu-jsp":
			generos = gService.listarGeneros();
			request.setAttribute("generos", generos);
			saida = "InserirFilme.jsp";
			break;
		case "btn-visualizar-de-listar-filmes-jsp":
		case "btn-mostrar-de-mostrar-filme-jsp":
			if (acao.equals("btn-visualizar-de-listar-filmes-jsp")) {
				listaIds = helper.obterIds();
				idFilme = listaIds.get(0);
			} else {
				idFilme = helper.getIdFilme();
			}
			filme = fService.buscarFilme(idFilme);
			System.out.println(filme);
			request.setAttribute("filme", filme);
			saida = "Filme.jsp";
			break;
		case "btn-inserir-de-inserir-filme-jsp":
		    filme = helper.getFilme();
			int id = fService.inserirFilme(filme);
			filme.setId(id);
			System.out.println(filme);
			request.setAttribute("filme", filme);
			saida = "Filme.jsp";
			break;
		case "btn-editar-de-listar-filmes-jsp":
		case "btn-editar-de-filme-jsp":
			if (acao.equals("btn-editar-de-listar-filmes-jsp")) {
				listaIds = helper.obterIds();
				idFilme = listaIds.get(0);
			} else {
				idFilme = helper.getIdFilme();
			}
			generos = gService.listarGeneros();
			request.setAttribute("generos", generos);
			filme = fService.buscarFilme(idFilme);
			request.setAttribute("filme", filme);
			System.out.println(filme);
			saida = "AtualizarFilme.jsp";
			break;
		case "btn-atualizar-de-atualizar-filme-jsp":
			filme = helper.getFilme();
			System.out.println(filme);
			fService.atualizarFilme(filme);
			request.setAttribute("filme", filme);
			saida = "Filme.jsp";
			break;
		case "btn-excluir-de-modal-listar-filmes-jsp":
			listaIds = helper.obterIds();
			filmes = fService.excluirFilmes(listaIds);
			request.setAttribute("filmes", filmes);
			saida = "FilmeExcluido.jsp";
			break;
		case "btn-excluir-de-filme-jsp":
			idFilme = helper.getIdFilme();
			filme = fService.excluirFilme(idFilme);
			System.out.println(filme);
			filmes = new ArrayList<>();
			filmes.add(filme);
			request.setAttribute("filmes", filmes);
			saida = "FilmeExcluido.jsp";
			break;
		case "menu-listar-menu-jsp":
			filmes = fService.listarFilmes();
			request.setAttribute("filmes", filmes);
			saida = "ListaFilmes.jsp";
			break;
		}
		RequestDispatcher view = request.getRequestDispatcher(saida);
		view.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
