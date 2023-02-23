package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.PermissionsModel;
import model.UserModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import auth.DataVerification;
import database.GetData;


@WebServlet("/SigninController")
public class SigninController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String data = request.getReader().lines().collect(Collectors.joining());
		Gson gson = new Gson();
		UserModel user = gson.fromJson(data, UserModel.class);
		HashMap map = new HashMap();

			try {
	            MessageDigest m = MessageDigest.getInstance("MD5");
	            m.update(user.getPassword().getBytes());
	            byte[] bytes = m.digest();
	            StringBuilder s = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++) 
	            	s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            user.setPassword(s.toString());
	            System.out.println("password : "+user.getPassword());
			}
			catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
			

			map.put("isResult", false);
			map.put("result", "Username or password is Wrong...!");
			boolean ischeck = DataVerification.usernameAndPassword(user.getUsername(),user.getPassword());
			if(ischeck) {
				HttpSession session = request.getSession(true);
				String query = "";
				boolean isGot = GetData.getPermissionsAndId(user.getUsername());
				PermissionsModel permissions = gson.fromJson(user.getPermissions(), PermissionsModel.class);
				
				if(permissions.isNewgroup_view() && isGot) {
					query = "select id from banks where adminId = "+user.getUserId();
					long bankId = GetData.getId(query);
					if(bankId == 0) {
						session.setAttribute("redirect","new-group");
						session.setAttribute("flag", false);
						map.put("redirect","new-group");
						map.put("flag", false);
						map.put("aId",user.getUserId());
						map.put("result", "data loading...!");
					}
					else {
						session.setAttribute("redirect","dashboard");
						session.setAttribute("isLogged", true);
						map.put("bId", bankId);
						map.put("uId", user.getUserId());
						map.put("flag", true);
						map.put("redirect", "dashboard");
						map.put("result", "data loading...!");
					}
				}
				else if(isGot) {
					query = "select bankId as id from savings where userId = "+user.getUserId();
					long bankId = GetData.getId(query);
					session.setAttribute("redirect","dashboard");
					session.setAttribute("flag", true);
					
					map.put("bId", bankId);
					map.put("uId", user.getUserId());
					map.put("flag", true);
					map.put("redirect", "dashboard");
					map.put("result", "data loading...!");

				}
			}
			
		out.print(new Gson().toJson(map));
		out.close();
	}

}
