package com.urk17cs290.orderfood;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	public SMTPAuthenticator() {
		super();
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		String username = "rajasinghsamuelb@karunya.edu.in";
		String password = "ctgpbbqrhthgyayg";
			return new PasswordAuthentication(username, password);
	}
}