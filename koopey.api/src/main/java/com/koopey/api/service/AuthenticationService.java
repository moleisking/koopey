package com.koopey.api.service;

import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.entity.User;
// import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.AuthenticationException;
import com.koopey.api.model.authentication.AuthenticationUser;
import com.koopey.api.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import com.koopey.api.service.impl.IAuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService implements IAuthenticationService {

    private final AdvertService advertService;
	private final AssetService assetService;    
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final CustomProperties customProperties;
    private final GameService gameService;
    private final JwtService jwtTokenUtility;
    private final LocationService locationService;
	private final MessageService messageService;
    private final SmtpService smtpService;
    private final TransactionService transactionService;
    private final UserRepository userRepository;

    public AuthenticationService(@Lazy AdvertService advertService,
			@Lazy AssetService assetService, AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bcryptEncoder, @Lazy CustomProperties customProperties, 
            @Lazy GameService gameService, @Lazy JwtService jwtTokenUtility,
            @Lazy LocationService locationService, @Lazy MessageService messageService,
			@Lazy TransactionService transactionService, @Lazy SmtpService smtpService,			
            @Lazy UserRepository userRepository) {
		this.advertService = advertService;
		this.assetService = assetService;
		this.authenticationManager = authenticationManager;
        this.bcryptEncoder = bcryptEncoder;
        this.customProperties = customProperties;
        this.jwtTokenUtility = jwtTokenUtility;
		this.gameService = gameService;		
		this.locationService = locationService;
		this.messageService = messageService;
        this.smtpService = smtpService;
		this.transactionService = transactionService;
		this.userRepository = userRepository;
	}
  
	public void delete(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
           /* user.get().getAdverts().forEach((advert) -> {
			advertService.deleteById(advert.getId());
		    });*/

		    user.get().getDeliveries().forEach((location) -> {
			locationService.deleteById(location.getId());
		    });
		    user.get().getCollections().forEach((location) -> {
			locationService.deleteById(location.getId());
		    });
		    user.get().getPurchases().forEach((asset) -> {
			assetService.deleteById(asset.getId());
		    });
		    user.get().getReceives().forEach((message) -> {
			messageService.deleteById(message.getId());
		    });
		    user.get().getSales().forEach((asset) -> {
			assetService.deleteById(asset.getId());
		    });
		    user.get().getSends().forEach((message) -> {
			messageService.deleteById(message.getId());
		    });
		    userRepository.deleteById(user.get().getId());
        }
	}


    public AuthenticationUser login(AuthenticationDto loginUser) throws AuthenticationException {
        if ((loginUser.getAlias() == null || loginUser.getAlias().isEmpty()) &&
                (loginUser.getEmail() == null || loginUser.getEmail().isEmpty())) {
            throw new AuthenticationException("Empty alias and email");
        } else if (loginUser.getPassword() == null || loginUser.getPassword().isEmpty()) {
            throw new AuthenticationException("Empty password");
        } else if (loginUser.getAlias() == null || loginUser.getAlias().isEmpty()) {
            loginUser.setAlias(userRepository.findAliasByEmail(loginUser.getEmail()));
        } else if (loginUser.getEmail() == null || loginUser.getEmail().isEmpty()) {
            loginUser.setEmail(userRepository.findEmailByAlias(loginUser.getAlias()));
        }

        if (this.checkPasswordMatch(loginUser)) {
            return this.getAuthenticationUser(loginUser);
        } else {
            throw new AuthenticationException("Password incorrect");
        }
    }

    public Boolean register(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getMobile() == null
                || user.getMobile().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getAlias() == null || user.getAlias().isEmpty()) {
            return false;
        } else if (userRepository.existsByEmailOrIdOrMobile(user.getEmail(), user.getId(), user.getMobile())) {
            return false;
        } else if (user.getAlias().isEmpty() || userRepository.existsByAlias(user.getAlias())) {
            return false;
        } else {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
            log.info("User register {}", user.getAlias());
            if (customProperties.getVerificationEnable()) {
                smtpService.sendSimpleMessage(user.getEmail(), customProperties.getEmailAddress(), "subject",
                        customProperties.getVerificationUrl());
            }
            return true;
        }
    }

    public Boolean changeAlias(UUID userId, String oldAlias, String newAlias, String password) {
        Optional<User> optionalUser = userRepository.findByAliasAndId(oldAlias,userId);
        if (optionalUser.isEmpty()){
            log.error("Alias change for {} to {} failed.", oldAlias, newAlias);
            return false;
        } else if (bcryptEncoder.matches(optionalUser.get().getPassword(), password)) {
            User user = optionalUser.get();
            user.setEmail(newAlias);
            userRepository.save(user);
            sendVerifyLink(newAlias);
            log.info("Alias change for {}.", user.getAlias());
            return true;
        } else {
            log.error("Alias change for {} to {} failed, because of password.", oldAlias, newAlias);
            return false;
        }
    }

    public Boolean changeEmail(UUID userId,String oldEmail, String newEmail, String password) {
        Optional<User> optionalUser = userRepository.findByEmailAndId(oldEmail,userId);
        if (optionalUser.isEmpty()){
            log.error("Email change for {} to {} failed.", oldEmail, newEmail);
            return false;
        } else if (bcryptEncoder.matches(optionalUser.get().getPassword(), password)) {
            User user = optionalUser.get();
            user.setEmail(newEmail);
            userRepository.save(user);
            sendVerifyLink(newEmail);
            log.info("Email change for {}.", user.getAlias());
            return true;
        } else {
            log.error("Email change for {} to {} failed, because of password.", oldEmail, newEmail);
            return false;
        }
    }

    public Boolean changePassword(UUID userId, String oldPassword, String newPassword) {
        User user = userRepository.getById(userId);
        if (bcryptEncoder.matches(user.getPassword(), oldPassword)) {
            user.setPassword(bcryptEncoder.encode(newPassword));
            userRepository.save(user);
            log.info("Password change for {}.", user.getAlias());
            return true;
        } else {
            log.error("Password change for {} failed.", user.getAlias());
            return false;
        }
    }

    public Boolean changeVerify(UUID guid) {
        Optional<User> optionalUser = userRepository.findByGuid(guid);
        if (optionalUser.isEmpty()) {
            log.error("Verify change for {} failed.", guid);
            return false;
        } else {
            User user = optionalUser.get();
            user.setVerify(true);
            userRepository.save(user);
            log.info("Verify change for {}.", user.getAlias());
            return true;
        }
    }

    private Boolean checkPasswordMatch(AuthenticationDto claimUser) {
        Optional<User> optionalUser = userRepository.findByAlias(claimUser.getAlias());
        if (optionalUser.isPresent() &&
                bcryptEncoder.matches(claimUser.getPassword(),optionalUser.get().getPassword())){
            log.info("Password match for {}.", claimUser.getAlias());
            return true;
        } else {
            log.error("No password match for {}.", claimUser.getAlias());
            return false;
        }
    }

    public Boolean checkUserExistence(User user) {
        return userRepository.existsByEmailOrIdOrMobile(user.getEmail(), user.getId(), user.getMobile());
    }

    public Boolean checkAliasExistence(User user) {
        return user.getAlias().isEmpty() || userRepository.existsByAlias(user.getAlias());
    }

    public Boolean sendForgotPasswordLink(String email) {
        UUID tempGuid = UUID.randomUUID();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setGuid(tempGuid);
            this.update(user);
            user.setPassword(bcryptEncoder.encode(tempGuid.toString()));
            smtpService.sendSimpleMessage(email, customProperties.getEmailAddress(), "Change forgotten password",
                    tempGuid.toString());
            return true;
        } else {
            return false;
        }
    }

    public Boolean sendVerifyLink(String email) {
        UUID tempGuid = UUID.randomUUID();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setGuid(tempGuid);
            smtpService.sendSimpleMessage(email, customProperties.getEmailAddress(), "Verify email address",
                    customProperties.getVerificationUrl() + "/" + tempGuid);
            return true;
        } else {
            return false;
        }
    }

    private AuthenticationUser getAuthenticationUser(AuthenticationDto loginUser) {
        final Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginUser.getAlias(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userRepository.findByAliasOrEmail(loginUser.getAlias(), loginUser.getEmail());
        final String token = jwtTokenUtility.generateToken(user);
        return new AuthenticationUser(token, user);
    }

    public AuthenticationUser getAuthenticationUser( UUID userId ) {
        final User user = userRepository.getById(userId);   
        final String token = jwtTokenUtility.generateToken(user);     
        return new AuthenticationUser(token, user);
    }

    public Boolean update(User user) {
        Optional<User> userExist = userRepository.findById(user.getId());
        if (userExist.isPresent()) {
            BeanUtils.copyProperties(userRepository, user, "alias", "gprs", "password", "username", "term");
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

}
