package com.crawl.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crawl.api.pojo.AuthenticationResponse;
import com.crawl.api.pojo.LoginRequest;
import com.crawl.api.pojo.RefreshTokenRequest;
import com.crawl.api.pojo.RegisterRequest;
import com.crawl.api.service.AuthService;
import com.crawl.api.service.RefreshTokenService;
import com.crawl.api.service.message.ServiceExecutionResult;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	
	@Autowired
	private AuthService authService;
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<ServiceExecutionResult> signup(@RequestBody RegisterRequest registerRequest) {
        ServiceExecutionResult result = new ServiceExecutionResult();
        result = 		authService.signUp(registerRequest);
        if(result.getErrorCode()!= "") { //   !result.isExecutionSuccessful()
        	System.out.println(result.getMessage());
        	return new ResponseEntity<ServiceExecutionResult>(result,HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/accountVerification/{token}", method = RequestMethod.GET)
	 public ResponseEntity<String> verifyAccount(@PathVariable String token) {
	      authService.verifyAccount(token);
	      return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
	 }
 
	@RequestMapping(value = "/login", method = RequestMethod.POST) 
	    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
	        return authService.login(loginRequest);
	    } 
	@RequestMapping(value = "/refresh/token", method = RequestMethod.POST) 
	    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
	        return authService.refreshToken(refreshTokenRequest);
	    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST) 
	@Transactional
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>("Refresh Token Deleted Successfully!", HttpStatus.OK); //ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
