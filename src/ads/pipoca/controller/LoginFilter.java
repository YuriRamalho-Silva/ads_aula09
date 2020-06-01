package ads.pipoca.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ads.pipoca.model.entity.Usuario;

@WebFilter("/*")
public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		Usuario logado = (Usuario)session.getAttribute("logado");
		String path = req.getContextPath();
		String uri = req.getRequestURI();
		System.out.println("filter: path: "+path);
		System.out.println("filter: uri: "+uri);
		String acao = req.getParameter("acao");
		if (acao == null) {
			acao = "";
		}
		
		if (logado != null || 
				uri.equals(path+"/Login.jsp") ||
				acao.equals("login") ||
				uri.endsWith(".js") ||
				uri.endsWith(".css") ||
				uri.contains("fonts")) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(path+"/Login.jsp");
		}
		
	}

}
