package fr.todooz.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.todooz.domain.DummyData;

public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		String name = request.getParameter("name"); 
		if (name == null) {
			name = (String) request.getSession(true).getAttribute("name");
	    }
		response.getWriter().write("Hello " + name + "!");
		**/
		
		//request.getRequestDispatcher("index.html").forward(request, response);
		
		/**
		request.setAttribute("name", request.getParameter("name"));
		request.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(request, response);
		**/
		
		request.setAttribute("tasks", DummyData.tasks());
		request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	}
}
