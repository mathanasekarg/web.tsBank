package controller;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import model.FineModel;
import model.PermissionsModel;
import model.SavingsModel;
import model.UserModel;

@SuppressWarnings("serial")
@WebServlet("/SavingsController")
public class SavingsController extends HttpServlet {

    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		long userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
		long checkId = (request.getParameter("chkId") == null) ? 0:Long.parseLong(request.getParameter("chkId"));
		String method = (request.getParameter("meth") == null) ? "":request.getParameter("meth");
		Gson gson = new Gson();
		HashMap map = new HashMap();
		boolean isUserBank = DataVerification.isUserBank(userId,bankId);
		PermissionsModel permissions = GetData.getPermissions(userId);
		
		if(isUserBank) {
			if(method.equals("check") && checkId !=0 && permissions.isSavings_check() && permissions.isSavings_view()) {
				map.clear();
				map.put("datas",GetData.checkSavings(bankId,checkId));
				map.put("isDatas",true);
				map.put("result","data loaded successfully...!");
			}
			else if(method.equals("history") && permissions.isSavings_history_view()) {
				map.put("isDatas",true);
				map.put("datas",GetData.getSavingsHistory(bankId));
				map.put("result","data loaded successfully...!");
			}
			else if(method.equals("list") && permissions.isSavings_list_view()) {
				map.put("datas",GetData.getUserSavings(bankId));
			
				map.put("result","data loaded successfully...!");
				map.put("isDatas",true);
			}
			else if((!method.equals("check") && !method.equals("history") && !method.equals("list") && !method.equals("list") && permissions.isSavings_view()) || (!method.equals("check") && !method.equals("history") && !method.equals("list") && !method.equals("list") && permissions.isNewgroup_view())) {
				map.put("datas",GetData.getUserSavingsList(bankId));
				map.put("isDatas",true);
				map.put("result","data loaded successfully...!");
			}
			else {
				map.put("result","permission denied...!");
				map.put("isDatas",false);
			}
		}
		else {
			map.put("result","illegel user...!");
			map.put("isDatas",false);
			
		}
		
		out.print(new Gson().toJson(map));
	}

    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	long userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		HashMap map = new HashMap();
		UserModel user = new UserModel();
		Gson gson = new Gson();
		
		boolean isUserBank = DataVerification.isUserBank(userId,bankId);
		PermissionsModel permissions = GetData.getPermissions(userId);
		map = GetData.updatedatAndDurationat(userId,bankId);
		if((isUserBank && permissions.isSavings_update()) || (isUserBank && permissions.isNewgroup_view())) {
			String data = request.getReader().lines().collect(Collectors.joining());
			JSONParser parser = new JSONParser();
			JSONObject json = null;
			SavingsModel savings = null;
			
			try {
//				FineModel fine = gson.fromJson((String) json.get("fine"), FineModel.class);
//				boolean fine[] = {false,false,false,false};
				json = (JSONObject) parser.parse(data);
//				System.out.println(json);
//				JSONArray jsonArray = (JSONArray) json.get("fine");
//		        for (int i = 0; i < jsonArray.size(); i++) {
//		        	fine[i] = (boolean) jsonArray.get(i);
//		        }
				FineModel fine = new FineModel((boolean) json.get("isSavings_fine"),(boolean) json.get("isExtra_fine"),(boolean) json.get("isInterest_fine"),(boolean) json.get("isDue_fine"));
				savings = new SavingsModel(((long)json.get("userId")),((boolean)json.get("savings")),((boolean)json.get("extra")),
					(fine),
					((boolean)json.get("interest")),
					(((json.get("due"))==null?0:(long)json.get("due"))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			map = DataValidation.validate(savings);//data validation
			if((boolean)map.get("isValidate")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				map = GetData.updatedatAndDurationat(savings.getUserId(),bankId);
				map = DataVerification.getNextStoreDuration(((Date)map.get("updated_at")),((int)map.get("durations")));
				map.put("result","internal error...!");
				if((boolean)map.get("flag")) {
					map.put("result","wait for your bank's durations  : "+dateFormat.format(map.get("durationAt"))+"...!");
					if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) == 0) {
						map = SaveData.save(savings,userId,bankId,permissions.isNewgroup_view());
					}
					else if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) > 0) {
						map.put("result", "your not Eligiple this group...!");
					
					}
				}
			}
			else map.put("result", "kindly check your datas...!");
		}
		else {
			map.put("result","permission denied...!");
		}
		out.print(new Gson().toJson(map));
	}
}