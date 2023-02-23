package model;

import java.util.Date;

public class BarrowModel {
	private long userId,bankId;
	private int barrowed_amount;
	private short barrow_type;
	private Float barrow_type_value;
	public Float getBarrow_type_value() {
		return barrow_type_value;
	}
	public void setBarrow_type_value(Float barrow_type_value) {
		this.barrow_type_value = barrow_type_value;
	}
	private Date updated_at;
	public BarrowModel(long userId,long bankId,int barrowed_amount, short barrow_type) {
		super();
		this.bankId = bankId;
		this.userId = userId;
		this.barrowed_amount = barrowed_amount;
		this.barrow_type = barrow_type;
	}
	public BarrowModel() {
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
	public int getBarrowed_amount() {
		return barrowed_amount;
	}
	public void setBarrowed_amount(int barrowed_amount) {
		this.barrowed_amount = barrowed_amount;
	}
	public int getBarrow_type() {
		return barrow_type;
	}
	public void setBarrow_type(short barrow_type) {
		this.barrow_type = barrow_type;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}
