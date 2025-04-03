package com.project.redditclone.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import com.project.redditclone.dto.AuthenticationResponse;
import com.project.redditclone.dto.LoginRequest;
import com.project.redditclone.dto.RegisterRequest;
import com.project.redditclone.exception.RedditErrorException;
import com.project.redditclone.model.NotificationEmail;
import com.project.redditclone.model.User;
import com.project.redditclone.model.VerificationToken;
import com.project.redditclone.repository.UserRepository;
import com.project.redditclone.repository.VerificationTokenRepository;
import com.project.redditclone.security.JwtProvider;

import java.time.Instant;
import java.util.UUID;
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, MailContentBuilder mailContentBuilder, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.verificationTokenRepository = verificationTokenRepository;
		this.mailContentBuilder = mailContentBuilder;
		this.mailService = mailService;
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
	}

	@Value("${ACTIVATION_EMAIL}")
	private String ACTIVATION_EMAIL;
	@Value("${NOTIFICATION_EMAIL_SUBJECT}")
	private String NOTIFICATION_EMAIL_SUBJECT;
	
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUserName(registerRequest.getUsername());
		user.setUserEmail(registerRequest.getEmail());
		user.setUserPassword(encodePassword(registerRequest.getPassword()));
		user.setCreationTime(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		String token = generateVerificationToken(user);
		String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account :" 
                + ACTIVATION_EMAIL + token);
		mailService.sendMail(new NotificationEmail(NOTIFICATION_EMAIL_SUBJECT,message,user.getUserEmail()));
	}
	
	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUserName(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("User name not found: "+user.getUsername()));
	}
	
	private String generateVerificationToken(User user) {
		VerificationToken verificationToken = new VerificationToken();
		String token = UUID.randomUUID().toString();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationToken.setCreationTime(Instant.now());
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public void verifyToken(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new RedditErrorException("Invalid Token"));
		findUserAndEnable(token);
		
	}

	private void findUserAndEnable(String token) {
		User user = verificationTokenRepository.findByToken(token).get().getUser();
		user = userRepository.findByUserName(user.getUserName()).orElseThrow(() -> new RedditErrorException("Invalid username"));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		 String authenticationToken = jwtProvider.generateToken(authenticate);
		 return new AuthenticationResponse(authenticationToken,loginRequest.getUsername());	 
	}

}
