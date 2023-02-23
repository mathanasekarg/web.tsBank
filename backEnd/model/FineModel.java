package model;

import java.util.Date;

public class FineModel {
	private long userId,bankId,savings_fine,extra_fine,interest_fine,due_fine;
	private boolean isSavings_fine,isExtra_fine,isInterest_fine,isDue_fine;
	public boolean isSavings_fine() {
		return isSavings_fine;
	}
	public boolean isExtra_fine() {
		return isExtra_fine;
	}
	public boolean isInterest_fine() {
		return isInterest_fine;
	}
	public boolean isDue_fine() {
		return isDue_fine;
	}
	private int savings_fine_count,extra_fine_count,interest_fine_count,due_fine_count;
	Date updated_at;
	public FineModel(boolean isSavings_fine, boolean isExtra_fine, boolean isInterest_fine, boolean isDue_fine) {
		super();
		this.isSavings_fine = isSavings_fine;
		this.isExtra_fine = isExtra_fine;
		this.isInterest_fine = isInterest_fine;
		this.isDue_fine = isDue_fine;
	}
	public FineModel() {
		// TODO Auto-generated constructor stub
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	public long getSavings_fine() {
		return savings_fine;
	}
	public void setSavings_fine(long savings_fine) {
		this.savings_fine = savings_fine;
	}
	public long getExtra_fine() {
		return extra_fine;
	}
	public void setExtra_fine(long extra_fine) {
		this.extra_fine = extra_fine;
	}
	public long getInterest_fine() {
		return interest_fine;
	}
	public void setInterest_fine(long interest_fine) {
		this.interest_fine = interest_fine;
	}
	public long getDue_fine() {
		return due_fine;
	}
	public void setDue_fine(long due_fine) {
		this.due_fine = due_fine;
	}
	public int getSavings_fine_count() {
		return savings_fine_count;
	}
	public void setSavings_fine_count(int savings_fine_count) {
		this.savings_fine_count = savings_fine_count;
	}
	public int getExtra_fine_count() {
		return extra_fine_count;
	}
	public void setExtra_fine_count(int extra_fine_count) {
		this.extra_fine_count = extra_fine_count;
	}
	public int getInterest_fine_count() {
		return interest_fine_count;
	}
	public void setInterest_fine_count(int interest_fine_count) {
		this.interest_fine_count = interest_fine_count;
	}
	public int getDue_fine_count() {
		return due_fine_count;
	}
	public void setDue_fine_count(int due_fine_count) {
		this.due_fine_count = due_fine_count;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}
