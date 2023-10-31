package com.koopey.api.service;

import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.entity.User;
import com.koopey.api.configuration.jwt.JwtTokenUtility;
import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.exception.AuthenticationException;
import com.koopey.api.model.authentication.AuthenticationUser;
import com.koopey.api.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

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
public class AuthenticationService {

    private final AdvertService advertService;
	private final AssetService assetService;    
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final CustomProperties customProperties;
    private final GameService gameService;
    private final JwtTokenUtility jwtTokenUtility;
    private final LocationService locationService;
	private final MessageService messageService;
    private final SmtpService smtpService;
    private final TransactionService transactionService;
    private final UserRepository userRepository;

    public AuthenticationService(@Lazy AdvertService advertService,
			@Lazy AssetService assetService, AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bcryptEncoder, @Lazy CustomProperties customProperties, 
            @Lazy GameService gameService, @Lazy JwtTokenUtility jwtTokenUtility,  
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
            user.get().getAdverts().forEach((advert) -> {
			advertService.deleteById(advert.getId());
		    });
		    user.get().getGames().forEach((game) -> {
			gameService.deleteById(game.getId());
		    });
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

        if ((loginUser.getAlias() == null) && (loginUser.getEmail() == null)) {
            throw new AuthenticationException("Empty alias and email");
        } else if ((loginUser.getAlias() == null || loginUser.getAlias().isEmpty())
                && !(loginUser.getEmail() == null && loginUser.getEmail().isEmpty())) {
            loginUser.setAlias(userRepository.findAliasByEmail(loginUser.getEmail()));
            return this.getAuthenticationUser(loginUser);
        } else {
            return this.getAuthenticationUser(loginUser);
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

    public Boolean changePassword(UUID userId, String oldPassword, String newPassword) {

        User user = userRepository.getById(userId);
        if (bcryptEncoder.encode(oldPassword).equals(user.getPassword())) {
            user.setPassword(bcryptEncoder.encode(newPassword));
            if (userRepository.save(user) == null) {
                log.error("Password change fail for {}", user.getAlias());
                return true;
            } else {
                log.info("Password change for {}", user.getAlias());
                return false;
            }
        }

        return false;
    }

    public Boolean forgotPassword(String email) {
        UUID tempPassword = UUID.randomUUID();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(bcryptEncoder.encode(tempPassword.toString()));
            smtpService.sendSimpleMessage(email, customProperties.getEmailAddress(), "new password",
                    tempPassword.toString());
            return true;
        } else
            return false;

    }

    public Boolean checkIfUserExists(User user) {

        if (userRepository.existsByEmailOrIdOrMobile(user.getEmail(), user.getId(), user.getMobile())) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean checkIfAliasExists(User user) {

        if (user.getAlias().isEmpty() || userRepository.existsByAlias(user.getAlias())) {
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
