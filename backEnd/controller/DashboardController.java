package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import auth.DataValidation;
import auth.DataVerification;
import database.GetData;
import database.SaveData;

@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {	
	private static final long serialVersionUID = 1L;
	private static PrintWriter out = null;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();
		
    	long userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		String method = (request.getParameter("meth") == null) ? "":request.getParameter("meth");
		
		HashMap map = new HashMap();
		map.put("isDatas", false);
		if(DataVerification.isUserBank(userId,bankId)) {		// user Vs bank
			
			if(method.equals("nav")) {
				map.put("result", "data successfully loaded...!");
				map.put("datas", GetData.getNavbar(userId,bankId));
				map.put("permissions", GetData.getPermissions(userId));
				map.put("isDatas", true);
			}
			else {
				map.put("result", "data successfully loaded...!");
				map.put("datas", GetData.getBank(bankId));
				map.put("isDatas", true);
			}
		}
		else {
			map.put("result", "Illegel user...!");
			map.put("isDatas", false);
		}
	out.print(new Gson().toJson(map));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();
		long userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("cPassword");
		String encryptPassword = "";
		boolean isUserBank = DataVerification.isUserBank(userId,bankId);
		HashMap map = new HashMap();
		
			
		if(isUserBank && !password.equals("") && !password.equals("")) {
			if(DataValidation.passwordRePassword(password,confirmPassword)) {
				try {
		            MessageDigest m = MessageDigest.getInstance("MD5");
		            m.update(password.getBytes());
		            byte[] bytes = m.digest();
		            StringBuilder s = new StringBuilder();
		            for(int i=0; i< bytes.length ;i++) 
		            	s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		            encryptPassword = s.toString();
				}
				catch(NoSuchAlgorithmException e){
					e.printStackTrace();
				}
				map.put("result","password reset failed(internal error)...!");
				map.put("isResult", false);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				map = GetData.updatedatAndDurationat(userId,bankId);
				map = DataVerification.getNextStoreDuration(((Date)map.get("updated_at")),((int)map.get("durations")));
				map.put("result","wait for your bank's durations  : "+dateFormat.format(map.get("durationAt"))+"...!");
				if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) == 0) {
					map = SaveData.passwordRePassword(userId,encryptPassword);
				}
				else if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) > 0) {
					map.put("result", "your not Eligiple this group...!");
				}
			}
			else {
				map.put("result","password and rePssword doesn't same ...!");
				map.put("isResult",false);
			}
		}
		out.print(new Gson().toJson(map));
	}
}
