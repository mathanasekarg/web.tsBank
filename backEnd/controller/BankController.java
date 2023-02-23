package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BankModel;
import model.PermissionsModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import auth.DataValidation;
import auth.DataVerification;
import database.GetData;
import database.SaveData;

@jakarta.servlet.annotation.WebServlet("/BankController")

public class BankController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static PrintWriter out = null;
	private static Gson gson = new Gson();

	private static PermissionsModel permissions = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();
		HashMap map = new HashMap();
		long adminId = (request.getParameter("aId") == null) ? 0:Long.parseLong(request.getParameter("aId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		String method = (request.getParameter("meth") == null) ? "":request.getParameter("meth");

		map.put("result","permissions denied (not admin)...!");
		map.put("isDatas", false);
		permissions = GetData.getPermissions(adminId);
		if(permissions.isNewgroup_view()) {
			if(method.equals("load")) {
				map.put("durations",GetData.getDurations());
				map.put("interest_collection_types",GetData.getInterest_collection_type());
				map.put("savings_collection_types",GetData.getSavings_collection_type());
				map.put("extras_collection_types",GetData.getExtras_collection_type());
				map.put("savings_extras_fine_types",GetData.getSavings_extras_fine_type());
				map.put("due_interest_fine_types",GetData.getDue_interest_fine_type());
				map.put("limited_types",GetData.getLimited_type());
				map.put("group_types",GetData.getGroup_type());
				map.put("isDatas", true);
				map.put("result","data loaded successfully...!");
			}
			else if(method.equals("details") && bankId != 0) {
				map.put("datas", GetData.getUniqueBank(bankId));
				map.put("durations",GetData.getDurations());
				map.put("interest_collection_types",GetData.getInterest_collection_type());
				map.put("savings_collection_types",GetData.getSavings_collection_type());
				map.put("extras_collection_types",GetData.getExtras_collection_type());
				map.put("savings_extras_fine_types",GetData.getSavings_extras_fine_type());
				map.put("due_interest_fine_types",GetData.getDue_interest_fine_type());
				map.put("limited_types",GetData.getLimited_type());
				map.put("group_types",GetData.getGroup_type());
				map.put("isDatas", true);
				map.put("result","data loaded successfully...!");
			}
			else if(method.equals("update") && bankId != 0 && adminId != 0) {
				map = GetData.updatedatAndDurationat(adminId,bankId);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				map = DataVerification.getNextStoreDuration(((Date)map.get("updated_at")),((int)map.get("durations")));
				map.put("result","internal error...!");
				if((boolean)map.get("flag")) {
					map.put("result","wait for your bank's durations  : "+dateFormat.format(map.get("durationAt"))+"...!");
					if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) == 0) {
						map = SaveData.updateBank(bankId);
					}
					else if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) > 0) {
						map.put("result", "bank is not Eligiple ...!");
					
					}
				}
			}
			else {
//				map.put("isDatas", false);
//				map.put("result","loading failed (internal error)...!");
				map.put("datas",GetData.getAdminBanks(adminId));
				map.put("isDatas", true);
				map.put("result","data loaded successfully...!");
			}
		}
//		else if(method.equals("load")) {
//			map.put("durations",GetData.getDurations());
//			map.put("interest_collection_types",GetData.getInterest_collection_type());
//			map.put("savings_collection_types",GetData.getSavings_collection_type());
//			map.put("extras_collection_types",GetData.getExtras_collection_type());
//			map.put("savings_extras_fine_types",GetData.getSavings_extras_fine_type());
//			map.put("due_interest_fine_types",GetData.getDue_interest_fine_type());
//			map.put("limited_types",GetData.getLimited_type());
//			map.put("group_types",GetData.getGroup_type());
//			map.put("isDatas", true);
//			map.put("result","data loaded successfully...!");
//		}
		out.print(gson.toJson(map));
		out.close();
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long adminId = (request.getParameter("aId") == null) ? 0:Long.parseLong(request.getParameter("aId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		String method = (request.getParameter("meth") == null) ? "":request.getParameter("meth");
		out = response.getWriter();
		@SuppressWarnings("rawtypes")
		HashMap map = new HashMap();
		String data = request.getReader().lines().collect(Collectors.joining());

		BankModel bank = gson.fromJson(data, BankModel.class);
		permissions = GetData.getPermissions(bank.getAdminId());
		

		System.out.println("method : "+method+" , adminId : "+adminId);
		System.out.println("url : "+request.getRequestURI());
		bank.setId(bankId);
		map = DataValidation.validate(bank,method);		// bank data validation
		if((boolean)map.get("isValid")) {
			map.put("result", "should can update admin only...!");
			if(permissions.isNewgroup_view() && method.equals("update")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				map = GetData.updatedatAndDurationat(adminId,bankId);
				map = DataVerification.getNextStoreDuration(((Date)map.get("updated_at")),((int)map.get("durations")));
				map.put("result","wait for your bank's durations  : "+dateFormat.format(map.get("durationAt"))+"...!");
				if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) == 0) {
					map = SaveData.update(bank);				// save bank model
				}
				else if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) > 0) {
					map.put("result", "your not Eligiple this group...!");
				
				}
				else {
					map.put("result", "only updated at duration time...!");
				}
			}
			else if(permissions.isNewgroup_view() && !method.equals("update")) {
				map.clear();
				map = SaveData.save(bank);				// save bank model
				if((boolean)map.get("isStored")) {
//					map.put("redirect", "dashboard/"+map.get("adminId")+"/"+map.get("bankId"));
				}
			}
		}
		
		out.print(gson.toJson(map));
		out.close();
	}

}
