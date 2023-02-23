package model;

public class PermissionsModel {
	boolean savings_view;
	boolean savings_check;
	boolean savings_update;
	boolean loan_view;
	boolean loan_update;
	boolean chat_view;
	boolean chat_everyone;
	boolean chat_unique;
	boolean users_view;
	boolean users_create;
	boolean users_details;
	boolean users_update,users_delete,users_edit,profile_view,profile_edit,profile_update,newgroup_view,rules_edit,rules_update,history_view,savings_history_view,loan_history_view;
	public boolean isUsers_edit() {
		return users_edit;
	}
	public void setUsers_edit(boolean users_edit) {
		this.users_edit = users_edit;
	}
	boolean savings_list_view,loan_list_view;
	public PermissionsModel(boolean savings_view, boolean savings_check, boolean savings_update, boolean savings_list_view, boolean loan_view,
			boolean loan_update, boolean loan_list_view, boolean chat_view, boolean chat_everyone, boolean chat_unique, boolean users_view,
			boolean users_create, boolean users_details, boolean users_edit, boolean users_update, boolean users_delete, boolean profile_view,
			boolean profile_edit, boolean profile_update, boolean newgroup_view, boolean rules_edit,
			boolean rules_update, boolean history_view, boolean savings_history_view, boolean loan_history_view) {
		super();
		this.savings_view = savings_view;
		this.savings_check = savings_check;
		this.savings_update = savings_update;
		this.savings_list_view = savings_list_view;
		this.loan_view = loan_view;
		this.loan_update = loan_update;
		this.loan_list_view = loan_list_view;
		this.chat_view = chat_view;
		this.chat_everyone = chat_everyone;
		this.chat_unique = chat_unique;
		this.users_view = users_view;
		this.users_create = users_create;
		this.users_details = users_details;
		this.users_update = users_update;
		this.users_edit = users_edit;
		this.users_delete = users_delete;
		this.profile_view = profile_view;
		this.profile_edit = profile_edit;
		this.profile_update = profile_update;
		this.newgroup_view = newgroup_view;
		this.rules_edit = rules_edit;
		this.rules_update = rules_update;
		this.history_view = history_view;
		this.savings_history_view = savings_history_view;
		this.loan_history_view = loan_history_view;
	}
	public PermissionsModel() {
		// TODO Auto-generated constructor stub
	}
	public boolean isSavings_list_view() {
		return savings_list_view;
	}
	public void setSavings_list_view(boolean savings_list_view) {
		this.savings_list_view = savings_list_view;
	}
	public boolean isLoan_list_view() {
		return loan_list_view;
	}
	public void setLoan_list_view(boolean loan_list_view) {
		this.loan_list_view = loan_list_view;
	}
	public boolean isSavings_view() {
		return savings_view;
	}
	public void setSavings_view(boolean savings_view) {
		this.savings_view = savings_view;
	}
	public boolean isSavings_check() {
		return savings_check;
	}
	public void setSavings_check(boolean savings_check) {
		this.savings_check = savings_check;
	}
	public boolean isSavings_update() {
		return savings_update;
	}
	public void setSavings_update(boolean savings_update) {
		this.savings_update = savings_update;
	}
	public boolean isLoan_view() {
		return loan_view;
	}
	public void setLoan_view(boolean loan_view) {
		this.loan_view = loan_view;
	}
	public boolean isLoan_update() {
		return loan_update;
	}
	public void setLoan_update(boolean loan_update) {
		this.loan_update = loan_update;
	}
	public boolean isChat_view() {
		return chat_view;
	}
	public void setChat_view(boolean chat_view) {
		this.chat_view = chat_view;
	}
	public boolean isChat_everyone() {
		return chat_everyone;
	}
	public void setChat_everyone(boolean chat_everyone) {
		this.chat_everyone = chat_everyone;
	}
	public boolean isChat_unique() {
		return chat_unique;
	}
	public void setChat_unique(boolean chat_unique) {
		this.chat_unique = chat_unique;
	}
	public boolean isUsers_view() {
		return users_view;
	}
	public void setUsers_view(boolean users_view) {
		this.users_view = users_view;
	}
	public boolean isUsers_create() {
		return users_create;
	}
	public void setUsers_create(boolean users_create) {
		this.users_create = users_create;
	}
	public boolean isUsers_details() {
		return users_details;
	}
	public void setUsers_details(boolean users_details) {
		this.users_details = users_details;
	}
	public boolean isUsers_update() {
		return users_update;
	}
	public void setUsers_update(boolean users_update) {
		this.users_update = users_update;
	}
	public boolean isUsers_delete() {
		return users_delete;
	}
	public void setUsers_delete(boolean users_delete) {
		this.users_delete = users_delete;
	}
	public boolean isProfile_view() {
		return profile_view;
	}
	public void setProfile_view(boolean profile_view) {
		this.profile_view = profile_view;
	}
	public boolean isProfile_edit() {
		return profile_edit;
	}
	public void setProfile_edit(boolean profile_edit) {
		this.profile_edit = profile_edit;
	}
	public boolean isProfile_update() {
		return profile_update;
	}
	public void setProfile_update(boolean profile_update) {
		this.profile_update = profile_update;
	}
	public boolean isNewgroup_view() {
		return newgroup_view;
	}
	public void setNewgroup_view(boolean newgroup_view) {
		this.newgroup_view = newgroup_view;
	}
	public boolean isRules_edit() {
		return rules_edit;
	}
	public void setRules_edit(boolean rules_edit) {
		this.rules_edit = rules_edit;
	}
	public boolean isRules_update() {
		return rules_update;
	}
	public void setRules_update(boolean rules_update) {
		this.rules_update = rules_update;
	}
	public boolean isHistory_view() {
		return history_view;
	}
	public void setHistory_view(boolean history_view) {
		this.history_view = history_view;
	}
	public boolean isSavings_history_view() {
		return savings_history_view;
	}
	public void setSavings_history_view(boolean savings_history_view) {
		this.savings_history_view = savings_history_view;
	}
	public boolean isLoan_history_view() {
		return loan_history_view;
	}
	public void setLoan_history_view(boolean loan_history_view) {
		this.loan_history_view = loan_history_view;
	}
	public PermissionsModel getAdminPermissions() {
		this.savings_view = true;
	    this.savings_check = true;
	    this.savings_update = true;
	    this.savings_list_view = true;
	    this.loan_view = true;
	    this.loan_update = true;
	    this.loan_list_view = true;
	    this.chat_view = true;
	    this.chat_everyone = true;
	    this.chat_unique = true;
	    this.users_view = true;
	    this.users_create = true;
	    this.users_details = true;
	    this.users_update = true;
	    this.users_delete = true;
	    this.profile_view = true;
	    this.profile_edit = true;
	    this.profile_update = true;
	    this.newgroup_view = true;
	    this.rules_edit = true;
	    this.rules_update = true;
	    this.history_view = true;
	    this.savings_history_view = true;
	    this.loan_history_view = true;
	    return new PermissionsModel();
	}
	
}
