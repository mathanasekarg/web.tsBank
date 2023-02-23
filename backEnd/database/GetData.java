package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import model.BankModel;
import model.BarrowModel;
import model.FineModel;
import model.PermissionsModel;
import model.SavingsModel;
import model.UserModel;

public class GetData {

	public static long getId(String query) {
		long id = 0;
		java.sql.Connection con = Connection.getConnection();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			id =  rs.getLong("id");
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		}
	}

	public static boolean getPermissionsAndId(String userName) {		//	!importsant
		String query = "select permissions,id from users where username = '"+userName+"'";
		java.sql.Connection con = Connection.getConnection();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			UserModel.setPermissions(rs.getString("permissions"));
			UserModel.setUserId(rs.getLong("id"));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
	}

	public static SavingsModel getSavings(long userId,long bankId) {		//	!important
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from savings where userId="+userId+" && bankId="+bankId+"";
		SavingsModel savingsModel = new SavingsModel();
		
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			System.out.println("dbsavings query ------------------- : "+query);
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
			
//			savingsModel.setNext_savings(rs.getInt("next_savings"));
//			savingsModel.setNext_extra(rs.getInt("next_extra"));
//			savingsModel.setNext_fine(rs.getInt("next_fine"));
//			savingsModel.setNext_interest(rs.getInt("next_interest"));
//			savingsModel.setNext_due(rs.getInt("next_due"));
			savingsModel.setSavings(rs.getLong("savings"));
			savingsModel.setExtra(rs.getLong("extra"));
			savingsModel.setInterest(rs.getLong("interest"));
			savingsModel.setDue(rs.getLong("due"));
			savingsModel.setFineAmount(rs.getLong("fine"));
			savingsModel.setUpdated_at(rs.getDate("updated_at"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return savingsModel;
	}

	public static FineModel getFine(long userId,long bankId) {		//	!important
		java.sql.Connection con = Connection.getConnection();
		FineModel fineModel = new FineModel();
		String query = "select * from fine where userId="+userId+" && bankId="+bankId+"";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			fineModel.setSavings_fine_count(rs.getInt("savings_fine_count"));
			fineModel.setExtra_fine_count(rs.getInt("extra_fine_count"));
			fineModel.setInterest_fine_count(rs.getInt("interest_fine_count"));
			fineModel.setDue_fine_count(rs.getInt("due_fine_count"));
			fineModel.setSavings_fine(rs.getInt("savings_fine"));
			fineModel.setExtra_fine(rs.getInt("extra_fine"));
			fineModel.setInterest_fine(rs.getInt("interest_fine"));
			fineModel.setDue_fine(rs.getInt("due_fine"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return fineModel;
	}

	public static BankModel getBank(long bankId) {		//	!important
		String query = "select banks.adminId,banks.name,banks.members_count,banks.fine_amounts,banks.interest_amounts,banks.savings_amounts,banks.extra_amounts,banks.barrowed_amounts,banks.bank_amounts,banks.descryptions,banks.share_amounts,banks.created_at,banks.updated_at,"
				+"durations.durations,interest_collection_type.interest_collection_type,savings_collection_type.savings_collection_type,extras_collection_type.extras_collection_type,"
				+"savings_extras_fine_type.savings_extras_fine_type,due_interest_fine_type.due_interest_fine_type,limited_type.limited_type,group_type.group_type "
				+"from banks "
				+"inner join durations on durations.id = banks.durations "
				+"inner join interest_collection_type on interest_collection_type.id = banks.interest_collection_type "
				+"inner join savings_collection_type on savings_collection_type.id = banks.savings_collection_type "
				+"inner join extras_collection_type on extras_collection_type.id = banks.extras_collection_type "
				+"inner join savings_extras_fine_type on savings_extras_fine_type.id = banks.savings_extras_fine_type "
				+"inner join due_interest_fine_type on due_interest_fine_type.id = banks.due_interest_fine_type "
				+"inner join limited_type on limited_type.id = banks.limited_type "
				+"inner join group_type on group_type.id = banks.group_type "
				+"where banks.id="+bankId+"";
		
		java.sql.Connection con = Connection.getConnection();
		BankModel bankModel = new BankModel();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			bankModel.setId((int)bankId);
			rs.next();
			bankModel.setName(rs.getString("name"));
			bankModel.setCreated_by(rs.getDate("created_at"));
			bankModel.setUpdated_by(rs.getDate("updated_at"));
			bankModel.setMembers_count(rs.getInt("members_count"));
			bankModel.setFine_amounts(rs.getLong("Fine_amounts"));
			bankModel.setInterest_amounts(rs.getLong("interest_amounts"));
			bankModel.setSavings_amounts(rs.getLong("savings_amounts"));
			bankModel.setExtra_amounts(rs.getLong("extra_amounts"));
			bankModel.setBarrowed_amounts(rs.getLong("barrowed_amounts"));
			bankModel.setBank_amounts(rs.getLong("bank_amounts"));
			bankModel.setShare_amounts(rs.getLong("share_amounts"));
			bankModel.setDurations_value(rs.getInt("durations"));
			bankModel.setSavings_collection_type(rs.getInt("savings_collection_type"));
			bankModel.setInterest_collection_type(rs.getFloat("interest_collection_type"));
			bankModel.setExtras_collection_type(rs.getInt("extras_collection_type"));
			bankModel.setSavings_extras_fine_type(rs.getFloat("savings_extras_fine_type"));
			bankModel.setDue_interest_fine_type(rs.getFloat("due_interest_fine_type"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return bankModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getDashboardByUserId(long userId) {		//	!important
		HashMap map = new HashMap();
		String query = "select savings.savings,savings.extra,savings.interest,savings.fine,savings.due,"
				+"fine.savings_fine,fine.extra_fine,fine.interest_fine,fine.due_fine,"
				+"barrow.barrowed_amount "
				+"from savings "
				+"inner join fine on fine.userId=savings.userId "
				+"inner join barrow on barrow.userId=savings.userId "
				+"where savings.userId="+userId+" && fine.userId="+userId+";";
		java.sql.Connection con = Connection.getConnection();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				map.put("savings",rs.getLong("savings_amounts"));
				map.put("extra",rs.getLong("extra_amounts"));
				map.put("interest",rs.getLong("interest_amounts"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

	public static BarrowModel getBarrow(long userId,long bankId) {		//	!important
		java.sql.Connection con = Connection.getConnection();
		String query = "select barrow.userId,barrow.bankId,barrow.barrowed_amount,barrow.barrow_type,barrow.updated_at "
				+"from barrow "
				+"inner join barrow_type on barrow_type.id=barrow.barrow_type "
				+"where barrow.userId="+userId+" && barrow.bankId="+bankId+"";
		BarrowModel barrowModel = new BarrowModel();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			barrowModel.setBarrow_type(rs.getShort("barrow_type"));
			barrowModel.setBarrowed_amount(rs.getInt("barrowed_amount"));
		} catch (SQLException e) {
			e.printStackTrace();
			return barrowModel;
		}
		return barrowModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap checkSavings(long bankId, long userId) {		//	!important
		FineModel fine = new FineModel();
		BankModel bank = new BankModel(bankId);
		SavingsModel savings = new SavingsModel();
		BarrowModel barrow = new BarrowModel();
		String query = "select fine.savings_fine_count as s_f_c ,fine.extra_fine_count as e_f_c,fine.interest_fine_count as i_f_c,fine.due_fine_count as d_f_c,"
				+"savings.due,barrow_type.barrow_type,"
				+"savings_collection_type.savings_collection_type,extras_collection_type.extras_collection_type,interest_collection_type.interest_collection_type,savings_extras_fine_type.savings_extras_fine_type,due_interest_fine_type.due_interest_fine_type "
				+"from savings "
				+"right join banks on banks.id="+bankId+" "
				+"right join barrow on barrow.userId="+userId+" "
				+"right join extras_collection_type on extras_collection_type.id = banks.extras_collection_type "
				+"right join savings_collection_type on savings_collection_type.id = banks.savings_collection_type "
				+"right join interest_collection_type on interest_collection_type.id = banks.interest_collection_type "
				+"right join savings_extras_fine_type on savings_extras_fine_type.id = banks.savings_extras_fine_type "
				+"right join due_interest_fine_type on due_interest_fine_type.id = banks.due_interest_fine_type "
				+"right join barrow_type on barrow_type.id=barrow.barrow_type "
				+"right join fine on fine.userId=savings.userId "
				+"where savings.userId="+userId+" && savings.bankId="+bankId+" && banks.id="+bankId+" && barrow.userId="+userId+" && barrow.bankId="+bankId+" && fine.userId="+userId+" && fine.bankId="+bankId+";";
		java.sql.Connection con = Connection.getConnection();
		HashMap map = new HashMap();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			long savingsFine=0,extraFine=0,interestFine=0,dueFine=0;
			rs.next();{
				fine.setSavings_fine_count(rs.getInt("s_f_c"));
				fine.setExtra_fine_count(rs.getInt("e_f_c"));
				fine.setInterest_fine_count(rs.getInt("i_f_c"));
				fine.setDue_fine_count(rs.getInt("d_f_c"));
//				fine.setSavings_fine_count(rs.getInt("savings_fine_count"));
//				fine.setExtra_fine_count(rs.getInt("extra_fine_count"));
//				fine.setInterest_fine_count(rs.getInt("interest_fine_count"));
//				fine.setDue_fine_count(rs.getInt("due_fine_count"));
				bank.setSavings_collection_type(rs.getInt("savings_collection_type"));
				bank.setExtras_collection_type(rs.getInt("extras_collection_type"));
				bank.setInterest_collection_type(rs.getFloat("interest_collection_type"));
				bank.setSavings_extras_fine_type(rs.getInt("savings_extras_fine_type"));
				bank.setDue_interest_fine_type(rs.getInt("due_interest_fine_type"));
				savings.setDue(rs.getLong("due"));
				barrow.setBarrow_type_value(rs.getFloat("barrow_type"));
			}
				map.put("savings", fine.getSavings_fine_count()*bank.getSavings_collection_type());
				map.put("extra", fine.getExtra_fine_count()	*bank.getExtras_collection_type());
				map.put("due", savings.getDue());
				map.put("interest", (long)Math.ceil((savings.getDue()*bank.getInterest_collection_type())/100)==1?fine.getInterest_fine_count()==1?1:fine.getInterest_fine_count():fine.getInterest_fine_count()*(int)Math.ceil((savings.getDue()*bank.getInterest_collection_type())/100));
				if(fine.getSavings_fine_count() > 1) {
					if(fine.getInterest_fine_count() > 1) {
						savingsFine = (long)Math.ceil(bank.getSavings_collection_type()*(bank.getSavings_extras_fine_type()/100));
						savingsFine = savingsFine <= 1?fine.getSavings_fine_count()-1:(fine.getSavings_fine_count()-1)*savingsFine;
						extraFine = (long)Math.ceil(bank.getExtras_collection_type()*(bank.getSavings_extras_fine_type()/100));
						extraFine = extraFine <= 1?fine.getExtra_fine_count()-1:(fine.getExtra_fine_count()-1)*extraFine;
												
						map.put("savings_fine", true);
						map.put("extra_fine", true);
						map.put("interest_fine", true);
						map.put("due_fine", true);
					}
					else {
						savingsFine = (long)Math.ceil(bank.getSavings_collection_type()*(bank.getSavings_extras_fine_type()/100));
						System.out.println("savings fine : "+savingsFine);
						savingsFine = savingsFine <= 1?fine.getSavings_fine_count()-1:(fine.getSavings_fine_count()-1)*savingsFine;
						System.out.println("savings fine : "+savingsFine);
						extraFine = (long)Math.ceil(bank.getExtras_collection_type()*(bank.getSavings_extras_fine_type()/100));
						System.out.println("extra fine : "+extraFine);
						extraFine = extraFine <= 1?fine.getExtra_fine_count()-1:(fine.getExtra_fine_count()-1)*extraFine;
						System.out.println("extra fine : "+extraFine);
						
						map.put("savings_fine", true);
						map.put("extra_fine", true);
						map.put("interest_fine", false);
						map.put("due_fine", false);
					}
				}
				else {
					if(fine.getInterest_fine_count() > 1) {

						System.out.println("interest fine : inside : "+interestFine);
						map.put("savings_fine", false);
						map.put("extra_fine", false);
						map.put("interest_fine", true);
						map.put("due_fine", true);
					}
					else {
						if(fine.getDue_fine_count() > 1) {
				
							map.put("savings_fine", false);
							map.put("extra_fine", false);
							map.put("interest_fine", false);
							map.put("due_fine", true);
						}
						else {
							map.put("savings_fine", false);
							map.put("extra_fine", false);
							map.put("interest_fine", false);
							map.put("due_fine", false);
						}
					}
				}
				interestFine = (long)(Math.ceil((savings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
				interestFine = interestFine <= 1?fine.getInterest_fine_count()-1:(fine.getInterest_fine_count()-1)*interestFine;
				dueFine = (long)Math.ceil(((savings.getDue()*bank.getInterest_collection_type())/100)*(bank.getDue_interest_fine_type()/100));
				dueFine = dueFine <= 1?fine.getDue_fine_count()-1:(fine.getDue_fine_count()-1)*dueFine;
				System.out.println("interest fine : "+interestFine);
				map.put("savings_fine_amount", savingsFine);
				map.put("extra_fine_amount", extraFine);
				map.put("interest_fine_amount", interestFine);
				map.put("due_fine_amount", dueFine);
				map.put("savings_fine_count", fine.getSavings_fine_count()-1);
				map.put("extra_fine_count", fine.getExtra_fine_count()-1);
				map.put("interest_fine_count", fine.getInterest_fine_count()-1);
				map.put("due_fine_count", fine.getDue_fine_count()-1);
		} catch (SQLException e) {
			e.printStackTrace();
			map.put("scan_result", "anything is wrong in getData class savings check");
			return map;
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getBarrowCheck(long bankId,long userId) {		//	!important
		String query = "select fine.savings_fine_count,savings.due,banks.bank_amounts,banks.barrowed_amounts from savings "
				+"inner join fine on fine.userId=savings.userId "
				+"inner join banks on banks.id="+bankId+" "
				+"where savings.userId = "+userId+" && savings.bankId = "+bankId+"";
		java.sql.Connection con = Connection.getConnection();
		HashMap map = new HashMap();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				map.put("savings_fine_count", rs.getInt("savings_fine_count"));
				map.put("due", rs.getLong("due"));
				map.put("bank_amounts", rs.getLong("bank_amounts"));
				map.put("barrowed_amounts", rs.getLong("barrowed_amounts"));
		} catch (SQLException e) {
			e.printStackTrace();
			map.put("scan_result", "anything is wrong in getData class");
			return map;
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getUserSavings(long bankId) {		//	!important
		String query = "select users.username,users.id,"
				+"savings.savings,savings.extra,savings.fine,savings.interest,savings.due,"
				+"fine.savings_fine,fine.extra_fine,fine.interest_fine,fine.due_fine"
				+" from savings"
				+" inner join users on users.id=savings.userId"
				+" inner join fine on users.id=fine.userId"
				+" where savings.bankId="+bankId+" && fine.bankId="+bankId+";";
		java.sql.Connection con = Connection.getConnection();
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("username", rs.getString("username"));
				map.put("id", rs.getLong("id"));
				map.put("savings", rs.getLong("savings"));
				map.put("extra", rs.getLong("extra"));
				map.put("interest", rs.getLong("interest"));
				map.put("barrow", rs.getLong("due"));
				map.put("savings_fine", rs.getLong("savings_fine")-1);
				map.put("extra_fine", rs.getLong("extra_fine")-1);
				map.put("interest_fine", rs.getLong("interest_fine")-1);
				map.put("due_fine", rs.getLong("due_fine")-1);
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map = new HashMap();
			map.put("scan_result", "anything is wrong in getData class");
			list.add(map);
			return list;
		}
		return list;
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getUserSavingsList(long bankId) {		//	!important
		String query = "select users.username,users.id,savings.updated_at"
				+" from savings"
				+" inner join users on users.id=savings.userId"
				+" where savings.bankId="+bankId+";";
		java.sql.Connection con = Connection.getConnection();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = null,durationAt = null;
		
		
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			Date updated_at = null;
			while(rs.next()) {
				map = new HashMap();
				map.put("username", rs.getString("username"));
				map.put("userId", rs.getLong("id"));
				updated_at = rs.getDate("updated_at");
				if(updated_at == null) { 		//		when created at today and when not yet submitted ready to submit
					map.put("isSubmitted", true);
					map.put("isIgnore", false);
				}
				else {
					try {
						durationAt = dateFormat.parse(dateFormat.format(updated_at));
						todayDate = dateFormat.parse(dateFormat.format(new Date()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					if(todayDate.compareTo(durationAt) > 0) { 		//		when ignore the member (not eligible)
						map.put("isSubmitted", false);
						map.put("isIgnore", true);
					}
					else if(todayDate.compareTo(durationAt) < 0) {		//		when wait for the next durations
						map.put("isSubmitted", true);
						map.put("isIgnore", false);
					}
					else if(todayDate.compareTo(durationAt) == 0) {		//		when not yet submitted ready to submit
						map.put("isSubmitted", false);
						map.put("isIgnore", false);
					}
				}
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map = new HashMap();
			map.put("scan_result", "anything is wrong in getData class");
			list.add(map);
			return list;
		}
		return list;
	}

	
	public static int getMembers_count(long bankId) {		//	!important
		java.sql.Connection con = Connection.getConnection();
		int members_count = 0;
		String query = "select members_count from banks where id="+bankId+"";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				members_count = rs.getInt("members_count");
		} catch (SQLException e) {
			e.printStackTrace();
			return members_count;
		}
		return members_count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getAdminBanks(long adminId) {		//	!important
		String query = "select banks.id,banks.name,banks.members_count,durations.durations,banks.bank_amounts,banks.barrowed_amounts,group_type.group_type,banks.share_amounts,limited_type.limited_type "
				+"from banks "
				+"inner join durations on durations.id=banks.durations "
				+"inner join group_type on group_type.id=banks.group_type "
				+"inner join limited_type on limited_type.id=banks.limited_type "
				+"where banks.adminId="+adminId+";";
		java.sql.Connection con = Connection.getConnection();
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getLong("id"));
				map.put("name", rs.getString("name"));
				map.put("members_count", rs.getInt("members_count"));
				map.put("bank_amounts", rs.getLong("bank_amounts"));
				map.put("barrowed_amounts", rs.getLong("barrowed_amounts"));
				map.put("group_type", rs.getString("group_type"));
				map.put("share_amounts", rs.getLong("share_amounts"));
				map.put("limited_type", rs.getInt("limited_type"));
				map.put("durations", rs.getInt("durations"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getLoanList(long bankId) {		//	!important
		String query = "select users.id,users.username,barrow.barrowed_amount,barrow_type.barrow_type,savings.due "
				+"from barrow "
				+"inner join barrow_type on barrow_type.id=barrow.barrow_type "
				+"inner join savings on savings.userId=barrow.userId "
				+"inner join users on savings.userId=users.id "
				+"where barrow.bankId="+bankId+" && savings.bankId="+bankId+";";
		java.sql.Connection con = Connection.getConnection();
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getLong("id"));
				map.put("username", rs.getString("username"));
				map.put("barrowed_amount", rs.getLong("barrowed_amount"));
				map.put("barrow_type", rs.getInt("barrow_type"));
				map.put("due", rs.getLong("due"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getNavbar(long userId, long bankId) {		//	!important
		String query = "select banks.name,users.username from users inner join banks on banks.id=banks.id where banks.id="+bankId+" && users.id="+userId+"";
		java.sql.Connection con = Connection.getConnection();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				map = new HashMap();
				map.put("bankname", rs.getString("name"));
				map.put("username", rs.getString("username"));
		} catch (SQLException e) {
			e.printStackTrace();
			map = new HashMap();
			map.put("scan_result", "anything is wrong in getData class");
			
			return map;
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getSavingsHistory(long bankId) {		//	!important
		String query = "select users.username,savings_history.userId,savings_history.savings,savings_history.extra,savings_history.fine,savings_history.interest,savings_history.due,savings_history.updated_at"
				+ " from savings_history"
				+ " inner join users on users.id=savings_history.userId"
				+ " where bankId="+bankId+";";
		java.sql.Connection con = Connection.getConnection();
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				if(rs.getDate("updated_at") == null) continue;
				map = new HashMap();
				map.put("userId", rs.getLong("userId"));
				map.put("username", rs.getString("username"));
				map.put("savings", rs.getLong("savings"));
				map.put("extra", rs.getLong("extra"));
				map.put("fine", rs.getLong("fine"));
				map.put("interest", rs.getLong("interest"));
				map.put("due", rs.getLong("due"));
				map.put("updated_at", rs.getDate("updated_at"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map = new HashMap();
			map.put("scan_result", "anything is wrong in getData class");
			list.add(map);
			return list;
		}
		return list;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getLoanHistory(long bankId) {		//	!important
		String query = "select users.username,barrow_history.userId,barrow_history.barrowed_amount,barrow_history.updated_at,barrow_type.barrow_type "
				+"from barrow_history"
				+ " inner join users on users.id=barrow_history.userId "
				+"inner join barrow_type on barrow_type.id=barrow_history.barrow_type "
				+"where barrow_history.bankId="+bankId+";";
		java.sql.Connection con = Connection.getConnection();
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				if(rs.getDate("updated_at") == null) continue;
				map = new HashMap();
				map.put("username", rs.getString("username"));
				map.put("userId", rs.getLong("userId"));
				map.put("barrowed_amount", rs.getInt("barrowed_amount"));
				map.put("barrow_type", rs.getInt("barrow_type"));
				map.put("updated_at", rs.getDate("updated_at"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	public static boolean isUsernameExist(String username) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select username from users where username='"+username+"'";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				rs.getString("username");
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public static boolean isNameExist(String name) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select name from banks where name='"+name+"'";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				rs.getString("name");
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static PermissionsModel getPermissions(long userId) {
		Gson gson = new Gson();
		java.sql.Connection con = Connection.getConnection();
		String query = "select permissions from users where id="+userId+"";
		PermissionsModel permissions = new PermissionsModel();
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				permissions = gson.fromJson((String) rs.getString("permissions").toString(), PermissionsModel.class);
		} catch (SQLException e) {
			e.printStackTrace();
			return permissions;
		}
		return permissions;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getPermissions() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from permissions";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("role"));
				map.put("permission", rs.getString("permissions"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getGender() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from gender";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("gender", rs.getString("gender"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getProfession() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from profession";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("profession", rs.getString("profession"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getCountry() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from country";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("country", rs.getString("country"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getBarrow_type() {		//	!important
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from barrow_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("barrow_type", rs.getString("barrow_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getState(String country) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from state where country_id="+country+"";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("country_id"));
				map.put("state", rs.getString("state"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getCategory() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from category";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("category", rs.getString("category"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getDurations() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from durations";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("durations", rs.getString("durations"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getInterest_collection_type() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from interest_collection_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("interest_collection_type", rs.getFloat("interest_collection_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getSavings_collection_type() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from savings_collection_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("savings_collection_type", rs.getInt("savings_collection_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getExtras_collection_type() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from extras_collection_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("extras_collection_type", rs.getInt("extras_collection_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getSavings_extras_fine_type() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from savings_extras_fine_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("savings_extras_fine_type", rs.getFloat("savings_extras_fine_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getDue_interest_fine_type() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from due_interest_fine_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("due_interest_fine_type", rs.getFloat("due_interest_fine_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getLimited_type() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from limited_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("limited_type", rs.getShort("limited_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getGroup_type() {
		java.sql.Connection con = Connection.getConnection();
		String query = "select * from group_type";
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("id", rs.getInt("id"));
				map.put("group_type", rs.getString("group_type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList getUsersInBank(long bankId) {
		String query = "select distinct users.id,users.username,users.DOB,users.mobile,users.email,users.marriedStatus,users.postalcode "
				+"from savings "
				+"inner join users on users.id=savings.userId "
				+"inner join banks on banks.id=savings.bankId "
				+"where savings.bankId="+bankId+";";
		java.sql.Connection con = Connection.getConnection();
		ArrayList list = new ArrayList();
		HashMap map = null;
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				map = new HashMap();
				map.put("userId", rs.getLong("id"));
				map.put("username", rs.getString("username"));
				map.put("email", rs.getString("email"));
				map.put("marriedStatus", rs.getBoolean("marriedStatus"));
				map.put("DOB", rs.getDate("DOB"));
				map.put("mobile", rs.getLong("mobile"));
				map.put("postalcode", rs.getLong("postalcode"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map = new HashMap();
			map.put("scan_result", "anything is wrong in getData class");
			list.add(map);
			return list;
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getUniqueBank(long bankId) {
		HashMap map = new HashMap();
		String query = "select * from banks where id = "+bankId;
		java.sql.Connection con = Connection.getConnection();
		
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				map.put("id",rs.getLong("id"));
				map.put("name",rs.getString("name"));
				map.put("descryptions",rs.getString("descryptions"));
				map.put("durations",rs.getInt("durations"));
				map.put("interest_collection_type",rs.getInt("interest_collection_type"));
				map.put("savings_collection_type",rs.getInt("savings_collection_type"));
				map.put("extras_collection_type",rs.getInt("extras_collection_type"));
				map.put("savings_extras_fine_type",rs.getInt("savings_extras_fine_type"));
				map.put("due_interest_fine_type",rs.getInt("due_interest_fine_type"));
				map.put("limited_type",rs.getInt("limited_type"));
				map.put("group_type",rs.getInt("group_type"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getUser(long userId) {
		HashMap map = new HashMap();
		String query = "select * from users where id = "+userId;
		java.sql.Connection con = Connection.getConnection();
		
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				map.put("id",rs.getLong("id"));
				map.put("username",rs.getString("username"));
				map.put("address",rs.getString("address"));
				map.put("gender",rs.getString("gender"));
				map.put("mobile",rs.getString("mobile"));
				map.put("email",rs.getString("email"));
				map.put("marriedStatus",rs.getBoolean("marriedStatus"));
				map.put("country",rs.getString("country"));
				map.put("state",rs.getString("state"));
				map.put("postalcode",rs.getString("postalcode"));
				map.put("profession",rs.getString("profession"));
				map.put("category",rs.getString("category"));
				map.put("permissions",rs.getString("permissions"));
				map.put("created_at",rs.getString("created_at"));
				map.put("updated_at",rs.getString("updated_at"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

	public static Long getShare_amounts(long bankId) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select share_amounts from banks where id="+bankId;
		long share_amounts = 0;
		
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				share_amounts = rs.getLong("share_amounts");
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return share_amounts;
	}

	public static long getBank_amounts(long bankId) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select bank_amounts from banks where id="+bankId;
		long bank_amounts = 0;
		
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			bank_amounts = rs.getLong("bank_amounts");
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bank_amounts;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap updatedatAndDurationat(long userId, long bankId) {
		HashMap map = new HashMap();
		java.sql.Connection con = Connection.getConnection();
		String query = "select banks.updated_at,durations.durations from banks "
//				+"inner join savings on savings.bankId="+bankId+" "
				+"inner join durations on durations.id=banks.durations "
				+"where banks.id="+bankId;
		
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			map.put("updated_at",rs.getDate("updated_at"));
			map.put("durations",rs.getInt("durations"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

}
