package com.example.mypkg;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping(value = "/DockerProducts")
	   public String index() {
	      return "Products";
	}

}
