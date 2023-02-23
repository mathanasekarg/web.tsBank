package auth;

import java.util.HashMap;
import java.util.regex.Pattern;

import database.GetData;
import model.BankModel;
import model.BarrowModel;
import model.SavingsModel;
import model.UserModel;

public class DataValidation {
	@SuppressWarnings("rawtypes")
	private static HashMap map = new HashMap();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap validate(UserModel user, String method) {//user validation
		if(user.getUsername().length() < 4) {
			map.put("result","valid username length required ...!");
			map.put("isValid",false);
			return map;
		}
		else {
			if(method.equals("update") && !DataVerification.isUsernameUpdate(user.getUsername(),user.getUserId())) {
				map.put("result","username already exist in update ...!");
				map.put("isValid",false);
				return map;
			}
			else if(!method.equals("update") && DataVerification.isUsernameExist(user.getUsername()) == 1) {
				map.put("result","username already exist ...!");
				map.put("isValid",false);
				return map;
			}
		}
		if(!Pattern.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",user.getEmail())) {
			map.put("result","valid email length required ...!");
			map.put("isValid",false);
			return map;
		}
		if(!Pattern.matches("^\\d{10}$",user.getMobile())) {
			map.put("result","phone number is length required ...!");
			map.put("isValid",false);
			return map;
		}
		if(!Pattern.matches("^\\d{6}$",user.getPostalcode())) {
			map.put("result","phone zip code is length required ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isGender(user.getGender()) == 0) {
			map.put("result","please check your gender ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isCountry(user.getCountry()) == 0) {
			map.put("result","please check your country ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isState(user.getState()) == 0) {
			map.put("result","please check your state ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isProfession(user.getProfession()) == 0) {
			map.put("result","please check your profession ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isCategory(user.getCategory()) == 0) {
			map.put("result","please check your category ...!");
			map.put("isValid",false);
			return map;
		}
//		if(!user.getPassword().equals(user.getRePassword())) {
//			System.out.println("password : "+user.getPassword());
//			System.out.println("re-password : "+user.getRePassword());
//			map.put("result","password and rePssword doesn't same ...!");
//			map.put("isValid",false);
//			return map;
//		}
//		if(!passwordRePassword(user.getPassword(),user.getRePassword())) {
//			map.put("result","try to strong password ...!");
//			map.put("isValid",false);
//			return map;
//		}
		map.put("isValid",true);
		return map;
	}

	public static boolean passwordRePassword(String password,String rePassword) {
		return (password.equals(rePassword));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap validate(BankModel bank,String method) {		//validate bank model
		map.put("result", "kindly check your datas...!");
		if(bank.getName().length() < 4) {
			map.put("result","valid name length required ...!");
			map.put("isValid",false);
			return map;
		}
		else {
			if(!method.equals("update") && GetData.isNameExist(bank.getName())) {
				map.put("result","Name already exist ...!");
				map.put("isValid",false);
				return map;
			}
			else if(method.equals("update") && !DataVerification.isBankNameUpdate(bank.getName(),bank.getId())) {
				map.put("result","username already exist in update ...!");
				map.put("isValid",false);
				return map;
			}
		}
		if(DataVerification.isDurations(bank.getDurations()) == 0) {
			map.put("result","valid durations required ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isInterest_collection_type(bank.getInterest_collection_type()) == 0) {
			map.put("result","valid interest collection type is required ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isSavings_collection_type(bank.getSavings_collection_type()) == 0) {
			map.put("result","valid saivings collection typr is required ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isExtras_collection_type(bank.getExtras_collection_type()) == 0) {
			map.put("result","valid extras collections type is required ...!");
			map.put("isValidate",false);
			return map;
		}
		if(DataVerification.isSavings_extras_fine_type(bank.getSavings_extras_fine_type()) == 0) {
			map.put("result","valid savings extra fine type is required ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isDue_interest_fine_type(bank.getDue_interest_fine_type()) == 0) {
			map.put("result","valid due interest fine collections type is required ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isLimited_type(bank.getLimited_type()) == 0) {
			map.put("result","valid group limited type is required ...!");
			map.put("isValid",false);
			return map;
		}
		if(DataVerification.isGroup_type(bank.getGroup_type()) == 0) {
			map.put("result","valid group type is required ...!");
			map.put("isValid",false);
			return map;
		}
		map.put("isValid",true);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap validate(SavingsModel savings) {
//		if(DataVerification.IsUser(savings.getUserId()) == 0) {
//			map.put("scan_result","user does not exists ...!");
//			map.put("isValidate",false);
//			return map;
//		}
		map.put("isValidate",true);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap validate(BarrowModel barrow) {
		if(DataVerification.isBrarrow_type(barrow.getBarrow_type()) == 0) {
			map.put("result","barrow type does not exists ...!");
			map.put("isValid",false);
			return map;
		}
		map.put("isValid",true);
		return map;
	}
}
