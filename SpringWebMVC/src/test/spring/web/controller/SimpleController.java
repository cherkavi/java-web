package test.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 

@Controller
public class SimpleController{

	@RequestMapping(value = "/test.form", // what URL listener
					method = RequestMethod.GET) // what kind of request do we need 
	public String webMethod(@RequestParam("name") String param, // request.getParameter("name"); 
							Model model){
		model.addAttribute("name",param);
		
		return "hello"; // request.getRequestDispatcher("hello.jsp").forward(req,res)
	}
}
