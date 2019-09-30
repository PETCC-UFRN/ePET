package br.ufrn.ePET;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class encript {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456789"));
		System.out.println(LocalDate.now());
	}
}
