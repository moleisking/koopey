package com.koopey.api.service;

import com.koopey.api.exception.UserException;
import com.koopey.api.model.entity.User;
import com.koopey.api.repository.UserRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;

import java.math.BigDecimal;
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
public class UserService extends BaseService<User, UUID> implements UserDetailsService {
	
	private final KafkaTemplate<String, String> kafkaTemplate;	
	private final UserRepository userRepository;

	public UserService( KafkaTemplate<String, String> kafkaTemplate,@Lazy UserRepository userRepository) {	
		this.kafkaTemplate = kafkaTemplate;	
		this.userRepository = userRepository;
	}

	@PostConstruct
	private void postConstruct() {
		// LOGGER.info(bcryptEncoder.encode("test"));
		// LOGGER.info(bcryptEncoder.encode("12345"));
	}
	
	protected BaseRepository<User, UUID> getRepository() {
		return userRepository;
	}

	protected KafkaTemplate<String, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByAlias(username);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getAlias(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return List.of(new SimpleGrantedAuthority("ADMIN"));
	}

	/*
	 * public List<User> findAll() { List<User> list = new ArrayList<>();
	 * userRepository.findAll().iterator().forEachRemaining(list::add); return list;
	 * }
	 * 
	 * public void delete(UUID id) { userRepository.deleteById(id); }
	 */

	public User findOne(String alias) {
		Optional<User> optionalUser = userRepository.findByAlias(alias);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UserException("User not found");
		}
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
			user.get().setLanguage(language);
			userRepository.save(user.get());
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateLocation(UUID userId, BigDecimal altitude, BigDecimal latitude, BigDecimal longitude) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			user.get().setAltitude(altitude);
			user.get().setLatitude(latitude);
			user.get().setLongitude(longitude);
			userRepository.save(user.get());
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateMeasure(UUID userId, String measure) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			user.get().setMeasure(measure);
			userRepository.save(user.get());
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

	public Boolean updateTerm(UUID userId, Boolean term) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();
			u.setTerm(term);
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

	/*
	 * @Override
	 * public User save(User user) {
	 * if (!user.getPassword().isEmpty()) {
	 * authenticationService.changePassword(user);
	 * }
	 * return userRepository.save(user);
	 * }
	 */

	/*
	 * @KafkaListener(topics = "user", groupId = "group-id")
	 * public void listen(String message) {
	 * System.out.println("Received Messasge in group - group-id: " + message);
	 * }
	 */

}