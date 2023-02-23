package controller;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import auth.DataValidation;
import auth.DataVerification;
import database.GetData;
import database.SaveData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.PermissionsModel;
import model.UserModel;

@WebServlet("/SignupController")
public class SignupController extends HttpServlet {
       
	private static final long serialVersionUID = -2453205309463947850L;

	@SuppressWarnings({ "unchecked", "rawtypes", "unlikely-arg-type" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		long userId,bankId,editId;
		String country;
		try {
			editId = (request.getParameter("editId") == null) ? 0:Long.parseLong(request.getParameter("editId"));
			userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
			bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
			country = (request.getParameter("counrty") == null) ? "":request.getParameter("counrty");
		} catch(NumberFormatException e) {
			userId=0;
			bankId=0;
			country="0";
			editId = 0;
		}
		country = request.getParameter("country");
		String method = request.getParameter("meth") == null ?"":request.getParameter("meth");
		HashMap map = new HashMap();
		boolean isUserBank = DataVerification.isUserBank(userId,bankId);
		if(isUserBank && editId != 0) isUserBank = DataVerification.isUserBank(editId,bankId);
		PermissionsModel permissions = GetData.getPermissions(userId);
		if((isUserBank && !permissions.isUsers_details()) && (isUserBank && !permissions.isNewgroup_view())) {
			map.put("isData", false);
			map.put("result", "permission denied...!!");
		}
		
		else if((isUserBank && method.equals("list") && permissions.isUsers_view()) || (isUserBank && method.equals("list") && permissions.isNewgroup_view())) {
			
				map.put("datas",GetData.getUsersInBank(bankId));
				map.put("result","data loaded successfully...!");
				map.put("permission",permissions.isUsers_edit());
			
			map.put("isData", true);
		}
		else if((isUserBank && method.equals("details") && permissions.isUsers_details()) || (isUserBank && method.equals("details") && permissions.isNewgroup_view())) {
			map.put("datas",GetData.getUser(editId));
			ArrayList<HashMap> list = new ArrayList<HashMap>();
			list = GetData.getPermissions();
			if(!permissions.isNewgroup_view()) {
//				list.remove(Integer.valueOf("newgroup_view"));
//				list.remove(Integer.valueOf("users_edit"));
				list.remove(19);//newgroup_view
				list.remove(25);//user_edit
			}
			map.put("gender",GetData.getGender());
			map.put("permissions",list);
			map.put("country",GetData.getCountry());
			map.put("category",GetData.getCategory());
			map.put("isData", true);
			System.out.println("map : "+map);
			map.put("profession",GetData.getProfession());
			map.put("result","data loaded successfully...!");
			
		}
		else if((isUserBank && (method.equals("load") || method.equals("state")) && permissions.isUsers_view()) || (isUserBank && (method.equals("load") || method.equals("state")) && permissions.isNewgroup_view())) {
			if(method.equals("state") && country != "0") {
				map.put("state",GetData.getState(country));
				map.put("result","data loaded successfully...!");
				map.put("isData", true);
			}
			else if(method.equals("load")){
				ArrayList<HashMap> list = new ArrayList();
				list = GetData.getPermissions();
				if(!permissions.isNewgroup_view()) {
//					for(HashMap arr:list)
//						System.out.println("Edit : "+arr);
//					System.out.println("Edit : "+list.get(19));
//					list.remove(Integer.valueOf("newgroup_view"));
//					list.remove(Integer.valueOf("users_edit"));
					list.remove(19);//newgroup_view
					list.remove(25);//user_edit
				}
				map.put("share_amounts", GetData.getShare_amounts(bankId));
				map.put("gender",GetData.getGender());
				map.put("permissions",list);
				map.put("country",GetData.getCountry());
				map.put("category",GetData.getCategory());
				map.put("isData", true);
			
				map.put("profession",GetData.getProfession());
				map.put("result","data loaded successfully...!");
			}
			else {
				map.put("isData", false);
				map.put("result", "please check your url...!");
			}
		}
		else {
			if(method.equals("load") && userId == 0 && bankId == 0){
				map.put("gender",GetData.getGender());
				map.put("isNewAdmin",true);
				map.put("permissions",GetData.getPermissions());
				map.put("country",GetData.getCountry());
				map.put("category",GetData.getCategory());
				map.put("isData", true);
				
				map.put("profession",GetData.getProfession());
				map.put("isData", true);
				map.put("result", "...!");
				map.put("result","data loaded successfully...!");
			}
			else if(method.equals("state")) {
				System.out.println("counrt : "+country);
				map.put("state",GetData.getState(country));
				map.put("result","data loaded successfully....!");
				map.put("isData", true);
			}
			else {
				map.put("isData", false);
				map.put("result","illegel user...!");
			}
		}
		
		out.print(new Gson().toJson(map));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		long userId,bankId;
		try {
			userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
			bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		} catch(NumberFormatException e) {
			userId=0;
			bankId=0;
		}
		String method = request.getParameter("meth") == null ?"":request.getParameter("meth");
		String data = request.getReader().lines().collect(Collectors.joining());
		JSONParser parser = new JSONParser();
		HashMap map = new HashMap();
		Gson gson = new Gson();
		long share_amounts = 0;
		JSONObject json = null;
		UserModel user = null;
		PermissionsModel newUserPermissions = new PermissionsModel();
		try {
			json = (JSONObject) parser.parse(data);

			share_amounts = json.get("share_amounts") == null?0:json.get("share_amounts").equals("")?0:(long) json.get("share_amounts");

			newUserPermissions = gson.fromJson((String) json.get("permissions").toString(), PermissionsModel.class);
			if(method.equals("update")) {
				user = new UserModel(Long.parseLong((String) json.get("editUserId")),
						(String)json.get("username"),(String)json.get("dob"),(String)json.get("address"),((Long)json.get("gender")).intValue(),((String)json.get("mobile")),(String)json.get("email"),
						Boolean.parseBoolean((String) json.get("marriedStatus")),
						((Long)json.get("country")).intValue(),
						((Long)json.get("state")).intValue(),
						((String)json.get("postalcode")),
					((Long)json.get("profession")).intValue(),((Long)json.get("category")).intValue(),(String)json.get("permissions").toString());
			}
				
			else {

				DataValidation.passwordRePassword((String)json.get("password"),(String)json.get("confirmPassword"));
				user = new UserModel((String)json.get("username"),
						(String)json.get("dob"),
						(String)json.get("address"),
						((Long)json.get("gender")).intValue(),
						((String)json.get("mobile")),
						(String)json.get("email"),
						Boolean.parseBoolean((String) json.get("marriedStatus")),
					((Long)json.get("country")).intValue(),((Long)json.get("state")).intValue(),((String)json.get("postalcode")),
					((Long)json.get("profession")).intValue(),((Long)json.get("category")).intValue(),(String)json.get("permissions").toString(),(String)json.get("password"),(String)json.get("confirmPassword"));
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
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		boolean isUserBank = DataVerification.isUserBank(userId,bankId);
		if(isUserBank && userId !=0 && user.getUserId() != 0) isUserBank = DataVerification.isUserBank(user.getUserId(),bankId);
		PermissionsModel permissions = GetData.getPermissions(userId);
		map = DataValidation.validate(user,method);//data validation
		if((boolean)map.get("isValid")) {
			if((isUserBank && permissions.isUsers_create()) || (isUserBank && permissions.isNewgroup_view())) {
				map.clear();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				map = GetData.updatedatAndDurationat(userId,bankId);
				map = DataVerification.getNextStoreDuration(((Date)map.get("updated_at")),((int)map.get("durations")));
				map.put("result","wait for your bank's durations  : "+dateFormat.format(map.get("durationAt"))+"...!");
				if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) == 0) {
					if(method.equals("create")) {
						map.clear();
						map = SaveData.save(user,bankId,newUserPermissions.isNewgroup_view(),share_amounts);
						if((boolean)map.get("isResult")) {
							map.put("isResult", true);
							map.put("result", "User is success fully created by another user...!");
						}
						else {//data doesn't store with the exceptions
							map.put("isResult", false);
							map.put("result", "user doesn't created...!");
						}
					}
					else if((method.equals("update") && permissions.isUsers_update()) || (method.equals("update") && permissions.isNewgroup_view())) {
						map = SaveData.update(user,newUserPermissions);
					}
				}
				else if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) > 0) {
					map.put("result", "your not Eligiple this group...!");
				
				}
				else {
					map.put("result", "only updated at duration time...!");
				}
				
			}
			
			else if(newUserPermissions.isNewgroup_view() && userId ==0 && bankId == 0 && method.equals("")) {
				map.clear();
				map = SaveData.save(user,bankId,newUserPermissions.isNewgroup_view(),share_amounts);
				if((boolean)map.get("isResult")) {
					SessionManagement session = new SessionManagement(request.getSession(true));
					session.setAttributes("redirect","new-group/"+map.get("adminId"));
					map.put("redirect", "new-group");
					map.put("adminId", map.get("adminId"));
					map.put("isResult", true);
				}
				else {
					map.put("isResult", false);
					map.put("result", "user doesn't created...!");
				}
			}
			else {
				map.put("isResult", false);
				map.put("result", "permissions denied...!");
			}
		}
		
		out.print(new Gson().toJson(map));
	}
}