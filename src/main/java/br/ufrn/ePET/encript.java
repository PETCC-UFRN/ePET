package br.ufrn.ePET;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class encript {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("password"));
		LocalDate date6 = LocalDate.of(2020, 04, 8);
		System.out.println(LocalDate.now().compareTo(date6));
	}
}
