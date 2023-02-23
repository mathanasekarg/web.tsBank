package controller;

import jakarta.servlet.http.HttpServlet;
import model.UserModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

import auth.DataVerification;

@WebServlet("/PasswordReset")
public class PasswordReset extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	@SuppressWarnings({ "rawtypes", "unused" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		long userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		String method = (request.getParameter("meth") == null) ? " ":request.getParameter("meth");
		String data = request.getReader().lines().collect(Collectors.joining());
		Gson gson = new Gson();
		UserModel user = gson.fromJson(data, UserModel.class);
		boolean isUserBank = DataVerification.isUserBank(userId,bankId);
		HashMap map = new HashMap();
		if(isUserBank) {
			
		}
		
	}

}
