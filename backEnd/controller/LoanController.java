package controller;

import jakarta.servlet.http.HttpServlet;
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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BarrowModel;
import model.PermissionsModel;

@WebServlet("/BarrowController")
public class LoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		long userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		String method = (request.getParameter("meth") == null) ? " ":request.getParameter("meth");
		HashMap map = new HashMap();
		
		if(DataVerification.isUserBank(userId,bankId)) {
			
			PermissionsModel permissions = GetData.getPermissions(userId);
			
			if((method.equals("history") && permissions.isLoan_history_view()) || (method.equals("history") && permissions.isNewgroup_view())) {
				map.put("datas",GetData.getLoanHistory(bankId));
				map.put("isDatas",true);
				map.put("result","data loaded successfully...!");
			}
			else if((method.equals("list") && permissions.isLoan_list_view()) || (method.equals("list") && permissions.isNewgroup_view())){
				map.put("datas",GetData.getLoanList(bankId));
				
				map.put("isDatas",true);
				map.put("result","data loaded successfully...!");
			}
			else if((method.equals("load") && permissions.isLoan_view() && permissions.isLoan_list_view()) || (method.equals("load") && permissions.isNewgroup_view())) {
				map.put("barrow_types",GetData.getBarrow_type());
				
				map.put("result","data loaded successfully...!");
				map.put("isDatas",true);
			}
			else {
				map.put("result","permission denied...!");
				map.put("isDatas",false);
				
			}
		}
		else {
			map.put("result","Illegel user...!");
			map.put("isDatas",false);
		}
		
		out.print(new Gson().toJson(map));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		long userId = (request.getParameter("uId") == null) ? 0:Long.parseLong(request.getParameter("uId"));
		long bankId = (request.getParameter("bId") == null) ? 0:Long.parseLong(request.getParameter("bId"));
		String data = request.getReader().lines().collect(Collectors.joining());
		Gson gson = new Gson();
		HashMap map = new HashMap();
		boolean isUserBank = DataVerification.isUserBank(userId,bankId);
		PermissionsModel permissions = GetData.getPermissions(userId);
		
		if((isUserBank && permissions.isLoan_update()) || (isUserBank && permissions.isNewgroup_view())) {
			BarrowModel barrow = gson.fromJson(data, BarrowModel.class);
			map = DataValidation.validate(barrow);//data validation
			if((boolean)map.get("isValid")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				map = GetData.updatedatAndDurationat(userId,bankId);
				map = DataVerification.getNextStoreDuration(((Date)map.get("updated_at")),((int)map.get("durations")));
				map.put("result","internal error...!");
				if((boolean)map.get("flag")) {
					if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) < 0) {
						map.put("result","wait for your bank's durations  : "+dateFormat.format(map.get("durationAt"))+"...!");
						
					}
					else if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) > 0) {
						map.put("result", "your not Eligiple this group...!");
						
					}
					else if(((Date)map.get("todayDate")).compareTo((Date)map.get("durationAt")) == 0) {
						map = SaveData.save(barrow,bankId);
					}
				}
			}
			else {
				map.put("result","kindly check your datas...!");
			}
		}
		else {
			map.put("result","permissions denied...!");
		}
		
		out.print(new Gson().toJson(map));
	}
}
