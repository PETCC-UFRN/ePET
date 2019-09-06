package br.ufrn.ePET;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class encript {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("password"));
	}
}
