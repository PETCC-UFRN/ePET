package br.ufrn.ePET.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringController {
	@RequestMapping("/docs")
	public String swaggerpath() {
		return "redirect:/swagger-ui.html";
	}
}
