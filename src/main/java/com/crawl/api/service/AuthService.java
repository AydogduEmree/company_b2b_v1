package com.crawl.api.service;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.crawl.api.dao.impl.AccountsDAOImpl;
import com.crawl.api.dao.util.Constants;
import com.crawl.api.entity.NotificationEmail;
import com.crawl.api.entity.User;
import com.crawl.api.entity.VerificationToken;
import com.crawl.api.exceptions.SpringException;
import com.crawl.api.pojo.AuthenticationResponse;
import com.crawl.api.pojo.LoginRequest;
import com.crawl.api.pojo.RefreshTokenRequest;
import com.crawl.api.pojo.RegisterRequest;
import com.crawl.api.repository.UserRepository;
import com.crawl.api.repository.VerificationTokenRepository;
import com.crawl.api.service.message.ServiceErrorCode;
import com.crawl.api.service.message.ServiceExecutionResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@SuppressWarnings("all")
@Service
@Data
@Builder
@AllArgsConstructor
public class AuthService {
	@Autowired
    private  UserRepository userRepository ; //Spring JPA
	@Autowired
	private AccountsDAOImpl accountsDAOImpl; // Hibernate
	@Autowired
	private  PasswordEncoder passwordEncoder ;
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;//SignUp token
    @Autowired
    private MailService mailService;
    @Autowired
    private MailContentBuilder mailContentBuilder;
    @Autowired
    private AuthenticationManager authenticationManager;
   @Autowired
   private JwtProvider jwtProvider;
   @Autowired
   private RefreshTokenService refreshTokenService;
   
   public ServiceExecutionResult signUp(RegisterRequest registerRequest) {
   	User user = new User();
   	/*Spring JPA
   	user.setUsername(registerRequest.getUsername());
   	user.setPassword(encodePassword(registerRequest.getPassword()));
   	user.setEmail(registerRequest.getEmail());
   	user.setCreatedBy("SYSTEM");
       user.setCreatedDate(Calendar.getInstance().getTime());
       user.setEnabled(false);
       
   	userRepository.save(user);
   	*/
   	ServiceExecutionResult result = new ServiceExecutionResult();
   	result.setExecutionSuccessful(true);
   	result.setErrorCode("");
   	result.setMessage("");
   	result.setSuccessCode("");
   	Map execute = accountsDAOImpl.addNewUser(registerRequest.getUsername(),
   							  encodePassword(registerRequest.getPassword()),
   							  registerRequest.getEmail(),
   							  "INSERT",
   							  new BigInteger("1")
   							  );
   if (execute.get("errorMessage") != null) {
	//   System.out.println("HATANIN SERVİSTE GOSTERİLMESİ:"); 	System.out.println(execute.get("errorMessage"));//throw new SpringException(execute.get("errorMessage").toString());
   	result.setExecutionSuccessful(false);
	   	if(execute.get("errorMessage").toString().contains("Email")) {
	   		result.setErrorCode(ServiceErrorCode.EMAIL_EXISTS);	
	   		result.setMessage("Email is already exists and activated!");
	   	}else {
	   		result.setErrorCode(ServiceErrorCode.UNKNOWN);	
	   		result.setMessage(ServiceErrorCode.UNKNOWN);
	   	}
	}else if (execute != null && execute.get("P_USER_ID") != null) {
	   
	   user.setUsername(registerRequest.getUsername());
       user.setPassword(encodePassword(registerRequest.getPassword()));
       user.setEmail(registerRequest.getEmail());
       user.setUserId(new BigInteger(""+ execute.get("P_USER_ID").toString()) );
       
       String token = generateVerificationToken(user);
        
    	/*
    	String message = mailContentBuilder.build("Thank you for signing up to our journey, " +
		           "please click on the below url to activate your account : <br/>" +
		           Constants.ACTIVATION_EMAIL + "/"+token);
    	mailService.sendMail(	
    						new NotificationEmail(
    							"Please Activate your Account",
    							user.getEmail(),
    							message
    						)
    					);*/
	}
	
   return result;
   	
   }
   
    
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        
        verificationTokenRepository.save(verificationToken);
        return token;
    }

	
	public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringException("Invalid Token")));
    }
	
	private void fetchUserAndEnable(VerificationToken verificationToken) {
	    String username = verificationToken.getUser().getUsername();
	    /* spring jpa
	    //User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
	    //user.setEnabled(true);
	    //userRepository.save(user);
	  
	     accountsDAOImpl.addNewUser(user.getUsername(),
				  encodePassword(user.getPassword()),
				  user.getEmail(),
				  "UPDATE",
				  user.getUserId()
				  );
	     */
	    User user= accountsDAOImpl.getUserWithId(verificationToken.getUser().getUserId());
	    accountsDAOImpl.addNewUser(null,
				  null,
				  null,
				  "UPDATE",
				  user.getUserId()
				  );
	}

	 public AuthenticationResponse login(LoginRequest loginRequest) {
		    
		    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
	                loginRequest.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authenticate);
	        String token = jwtProvider.generateToken(authenticate);
	        System.out.println(token);
	        System.out.println("------ -!!!!!!!!!!!------!!!!!!!----------------------------");
	        System.out.println("Current User : "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	        System.out.println(isLoggedIn());
	        return AuthenticationResponse.builder()
	                .authenticationToken(token)
	                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
	                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	                .username(loginRequest.getUsername())
	                .build();
	    }
	
	 public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
	        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
	        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
	        return AuthenticationResponse.builder()
	                .authenticationToken(token)
	                .refreshToken(refreshTokenRequest.getRefreshToken())
	                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	                .username(refreshTokenRequest.getUsername())
	                .build();
	    }

	    public boolean isLoggedIn() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	    }
	    
	    public User getCurrentUser() {
	        Jwt principal = (Jwt) SecurityContextHolder.
	                getContext().getAuthentication().getPrincipal();
	        return userRepository.findByUsername(principal.getSubject())
	                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
	    }
	    public String getCredential() {
	    	String username;
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if (principal instanceof UserDetails) {
	    	  username = ((UserDetails)principal).getUsername() +"-1";
	    	} else {
	    	  username = principal.toString()+"-2";
	    	}
	    	System.out.println("Current User : "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	    	System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
	    	return username;
	              
	    }
}
