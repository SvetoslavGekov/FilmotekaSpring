package com.filmoteka.s9.controller;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.filmoteka.s9.model.Papagal;



@Controller
@RequestMapping(value = "/ittalents")
public class MishaController {
	
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/misha", method = RequestMethod.GET )
	public String hiMisha(Model m, HttpSession s, HttpServletRequest request) {
		int age = 10;
		Papagal pesho = new Papagal("Petyr", 32);
		m.addAttribute("papagalche",pesho);
		m.addAttribute("godinki", age);
		return "misha";
	}
	
	@RequestMapping(value = "/papagali", method = RequestMethod.GET )
	public String getPapagali(Model m) {
		ArrayList<Papagal> papagali = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			papagali.add(new Papagal("Papagalche " + i, i*3));
		}
		m.addAttribute("papagali", papagali);
		return "papagali";
	}
	
	@RequestMapping(value = "/misha/papagal/{papagal_id}/{pero_id}", method = RequestMethod.GET )
	public String daiPapagal(
			Model m, 
			@PathVariable("papagal_id") Integer p_id, 
			@PathVariable("pero_id") Integer pero) {
		
		m.addAttribute("papagal_nomer", p_id);
		m.addAttribute("pero_nomer", pero);
		return "misha";
	}
}
