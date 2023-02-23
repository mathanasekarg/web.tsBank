package database;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import model.BankModel;
import model.BarrowModel;
import model.FineModel;
import model.PermissionsModel;
import model.SavingsModel;
import model.UserModel;

public class SaveData {
	@SuppressWarnings("rawtypes")
	private static HashMap map = new HashMap();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap save(UserModel user,long bankId,boolean isAdmin,long share_amounts) {
		HashMap map = new HashMap();
		String query = "";
		
		if(isAdmin) {//when new admin
			query = "insert into users values(null,'"+user.getUsername()+"','"+user.getDob()+"','"+user.getAddress()+"',"+user.getGender()+","
				+ ""+user.getMobile()+",'"+user.getEmail()+"',"+user.getMarriedStatus()+","+user.getCountry()+","+user.getState()+","
				+ ""+user.getPostalcode()+","+user.getProfession()+","+user.getCategory()+",'"+user.getPassword()+"','"+user.getPermissions()+"',"
				+ "now(),now())";
			SaveData.save(query);
			query = "select id from users where username = '"+user.getUsername()+"'";
			long userId = GetData.getId(query);
		
			map.put("adminId",userId);
			map.put("isResult", true);
			System.out.println("admin ...!");
			return map;
		}
		else {// if(permissions.isNewgroup_view() && ((adminId != 0) && (bankId != 0))) {//when new user

			
			long dbBankId = GetData.getId("select id from banks where id="+bankId);
			
			if((dbBankId != 0) && (bankId == dbBankId)) {//Admin is should put a valid bank's id or not
				query = "insert into users values(null,'"+user.getUsername()+"','"+user.getDob()+"','"+user.getAddress()+"',"+user.getGender()+","
					+ ""+user.getMobile()+",'"+user.getEmail()+"',"+user.getMarriedStatus()+","+user.getCountry()+","+user.getState()+","
					+ ""+user.getPostalcode()+","+user.getProfession()+","+user.getCategory()+",'"+user.getPassword()+"','"+user.getPermissions()+"',"
					+ "now(),now())";
				SaveData.save(query);
				query = "select id from users where username = '"+user.getUsername()+"'";
				long userId = GetData.getId(query);
				query = "insert into savings values("+userId+","+bankId+",0,0,0,0,0,null);"
						+"insert into barrow values("+userId+","+bankId+",0,1,now());"
						+"insert into fine values("+userId+","+bankId+",1,1,1,1,1,1,1,1,now());"
						+"insert into savings_history values("+userId+","+bankId+",0,0,0,0,0,now());"
						+"insert into barrow_history values("+userId+","+bankId+",0,1,now());"
						+"update banks set members_count="+(GetData.getMembers_count(dbBankId)+1)+",bank_amounts="+(GetData.getBank_amounts(dbBankId)+share_amounts)+" where id="+bankId+"";
				SaveData.save(query);
				map.put("userId",userId);
				map.put("isResult", true);
			}
			else {
				map.put("scan_result", "Admin give the wrong bank id...");
				map.put("isResult", false);
			}
			System.out.println("not a admin ...!");
		}
//		else {
//			map.put("scan_result", "Illegel User...");
//			map.put("isResult", false);
//		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap update(UserModel user,PermissionsModel permissions) {
		HashMap map = new HashMap();
		System.out.println("married status : "+user.getMarriedStatus());
		String query = "update users set userName='"+user.getUsername()+"',DOB='"+user.getDob()+"',address='"+user.getAddress()+"',gender="+user.getGender()+","
				+ "mobile="+user.getMobile()+",email='"+user.getEmail()+"',marriedStatus="+user.getMarriedStatus()+",country="+user.getCountry()+",state="+user.getState()+","
				+ "postalcode="+user.getPostalcode()+",profession="+user.getProfession()+",category="+user.getCategory()+",permissions='"+user.getPermissions()+"',"
				+ "updated_at=now() where id="+user.getUserId()+"";
		
		map.put("result", "updated failed (internal error)...");
		map.put("isResult", false);
		SaveData.save(query);
		map.put("result", "successfully updated...");
		map.put("isResult", true);
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap updateBank(long bankId) {
		map.put("result","failed to update (internal error...!)");
		SaveData.save("update banks set updated_at=now() where id="+bankId);
		map.put("result","success fully updated ...!");
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap update(BankModel bank) {
		String query = "update banks set name='"+bank.getName()+"',descryptions='"+bank.getDescryptions()+"',durations="+bank.getDurations()+","
			+"interest_collection_type="+bank.getInterest_collection_type()+",savings_collection_type="+bank.getSavings_collection_type()+",extras_collection_type="+bank.getExtras_collection_type()+",savings_extras_fine_type="+bank.getSavings_extras_fine_type()+","
			+"due_interest_fine_type="+bank.getDue_interest_fine_type()+",limited_type="+bank.getLimited_type()+",group_type="+bank.getGroup_type()+" where id="+bank.getId();
		
		map.put("result", "updated failed (internal error)...");
		map.put("isResult", false);
		SaveData.save(query);
		map.put("result", "successfully updated...");
		map.put("isResult", true);
		return map;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap save(BankModel bank) {//bankController 1
		String query = "insert into banks values(null,"+bank.getAdminId()+",'"+bank.getName()+"',1,0,0,0,0,0,"+bank.getBank_amounts()+",'"+bank.getDescryptions()+"',"+bank.getDurations()+","
			+""+bank.getInterest_collection_type()+","+bank.getSavings_collection_type()+","+bank.getExtras_collection_type()+","+bank.getSavings_extras_fine_type()+","
			+""+bank.getDue_interest_fine_type()+","+bank.getLimited_type()+","+bank.getGroup_type()+",0,now(),null)";
		SaveData.save(query);
		query = "select id from banks where adminId = "+bank.getAdminId()+" && name='"+bank.getName()+"'";
		long bankId = GetData.getId(query);

		map.put("adminId",bank.getAdminId());
		query = "insert into savings values("+bank.getAdminId()+","+bankId+",0,0,0,0,0,null);"
				+"insert into barrow values("+bank.getAdminId()+","+bankId+",0,1,now());"
				+"insert into fine values("+bank.getAdminId()+","+bankId+",1,1,1,1,1,1,1,1,now());"
				+"insert into savings_history values("+bank.getAdminId()+","+bankId+",0,0,0,0,0,now());"
				+"insert into barrow_history values("+bank.getAdminId()+","+bankId+",0,1,now());";

		map.put("result", "bank doesn't created(internal error)...!");
		map.put("isStored", false);
		SaveData.save(query);
		map.put("isStored", true);
		map.put("bankId", bankId);
		map.put("adminId", bank.getAdminId());
		map.put("result", "bank is success fully created...!");
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap save(SavingsModel savings, long userId, long bankId, boolean isAdmin) {
		HashMap map = new HashMap();
		String result = "Something went wrong ...!",query = "";
		FineModel fine = new FineModel();
		BankModel bank = new BankModel(bankId);
		SavingsModel dbSavings = new SavingsModel();
		BarrowModel barrow = new BarrowModel();
		
		dbSavings = GetData.getSavings(savings.getUserId(),bankId);
		
		fine = GetData.getFine(savings.getUserId(),bankId);
		
		barrow = GetData.getBarrow(savings.getUserId(),bankId);
		
		bank = GetData.getBank(bankId);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date durationAt = null,todayDate = null;
		
		try {

			if(dbSavings.getUpdated_at() == null) durationAt = dateFormat.parse(dateFormat.format(new Date()));
			else {
				calendar.setTime(dbSavings.getUpdated_at());
				calendar.add(Calendar.DATE, bank.getDurations_value());
				durationAt = dateFormat.parse(dateFormat.format(calendar.getTime()));
			}
			
			todayDate = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(todayDate.compareTo(durationAt) < 0) {
			map.put("result", "wait for the next duration...!("+durationAt+")");
			return map;
		
		}
		else if(todayDate.compareTo(durationAt) > 0) {
			map.put("result", "your not Eligiple this group...!");
			return map;
		
		}
		
		long totalFine=0,savingsFine=0,extraFine=0,interestFine=0,dueFine=0,bank_amounts=0;
		if(savings.isSavings() && savings.isExtra()) {//start enable savings and extra
			if(savings.isInterest() && savings.getDue() > 0) {//start enable interest and due
				if(dbSavings.getDue() > 0) {//start have done the due
					if(fine.getSavings_fine_count() > 1) {//start have done the all fines because savings is fines
						if(savings.getFine().isSavings_fine() && savings.getFine().isExtra_fine() && savings.getFine().isInterest_fine() && savings.getFine().isDue_fine()) {//start enable all fines
							
							savingsFine = ((long)Math.ceil(bank.getSavings_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getSavings_fine_count()-1);
							extraFine = ((long)Math.ceil(bank.getExtras_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getExtra_fine_count()-1);
							interestFine = (long)((long)Math.ceil((dbSavings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
							interestFine = interestFine <= 1?fine.getInterest_fine_count()-1:(fine.getInterest_fine_count()-1)*interestFine;
							dueFine = (long)Math.ceil(((dbSavings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
							dueFine = dueFine <= 1?fine.getDue_fine_count()-1:(fine.getDue_fine_count()-1)*dueFine;
							totalFine = savingsFine+extraFine+interestFine+dueFine;
							
							bank_amounts = (bank.getBank_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count())+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+(long)((Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+totalFine));
							if(dbSavings.getDue() == savings.getDue()) {
								query = "update savings set savings="+(dbSavings.getSavings()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra="+(dbSavings.getExtra()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",fine="+(dbSavings.getFineAmount()+totalFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",due="+(dbSavings.getDue()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+totalFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra_amounts="+(bank.getExtra_amounts()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+(bank_amounts)+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update barrow set barrowed_amount=0,barrow_type=1,updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update fine set savings_fine_count=1,extra_fine_count=1,interest_fine_count=1,due_fine_count=1,savings_fine="+(fine.getSavings_fine()+savingsFine)+",extra_fine="+(fine.getExtra_fine()+extraFine)+",interest_fine="+(fine.getInterest_fine()+interestFine)+",due_fine="+(fine.getDue_fine()+dueFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+((bank.getSavings_collection_type()*fine.getSavings_fine_count()))+","+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+","+(totalFine)+","+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+","+(savings.getDue())+",now());";
								result = "successfully stored ...!";
							}
							else if(dbSavings.getDue() > savings.getDue()) {

								query = "update savings set savings="+(dbSavings.getSavings()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra="+(dbSavings.getExtra()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",fine="+(dbSavings.getFineAmount()+totalFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",due="+(dbSavings.getDue()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+totalFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra_amounts="+(bank.getExtra_amounts()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+(bank_amounts)+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update barrow set barrowed_amount="+(barrow.getBarrowed_amount()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update fine set savings_fine_count=1,extra_fine_count=1,interest_fine_count=1,due_fine_count=1,savings_fine="+(fine.getSavings_fine()+savingsFine)+",extra_fine="+(fine.getExtra_fine()+extraFine)+",interest_fine="+(fine.getInterest_fine()+interestFine)+",due_fine="+(fine.getDue_fine()+dueFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+((bank.getSavings_collection_type()*fine.getSavings_fine_count()))+","+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+","+(totalFine)+","+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+","+(savings.getDue())+",now());";
								result = "successfully stored ...!";
							}
							else {
								result = "check your due ...!";
							}
							
						}//end enable all fines
						else {
							result = "please check your fines...!";
							
						}
					}//end have done the savings fine
					else if(fine.getInterest_fine_count() > 1) {//start have done the interest and due fines
						if((savings.getFine().isInterest_fine() && savings.getFine().isDue_fine()) && !(savings.getFine().isSavings_fine() || savings.getFine().isExtra_fine())) {//start enable interest fine and due fine
							interestFine = (long)((long)Math.ceil((dbSavings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
							interestFine = interestFine <= 1?fine.getInterest_fine_count()-1:(fine.getInterest_fine_count()-1)*interestFine;
							dueFine = (long)Math.ceil(((dbSavings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
							dueFine = dueFine <= 1?fine.getDue_fine_count()-1:(fine.getDue_fine_count()-1)*dueFine;
							totalFine = interestFine+dueFine;
							
							bank_amounts = (bank.getBank_amounts()+bank.getSavings_collection_type()+bank.getExtras_collection_type()+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+totalFine));
							System.out.println("interest : "+interestFine+" dueFine : "+dueFine);
							if(dbSavings.getDue() == savings.getDue()) {
								query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",fine="+(dbSavings.getFineAmount()+totalFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",due="+(dbSavings.getDue()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+totalFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update barrow set barrowed_amount=0,barrow_type=1,updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update fine set interest_fine_count=1,due_fine_count=1,interest_fine="+(fine.getInterest_fine()+interestFine)+",due_fine="+(fine.getDue_fine()+dueFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+","+(totalFine)+","+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+","+(savings.getDue())+",now());";
								result = "successfully stored...!";
							}
							else if(dbSavings.getDue() > savings.getDue()) {
								query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",fine="+(dbSavings.getFineAmount()+totalFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",due="+(dbSavings.getDue()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+totalFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update barrow set barrowed_amount="+(barrow.getBarrowed_amount()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update fine set interest_fine_count=1,due_fine_count=1,interest_fine="+(fine.getInterest_fine()+interestFine)+",due_fine="+(fine.getDue_fine()+dueFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+","+(totalFine)+","+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+","+(savings.getDue())+",now());";
								result = "successfully stored...!";
							}
							else {
								result = "check your due...!";
							}
														
							
						}//end enable interest fine and due fine
						else {
							result = "please check your fines...! (interest fine and due fine only)";
							
						}
					}//end have done the interest and due fines
					else if(fine.getDue_fine_count() > 1) {//start have done the due fine only 
						if(savings.getFine().isDue_fine() && !(savings.getFine().isSavings_fine() || savings.getFine().isExtra_fine() || savings.getFine().isInterest_fine())){
							
							dueFine = (long)Math.ceil(((dbSavings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
							dueFine = dueFine <= 1?fine.getDue_fine_count()-1:(fine.getDue_fine_count()-1)*dueFine;
							bank_amounts = (bank.getBank_amounts()+bank.getSavings_collection_type()+bank.getExtras_collection_type()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100))+dueFine);
							if(dbSavings.getDue() == savings.getDue()) {
								query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",fine="+(dbSavings.getFineAmount()+dueFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)))+",due=0,updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+dueFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update barrow set barrowed_amount=0,barrow_type=1,updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update fine set due_fine_count=1,due_fine="+(fine.getDue_fine()+dueFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+","+(dueFine)+","+((long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+","+(savings.getDue())+",now());";
								result = "successfully stored...!";
							}
							else if(dbSavings.getDue() > savings.getDue()) {
								query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",fine="+(dbSavings.getFineAmount()+dueFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)))+",due="+(dbSavings.getDue()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+dueFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update barrow set barrowed_amount="+(barrow.getBarrowed_amount()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update fine set due_fine_count=1,due_fine="+(fine.getDue_fine()+dueFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+","+(dueFine)+","+((long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+","+(savings.getDue())+",now());";
								result = "successfully stored...!";
							}
							else {
								result = "check your due...!";
							}							
							
						}
						else {
							result = "please check your fines...!(due fine only)";
							
						}
					}//end have done the due fine only
					else if(savings.getFine().isSavings_fine() || savings.getFine().isInterest_fine() || savings.getFine().isDue_fine() || savings.getFine().isExtra_fine()) {//start no fines but fine is enabled
						result = "please check your fines...!";
						
					}//end no fines but fine is enabled
					else {// start data stored succesfully
						bank_amounts = (bank.getBank_amounts()+bank.getSavings_collection_type()+bank.getExtras_collection_type()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()));
						if(dbSavings.getDue() == savings.getDue()) {
							query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",due=0,updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"update banks set interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
									+"update barrow set barrowed_amount=0,barrow_type=1,updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+",0,"+((long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+","+(savings.getDue())+",now());";
							result = "successfully stored...!";
						}
						else if(dbSavings.getDue() > savings.getDue()) {
							query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",due="+(dbSavings.getDue()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"update banks set interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",barrowed_amounts="+(bank.getBarrowed_amounts()-savings.getDue())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
									+"update barrow set barrowed_amount="+(barrow.getBarrowed_amount()-savings.getDue())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+",0,"+((long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+","+(savings.getDue())+",now());";
							result = "successfully stored...!";
						}
						else {
							result = "check your due...!";
						}
						
						result = "successfully stored";
					}//end data stored succesfully
				}//end have done the due
				else {//start if haven't the due
					result = "please check your interest and due becaues you havn't a due...!";
					
				}//end if haven't the due
			}//end enable interest and due
			else {//start not enable interest or due 
				if(dbSavings.getDue() > 0) {//start have done the due
					if(savings.isInterest()) {//start you enabled interest only
						if(fine.getSavings_fine_count() > 1 && fine.getInterest_fine_count() > 1) {//start you have done the all fines
							if(savings.getFine().isSavings_fine() && savings.getFine().isExtra_fine() && savings.getFine().isInterest_fine() && !(savings.getFine().isDue_fine())) {//start savings,extra and interest respectively enabled(but not due and fine) fine and amount
								
								savingsFine = ((long)Math.ceil(bank.getSavings_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getSavings_fine_count()-1);
								extraFine = ((long)Math.ceil(bank.getExtras_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getExtra_fine_count()-1);
								interestFine = (long)((long)Math.ceil((dbSavings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
								interestFine = interestFine <= 1?fine.getInterest_fine_count()-1:(fine.getInterest_fine_count()-1)*interestFine;
								
								totalFine = savingsFine+extraFine+interestFine;
								bank_amounts = (bank.getBank_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count())+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+totalFine);
								query = "update savings set savings="+(dbSavings.getSavings()+(bank.getSavings_collection_type()*fine.getInterest_fine_count()))+",extra="+(dbSavings.getExtra()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",fine="+(dbSavings.getFineAmount()+totalFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+totalFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra_amounts="+(bank.getExtra_amounts()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update fine set savings_fine_count=1,extra_fine_count=1,interest_fine_count=1,due_fine_count="+(fine.getDue_fine_count()+1)+",savings_fine="+(fine.getSavings_fine()+savingsFine)+",extra_fine="+(fine.getExtra_fine()+extraFine)+",interest_fine="+(fine.getInterest_fine()+interestFine)+",updated_at=now(); where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type()*fine.getSavings_fine_count())+","+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+","+(totalFine)+","+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+",0,now());";
								
								result = "successfully stored...!";
								
							}//start savings,extra and interest respectively enabled fine and amount
							else {
								result = "please check your fines...!";
								
							}
						}//start you have done the all fines
						else if(fine.getInterest_fine_count() > 1) {//start you have done the interest fine
							if((savings.getFine().isInterest_fine()) && !(savings.getFine().isSavings_fine() || savings.getFine().isExtra_fine() || savings.getFine().isDue_fine())) {//start enable only the interest fine
								
								interestFine = (long)(Math.ceil((dbSavings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
								interestFine = interestFine <= 1?fine.getInterest_fine_count()-1:(fine.getInterest_fine_count()-1)*interestFine;
								bank_amounts = (bank.getBank_amounts()+bank.getSavings_collection_type()+bank.getExtras_collection_type()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+interestFine);
								query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",fine="+(dbSavings.getFineAmount()+interestFine)+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+interestFine)+",interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count()))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update fine set interest_fine_count=1,due_fine_count="+(fine.getDue_fine_count()+1)+",interest_fine="+(fine.getInterest_fine()+interestFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+","+(interestFine)+","+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)*fine.getInterest_fine_count())+",0,now());";
								
								result = "successfully stored";
								
							}//end enable only the interest fine
							else {
								result = "please check your fines...!";
								
							}
						}//end you have done the interest fine
						else if(savings.getFine().isSavings_fine() || savings.getFine().isInterest_fine() || savings.getFine().isDue_fine() || savings.getFine().isExtra_fine()) {//start no fines but fine is enabled
							result = "please check your fines...!";
							
						}//end no fines but fine is enabled
						else {//start store savings,extra,interest and due is fine
							bank_amounts = (bank.getBank_amounts()+bank.getSavings_collection_type()+bank.getExtras_collection_type()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100))+totalFine);
							query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",interest="+(dbSavings.getInterest()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)))+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"update banks set interest_amounts="+(bank.getInterest_amounts()+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100)))+",savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
									+"update fine set due_fine_count="+(fine.getDue_fine_count()+1)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+","+(totalFine)+","+(long)(Math.ceil(dbSavings.getDue()*bank.getInterest_collection_type()/100))+","+(savings.getDue())+",now());";
							
							result = "successfully stored";
							
						}
					}//end you enabled interest only
					else if(savings.getDue() > 0) {//start due only give without interest	//////logic issue (because it when true ....)
						result = "due only not accepted...!";
						
					}//end due only give without interest
					else {//start only interest and due is give because fine
						if(fine.getSavings_fine_count() > 1) {//start have done the savings fine
							if(savings.getFine().isSavings_fine() && savings.getFine().isExtra_fine() && !(savings.getFine().isInterest_fine() || savings.getFine().isDue_fine())) {//start due and interest is fine
								
								savingsFine = ((long)Math.ceil(bank.getSavings_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getSavings_fine_count()-1);
								extraFine = ((long)Math.ceil(bank.getExtras_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getExtra_fine_count()-1);
								totalFine = savingsFine+extraFine;
								bank_amounts = (bank.getBank_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+((bank.getExtras_collection_type()*fine.getExtra_fine_count())+totalFine);
								
								query = "update savings set savings="+(dbSavings.getSavings()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra="+(dbSavings.getExtra()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",fine="+(dbSavings.getFineAmount()+totalFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"update banks set fine_amounts="+(bank.getFine_amounts()+totalFine)+",savings_amounts="+(bank.getSavings_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra_amounts="+(bank.getExtra_amounts()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
										+"update fine set savings_fine_count=1,extra_fine_count=1,interest_fine_count="+(fine.getInterest_fine_count()+1)+",due_fine_count="+(fine.getDue_fine_count()+1)+",savings_fine="+(fine.getSavings_fine()+savingsFine)+",extra_fine="+(fine.getExtra_fine()+extraFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
										+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type()*fine.getSavings_fine_count())+","+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+","+(totalFine)+",0,0,now());";
								
								result = "successfully stored...!";
						
							}//end due and interest is fine
							else {
								result = "please check your fines...!";
								
							}
						}//end have done the savings fine
						else {//start store successfully only savings and extra without fine and interest,due is fine
								if(!savings.getFine().isInterest_fine() && !savings.getFine().isDue_fine() && !savings.getFine().isSavings_fine() && !savings.getFine().isExtra_fine()) {
									bank_amounts = (bank.getBank_amounts()+bank.getSavings_collection_type()+bank.getExtras_collection_type());
									query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
											+"update banks set savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
											+"update fine set interest_fine_count="+(fine.getInterest_fine_count()+1)+",due_fine_count="+(fine.getDue_fine_count()+1)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
											+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+","+(totalFine)+",0,0,now());";
									
									result = "successfully stored";
								
								}
								else {
									result = "check your fine...!";
								}
							
						}//end store successfully only savings and extra without fine and interest,due is fine
					}//end only interest and due is give because fine
				}//end have done the due
				else {//start without due and interest
					if(savings.isInterest() || savings.getDue() > 0) {
						result = "you haven't a due , please check your due and interest";
						
					}
					else if(fine.getSavings_fine_count() > 1) {//start have done the savings fine
						if(savings.getFine().isSavings_fine() && savings.getFine().isExtra_fine() && !(savings.getFine().isInterest_fine() || savings.getFine().isDue_fine())) {//start due and interest is fine

							savingsFine = ((long)Math.ceil(bank.getSavings_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getSavings_fine_count()-1);
							extraFine = ((long)Math.ceil(bank.getExtras_collection_type()*(bank.getSavings_extras_fine_type()/100)))*(fine.getExtra_fine_count()-1);
							totalFine = savingsFine+extraFine;
							bank_amounts = (bank.getBank_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count())+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+totalFine);
							
							query = "update savings set savings="+(dbSavings.getSavings()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra="+(dbSavings.getExtra()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",fine="+(dbSavings.getFineAmount()+totalFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"update banks set fine_amounts="+(bank.getFine_amounts()+totalFine)+",savings_amounts="+(bank.getSavings_amounts()+(bank.getSavings_collection_type()*fine.getSavings_fine_count()))+",extra_amounts="+(bank.getExtra_amounts()+(bank.getExtras_collection_type()*fine.getExtra_fine_count()))+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
									+"update fine set savings_fine_count=1,extra_fine_count=1,savings_fine="+(fine.getSavings_fine()+savingsFine)+",extra_fine="+(fine.getExtra_fine()+extraFine)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type()*fine.getSavings_fine_count())+","+(bank.getExtras_collection_type()*fine.getExtra_fine_count())+","+(totalFine)+",0,0,now());";
							
							result = "successfully stored";
						
						}//end due and interest is fine
						else {
							result = "please check your fines...!";
						
						}
					}//end have done the savings fine
					else {//start no fines and savings and extras perfectively
						if(savings.getFine().isSavings_fine() || savings.getFine().isExtra_fine() || savings.getFine().isInterest_fine() || savings.getFine().isDue_fine()) {//start you don't put the fine please
							result = "please check your fines...!";
						
						}
						else {
							System.out.println("check savings : "+dbSavings.getSavings());
							bank_amounts = (bank.getBank_amounts()+bank.getSavings_collection_type()+bank.getExtras_collection_type());
							query = "update savings set savings="+(dbSavings.getSavings()+bank.getSavings_collection_type())+",extra="+(dbSavings.getExtra()+bank.getExtras_collection_type())+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
									+"update banks set savings_amounts="+(bank.getSavings_amounts()+bank.getSavings_collection_type())+",extra_amounts="+(bank.getExtra_amounts()+bank.getExtras_collection_type())+",bank_amounts="+bank_amounts+",share_amounts="+(bank_amounts/bank.getMembers_count())+" where id="+bankId+";"
									+"insert into savings_history values("+(savings.getUserId())+","+bankId+","+(bank.getSavings_collection_type())+","+(bank.getExtras_collection_type())+",0,0,0,now());";
							
							result = "successfully stored";
						
						}
					}//end no fines and savings and extras perfectively
				}//start without due and interest
			}//end not enable interest no due
		}//end enable savings and extra
		else {
			if(savings.isSavings()) {
				result = "only savings is not accepted...!";
				
			}
			else if(savings.isExtra()) {
				result = "only extra is not accepted...!";
				
			}
			else {
				if(dbSavings.getDue() > 0) {
					
					query = "update fine set savings_fine_count="+(fine.getSavings_fine_count()+1)+",extra_fine_count="+(fine.getExtra_fine_count()+1)+",interest_fine_count="+(fine.getInterest_fine_count()+1)+",due_fine_count="+(fine.getDue_fine_count()+1)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
							+"insert into savings_history values("+(savings.getUserId())+","+bankId+",0,0,0,0,0,now());";
					
					result = "successfully stored";
				
				}
				else {
					query = "update fine set savings_fine_count="+(fine.getSavings_fine_count()+1)+",extra_fine_count="+(fine.getExtra_fine_count()+1)+",updated_at=now() where userId="+(savings.getUserId())+" && bankId="+bankId+";"
							+"insert into savings_history values("+(savings.getUserId())+","+bankId+",0,0,0,0,0,now());";
					
					result = "successfully stored";
				
				}
			}
		}
		if(query == "");
		else SaveData.save(query);
		map.put("result",result);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap save(BarrowModel barrow, long bankId) {
		String query = "";
		HashMap map = new HashMap();
		map = GetData.getBarrowCheck(bankId,barrow.getUserId());
		if((int)map.get("savings_fine_count") > 1) {
			System.out.println("you should pay the savings fine...!");
			map.put("result", "you should pay the savings fine...!");
		}
		else if((long)map.get("due") > 0) {
			System.out.println("you have a due so clear please...!");
			map.put("result", "you have a due so clear please...!");
		}
		else if(((long)map.get("bank_amounts")-(long)map.get("barrowed_amounts")) < barrow.getBarrowed_amount()) {
			System.out.println("sorry bank amount is less than you wanted...!");
			map.put("result", "sorry bank amount is less than you wanted...!");
		}
		else {
			
			query = "update savings set due="+(barrow.getBarrowed_amount())+" where userId="+(barrow.getUserId())+" && bankId="+bankId+";"
					+"update banks set barrowed_amounts="+((long)map.get("barrowed_amounts")+barrow.getBarrowed_amount())+" where id="+bankId+";"
					+"update barrow set barrowed_amount="+(barrow.getBarrowed_amount())+",barrow_type="+(barrow.getBarrow_type())+",updated_at=now() where userId="+(barrow.getUserId())+" && bankId="+bankId+";"
					+"insert into barrow_history values("+barrow.getUserId()+","+bankId+","+(barrow.getBarrowed_amount())+","+(barrow.getBarrow_type())+",now());";
			
			SaveData.save(query);
			System.out.println("stored successfully ...!");
			map.put("result", "stored successfully ...!");
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap passwordRePassword(long userId, String password) {
		HashMap map = new HashMap();
		String query = "update users set userPassword='"+password+"' where id="+userId;
		SaveData.save(query);
		map.put("result","password reset successfully...!");
		map.put("isResult", true);
		return map;
	}
	
	private static void save(String query) {//invoked by current class
		java.sql.Connection con = Connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			System.out.println("save query is (before) : "+query);
			stmt.executeUpdate(query);
			System.out.println("save successfully (after) : "+query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
