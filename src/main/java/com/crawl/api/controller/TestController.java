package com.crawl.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crawl.api.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/t1", method = RequestMethod.GET)
	 public ResponseEntity<String> verifyPoint() {  
	      return new ResponseEntity<>("Test  Successfully from secured point", HttpStatus.OK);
	 }
	
	@RequestMapping(value = "/t2", method = RequestMethod.GET)
	 public ResponseEntity<String> verifyPoint2() { 
	      return new ResponseEntity<>("Invalid Test from UNsecured point" + authService.getCredential(), HttpStatus.OK);
	 }
	
	@RequestMapping(value = "/t3", method = RequestMethod.GET)
	 public ResponseEntity<String> verifyPoint3() { 
		String userEmail;
		String responseMessage="NOT OK";
		Integer lineNo=1;
		try {
			lineNo=2;
			if(authService.isLoggedIn()  == true)
				responseMessage="LOGGED IN - OK";
				//userEmail=authService.getCurrentUser().getEmail();
			lineNo=3;
		} catch (Exception e) {
			lineNo=4;
			System.out.println(e.getMessage()+ " is my message! - lineNo="+lineNo);
			
		}
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	 }

	
}
