package com.koopey.api.service;

import com.koopey.api.model.entity.User;
import com.koopey.api.repository.UserRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.service.base.AuditService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserService extends AuditService<User, UUID> implements UserDetailsService {

	private final AdvertService advertService;
	private final AssetService assetService;
	private final AuthenticationService authenticationService;
	private final GameService gameService;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final LocationService locationService;
	private final MessageService messageService;
	private final TransactionService transactionService;
	private final UserRepository userRepository;

	public UserService(@Lazy AdvertService advertService,
			@Lazy AssetService assetService, @Lazy AuthenticationService authenticationService,
			@Lazy GameService gameService, KafkaTemplate<String, String> kafkaTemplate,
			@Lazy LocationService locationService, @Lazy MessageService messageService,
			@Lazy TransactionService transactionService,
			@Lazy UserRepository userRepository) {
		this.advertService = advertService;
		this.assetService = assetService;
		this.authenticationService = authenticationService;
		this.gameService = gameService;
		this.kafkaTemplate = kafkaTemplate;
		this.locationService = locationService;
		this.messageService = messageService;
		this.transactionService = transactionService;
		this.userRepository = userRepository;
	}

	@PostConstruct
	private void postConstruct() {
		// LOGGER.info(bcryptEncoder.encode("test"));
		// LOGGER.info(bcryptEncoder.encode("12345"));
	}

	@Override
	public void delete(User user) {
		user.getAdverts().forEach((advert) -> {
			advertService.deleteById(advert.getId());
		});
		user.getGames().forEach((game) -> {
			gameService.deleteById(game.getId());
		});
		user.getDeliveries().forEach((location) -> {
			locationService.deleteById(location.getId());
		});
		user.getCollections().forEach((location) -> {
			locationService.deleteById(location.getId());
		});
		user.getPurchases().forEach((asset) -> {
			assetService.deleteById(asset.getId());
		});
		user.getReceives().forEach((message) -> {
			messageService.deleteById(message.getId());
		});
		user.getSales().forEach((asset) -> {
			assetService.deleteById(asset.getId());
		});
		user.getSends().forEach((message) -> {
			messageService.deleteById(message.getId());
		});
		userRepository.deleteById(user.getId());
	}

	protected AuditRepository<User, UUID> getRepository() {
		return userRepository;
	}

	protected KafkaTemplate<String, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByAlias(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getAlias(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
	}

	/*
	 * public List<User> findAll() { List<User> list = new ArrayList<>();
	 * userRepository.findAll().iterator().forEachRemaining(list::add); return list;
	 * }
	 * 
	 * public void delete(UUID id) { userRepository.deleteById(id); }
	 */

	public User findOne(String alias) {
		return userRepository.findByAlias(alias);
	}

	public List<User> findListeners(UUID userId) {
		return userRepository.findListeners(userId);
	}

	// @Override
	/*
	 * public User update(User user) {
	 * Optional<User> userExist = super.findById(user.getId());
	 * if (userExist.isPresent()) {
	 * BeanUtils.copyProperties(userRepository, user, "password");
	 * return userRepository.save(user);
	 * } else {
	 * return null;
	 * }
	 * }
	 */

	public Boolean updateCurrency(UUID userId, String currency) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();
			u.setCurrency(currency);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateGdpr(UUID userId, Boolean gdpr) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();
			u.setGdpr(gdpr);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateLanguage(UUID userId, String language) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();
			u.setLanguage(language);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateMeasure(UUID userId, String measure) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();
			u.setCurrency(measure);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateTrack(UUID userId, Boolean track) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();
			u.setTrack(track);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateNotifyByDevice(UUID userId, Boolean notify) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();			
			u.setNotifyByDevice(notify);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

		public Boolean updateNotifyByEmail(UUID userId, Boolean notify) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();			
			u.setNotifyByEmail(notify);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

	/*@Override
	public User save(User user) {
		if (!user.getPassword().isEmpty()) {
			authenticationService.changePassword(user);
		}
		return userRepository.save(user);
	}*/

	/*
	 * @KafkaListener(topics = "user", groupId = "group-id")
	 * public void listen(String message) {
	 * System.out.println("Received Messasge in group - group-id: " + message);
	 * }
	 */

}