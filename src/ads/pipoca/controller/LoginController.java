package ads.pipoca.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ads.pipoca.model.entity.Usuario;
import ads.pipoca.model.service.UsuarioService;

@WebServlet("/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ParametrosHelper helper = new ParametrosHelper(request);
		Usuario usuario = helper.getUsuario();
		UsuarioService service = new UsuarioService();
		HttpSession session = request.getSession();
		String saida = "Login.jsp";
		String acao = request.getParameter("acao");
		
		switch(acao) {
		case "login":
		if (service.validar(usuario)) {
			session.setAttribute("logado", usuario);
			saida = "index.jsp";
		}
		break;
		
		case "logout":
			session.invalidate();
			break;
		}
		 
		RequestDispatcher view = request.getRequestDispatcher(saida);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
