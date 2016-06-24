package com.venus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hrishikeshjoshi on 6/16/16.
 */
@RestController
@RequestMapping("/home")
class HomeController {

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

}
