package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

// @WebServlet("/IndexController")
public class IndexController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
//		for(Cookie cookie : cookies) {
//			System.out.println("name: "+cookie.getName()+", value: "+cookie.getValue());
//		}
		
		System.out.println("initial index session cheking ...!");
		HashMap<String,String> map = new HashMap<String, String>();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if(session.isNew()) System.out.println("new : "+session.getAttribute("redirect"));
		Collections.list(session.getAttributeNames()).forEach(name -> System.out.println(name + " : " + session.getAttribute(name)));
		String redirect = (String) session.getAttribute("redirect");
		String isLogged = (String) session.getAttribute("isLogged");
		System.out.println("redirect : "+session.getAttribute("redirect"));
		map.put("redirect", redirect);
		map.put("isLogged", isLogged);
		map.put("result", "Welcome ....!");
		out.print(new Gson().toJson(map));
		out.close();
	}

}
