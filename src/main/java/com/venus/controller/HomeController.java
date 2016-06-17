package com.venus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hrishikeshjoshi on 6/16/16.
 */
@Controller
@RequestMapping("/home")
class HomeController {

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

}
