package controller;

import jakarta.servlet.http.HttpSession;

public class SessionManagement {
	private HttpSession session = null;
	public SessionManagement(HttpSession session) {
		this.session = session;
	}
	public void setAttributes(String redirect, long bankId) {
		session.setAttribute(redirect, bankId);
	}
	public void setAttributes(String isLoggedString, boolean isLogged) {
		session.setAttribute(isLoggedString, isLogged);
	}
	public String getAttribute(String redirect) {
		return (String) session.getAttribute(redirect);
	}
	public void setAttributes(String redirect, String url) {
		session.setAttribute(redirect, url);
	}
}
