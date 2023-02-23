package model;

import java.util.Date;

public class BankModel {
	private int members_count,savings_collection_type,extras_collection_type,durations_value;
	private long adminId,id,fine_amounts,interest_amounts,savings_amounts,extra_amounts,barrowed_amounts,bank_amounts,share_amounts;
	private short durations,limited_type,group_type;
	float interest_collection_type,savings_extras_fine_type,due_interest_fine_type;
	private String name,descryptions,group_type_value;
	private Date created_by,updated_by;
	public BankModel(long adminId, String name, String descryptions, short durations, short interest_collection_type, short savings_collection_type,
			short extras_collection_type, short savings_extras_fine_type, short due_interest_fine_type,
			short limited_type, short group_type, long bank_amounts) {
		super();
		this.adminId = adminId;
		this.durations = durations;
		this.interest_collection_type = interest_collection_type;
		this.savings_collection_type = savings_collection_type;
		this.extras_collection_type = extras_collection_type;
		this.savings_extras_fine_type = savings_extras_fine_type;
		this.due_interest_fine_type = due_interest_fine_type;
		this.limited_type = limited_type;
		this.group_type = group_type;
		this.name = name;
		this.descryptions = descryptions;
		this.bank_amounts = bank_amounts;
	}
	public BankModel(long id) {
		this.id = id;
	}
	public BankModel() {
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long bankId) {
		this.id = bankId;
	}
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int getMembers_count() {
		return members_count;
	}
	public void setMembers_count(int members_count) {
		this.members_count = members_count;
	}
	public long getFine_amounts() {
		return fine_amounts;
	}
	public void setFine_amounts(long fine_amounts) {
		this.fine_amounts = fine_amounts;
	}
	public long getInterest_amounts() {
		return interest_amounts;
	}
	public void setInterest_amounts(long interest_amounts) {
		this.interest_amounts = interest_amounts;
	}
	public long getSavings_amounts() {
		return savings_amounts;
	}
	public void setSavings_amounts(long savings_amounts) {
		this.savings_amounts = savings_amounts;
	}
	public long getExtra_amounts() {
		return extra_amounts;
	}
	public void setExtra_amounts(long extra_amounts) {
		this.extra_amounts = extra_amounts;
	}
	public long getBarrowed_amounts() {
		return barrowed_amounts;
	}
	public void setBarrowed_amounts(long barrowed_amounts) {
		this.barrowed_amounts = barrowed_amounts;
	}
	public long getBank_amounts() {
		return bank_amounts;
	}
	public void setBank_amounts(long bank_amounts) {
		this.bank_amounts = bank_amounts;
	}
	public long getShare_amounts() {
		return share_amounts;
	}
	public void setShare_amounts(long share_amounts) {
		this.share_amounts = share_amounts;
	}
	public short getDurations() {
		return durations;
	}
	public void setDurations(short durations) {
		this.durations = durations;
	}
	public float getInterest_collection_type() {
		return interest_collection_type;
	}
	public void setInterest_collection_type(float interest_collection_type) {
		this.interest_collection_type = interest_collection_type;
	}
	public int getSavings_collection_type() {
		return savings_collection_type;
	}
	public void setSavings_collection_type(int savings_collection_type) {
		this.savings_collection_type = savings_collection_type;
	}
	public int getExtras_collection_type() {
		return extras_collection_type;
	}
	public void setExtras_collection_type(int extras_collection_type) {
		this.extras_collection_type = extras_collection_type;
	}
	public float getSavings_extras_fine_type() {
		return savings_extras_fine_type;
	}
	public void setSavings_extras_fine_type(float savings_extras_fine_type) {
		this.savings_extras_fine_type = savings_extras_fine_type;
	}
	public float getDue_interest_fine_type() {
		return due_interest_fine_type;
	}
	public void setDue_interest_fine_type(float due_interest_fine_type) {
		this.due_interest_fine_type = due_interest_fine_type;
	}
	public short getLimited_type() {
		return limited_type;
	}
	public void setLimited_type(short limited_type) {
		this.limited_type = limited_type;
	}
	public short getGroup_type() {
		return group_type;
	}
	public void setGroup_type(short group_type) {
		this.group_type = group_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescryptions() {
		return descryptions;
	}
	public void setDescryptions(String descryptions) {
		this.descryptions = descryptions;
	}
	public Date getCreated_by() {
		return created_by;
	}
	public void setCreated_by(Date created_by) {
		this.created_by = created_by;
	}
	public Date getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(Date updated_by) {
		this.updated_by = updated_by;
	}
	public void setDurations_value(int durations_value) {
		this.durations_value = durations_value;
	}
	public int getDurations_value() {
		return durations_value;
	}
	public void setGroup_type_value(String group_type_value) {
		this.group_type_value = group_type_value;
	}
	public String getGroup_type_value() {
		return group_type_value;
	}
}
