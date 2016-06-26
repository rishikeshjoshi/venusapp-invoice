package com.venus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hrishikeshjoshi on 6/16/16.
 */
@Controller
class HomeController {

	@RequestMapping("/invoices")
	public String index() {
		return "home";
	}

}
