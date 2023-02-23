package auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import database.Connection;

public class DataVerification {

	public static boolean usernameAndPassword(String username, String password) {		//	!important
		java.sql.Connection con = Connection.getConnection();
		String query = "select userPassword from users where username='"+username+"'";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				String dbPassword = rs.getString("userPassword");
			if(password.equals(dbPassword)) return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean isAdminBank(long adminId, long bankId) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select adminId,id from banks where adminId="+adminId+" && id="+bankId+"";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				rs.getLong("id");
				rs.getString("adminId");
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isUserBank(long userId, long bankId) {		//	!important
		java.sql.Connection con = Connection.getConnection();
		String query = "select userId from barrow where userId="+userId+" && bankId="+bankId+"";
		try {
			System.out.println("data verfivations user bank : "+query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				rs.getString("userId");
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int isGender(int gender) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from gender where id="+gender+") as gender";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("gender");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isCountry(int country) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from country where id="+country+") as country";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("country");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isState(int state) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from state where id="+state+") as state";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("state");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isProfession(int profession) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from profession where id="+profession+") as profession";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("profession");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isRole(int role) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from role where id="+role+") as role";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("role");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isCategory(int category) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from category where id="+category+") as category";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("category");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isDurations(short durations) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from durations where id="+durations+") as durations";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("durations");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isInterest_collection_type(float interest_collection_type) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from interest_collection_type where id="+interest_collection_type+") as interest_collection_type";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("interest_collection_type");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isSavings_collection_type(int savings_collection_type) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from savings_collection_type where id="+savings_collection_type+") as savings_collection_type";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("savings_collection_type");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isExtras_collection_type(int extras_collection_type) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from extras_collection_type where id="+extras_collection_type+") as extras_collection_type";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("extras_collection_type");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isSavings_extras_fine_type(float savings_extras_fine_type) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from savings_extras_fine_type where id="+savings_extras_fine_type+") as savings_extras_fine_type";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("savings_extras_fine_type");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isDue_interest_fine_type(float due_interest_fine_type) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from due_interest_fine_type where id="+due_interest_fine_type+") as due_interest_fine_type";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("due_interest_fine_type");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isLimited_type(short limited_type) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from limited_type where id="+limited_type+") as limited_type";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("limited_type");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isGroup_type(short group_type) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from group_type where id="+group_type+") as group_type";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("group_type");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int IsUser(long userId) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from users where id="+userId+") as id";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int isBrarrow_type(long barrowId) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select id from barrow_type where id="+barrowId+") as id;";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int isUsernameExist(String username) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select exists(select username from users where username='"+username+"') as username";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				return rs.getInt("username");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getNextStoreDuration(Date updated_at, int durations_value) {
		HashMap map = new HashMap();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		System.out.println(updated_at);
		try {
			if(updated_at == null) map.put("durationAt",dateFormat.parse(dateFormat.format(new Date())));
			else {
				calendar.setTime(updated_at);
				calendar.add(Calendar.DATE, durations_value);
				map.put("durationAt",dateFormat.parse(dateFormat.format(calendar.getTime())));
				System.out.println("when not null duration : "+map.get("durationAt"));
			}
			map.put("todayDate",dateFormat.parse(dateFormat.format(new Date())));
			map.put("flag", true);

//			System.out.println("duration : "+map.get("durationAt"));
//			System.out.println("updated : "+map.get("todayDate"));
		} catch (ParseException e) {
			e.printStackTrace();
			map.put("flag", false);
		}
		return map;
	}

	public static boolean isUsernameUpdate(String username,long userId) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select username,id from users where username='"+username+"'";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
				rs.getString("username");
				if(rs.getLong("id") == userId) return true;
				else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}


	public static boolean isBankNameUpdate(String name,long bankId) {
		java.sql.Connection con = Connection.getConnection();
		String query = "select name,id from banks where name='"+name+"'";
		try {
			System.out.println(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			rs.getString("name");
			if(rs.getLong("id") == bankId) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}


}
