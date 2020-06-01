package ads.pipoca.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ads.pipoca.model.entity.Filme;
import ads.pipoca.model.service.FilmeService;

@WebServlet("/comprar_filmes.do")
public class ComprarFilmesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ParametrosHelper helper = new ParametrosHelper(request);
		
		String acao = helper.getAcao();
		FilmeService fService = new FilmeService();
		String saida = "index.jsp";
		HttpSession session = request.getSession();
		TreeSet<Filme> carrinho = null;
		ArrayList<Integer> lista = null;
		
		switch(acao) {
		case "btn-comprar-de-exibir-filmes-jsp":
			lista = helper.obterIds();
			ArrayList<Filme> filmes = fService.listarFilmes(lista);
			//pegar o carrinho da sessão e ver se já tem filmes
			Object aux = session.getAttribute("filmes");
			if(aux != null && aux instanceof TreeSet<?>) {
				carrinho = (TreeSet<Filme>)aux;
			} else {
				carrinho = new TreeSet<>();
			}
			for(Filme f:filmes) {
				carrinho.add(f);
			}
			session.setAttribute("filmes", carrinho);
			saida = "Carrinho.jsp";
			break;
		case "menu-comprar-filmes-de-menu-jsp":
			filmes = fService.listarFilmes();
			request.setAttribute("filmes", filmes);
			saida = "ExibirFilmes.jsp";
			break;
		case "btn-excluir-de-modal-carrinho-jsp":
			lista = helper.obterIds();
			//pegar o carrinho da sessão e ver se já tem filmes
			aux = session.getAttribute("filmes");
			if(aux != null && aux instanceof TreeSet<?>) {
				carrinho = (TreeSet<Filme>)aux;
				for(int id:lista) {
					Filme filme = new Filme();
					filme.setId(id);
					carrinho.remove(filme);
				}
			}
		}
		
		RequestDispatcher view = request.getRequestDispatcher(saida);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
