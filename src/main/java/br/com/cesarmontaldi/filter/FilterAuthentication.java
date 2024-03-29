package br.com.cesarmontaldi.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Pessoa;

@WebFilter(urlPatterns = {"/*"})
public class FilterAuthentication implements Filter {
	
	@Inject
	private JpaUtil jpaUtil;
 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Pessoa userLogado = (Pessoa) session.getAttribute("userLogado");
		
		String url = req.getServletPath();
		
		if (!url.equalsIgnoreCase("index.jsf") && userLogado == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			return;
		}
		else {
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void init(FilterConfig filter) throws ServletException {
		
		jpaUtil.getEntityManager();
	}

}
