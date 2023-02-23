package model;

import java.util.Date;

public class SavingsModel {
	private long userId,bankId,savings,extra,fineAmount,interest,due,next_savings,next_extra,next_fine,next_interest,next_due;
	FineModel fine;
	private boolean isUserId,isBankId,isSavings,isExtra,isInterest;
	private Date updated_at;
	private String result;
	public SavingsModel(long userId, boolean savings, boolean extra, FineModel fine, boolean interest, long due) {
		super();
		this.userId = userId;
		this.isSavings = savings;
		this.isExtra = extra;
		this.fine = fine;
		this.isInterest = interest;
		this.due = due;
	}
	public SavingsModel(long next_savings, long next_extra, long next_fine, long next_interest, long next_due) {
		super();
		this.next_savings = next_savings;
		this.next_extra = next_extra;
		this.next_fine = next_fine;
		this.next_interest = next_interest;
		this.next_due = next_due;
	}
	public SavingsModel() {
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
	public long getSavings() {
		return savings;
	}
	public void setSavings(long savings) {
		this.savings = savings;
	}
	public long getExtra() {
		return extra;
	}
	public void setExtra(long extra) {
		this.extra = extra;
	}
	public long getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(long fineAmount) {
		this.fineAmount = fineAmount;
	}
	public long getInterest() {
		return interest;
	}
	public void setInterest(long interest) {
		this.interest = interest;
	}
	public long getDue() {
		return due;
	}
	public void setDue(long due) {
		this.due = due;
	}
	public long getNext_savings() {
		return next_savings;
	}
	public void setNext_savings(long next_savings) {
		this.next_savings = next_savings;
	}
	public long getNext_extra() {
		return next_extra;
	}
	public void setNext_extra(long next_extra) {
		this.next_extra = next_extra;
	}
	public long getNext_fine() {
		return next_fine;
	}
	public void setNext_fine(long next_fine) {
		this.next_fine = next_fine;
	}
	public long getNext_interest() {
		return next_interest;
	}
	public void setNext_interest(long next_interest) {
		this.next_interest = next_interest;
	}
	public long getNext_due() {
		return next_due;
	}
	public void setNext_due(long next_due) {
		this.next_due = next_due;
	}
	public FineModel getFine() {
		return fine;
	}
	public void setFine(FineModel fine) {
		this.fine = fine;
	}
	public boolean isUserId() {
		return isUserId;
	}
	public void setUserId(boolean isUserId) {
		this.isUserId = isUserId;
	}
	public boolean isBankId() {
		return isBankId;
	}
	public void setBankId(boolean isBankId) {
		this.isBankId = isBankId;
	}
	public boolean isSavings() {
		return isSavings;
	}
	public void setSavings(boolean isSavings) {
		this.isSavings = isSavings;
	}
	public boolean isExtra() {
		return isExtra;
	}
	public void setExtra(boolean isExtra) {
		this.isExtra = isExtra;
	}
	public boolean isInterest() {
		return isInterest;
	}
	public void setInterest(boolean isInterest) {
		this.isInterest = isInterest;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}
}
