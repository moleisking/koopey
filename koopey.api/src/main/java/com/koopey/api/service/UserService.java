package com.koopey.api.service;

import com.koopey.api.model.entity.User;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserService extends BaseService<User, UUID> implements UserDetailsService {

	private final AdvertService advertService;
	private final AppointmentService appointmentService;
	private final AssetService assetService;
	private final AuthenticationService authenticationService;
	private final GameService gameService;
	private final LocationService locationService;
	private final MessageService messageService;
	private final ReviewService reviewService;
	private final UserRepository userRepository;

	public UserService(@Lazy AdvertService advertService, @Lazy AppointmentService appointmentService,
			@Lazy AssetService assetService, @Lazy AuthenticationService authenticationService,
			@Lazy GameService gameService, @Lazy LocationService locationService, @Lazy MessageService messageService,
			@Lazy ReviewService reviewService, @Lazy UserRepository userRepository) {
		this.advertService = advertService;
		this.appointmentService = appointmentService;
		this.assetService = assetService;
		this.authenticationService = authenticationService;
		this.gameService = gameService;
		this.locationService = locationService;
		this.messageService = messageService;
		this.reviewService = reviewService;
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
		user.getAppointments().forEach((appointment) -> {
			appointmentService.deleteById(appointment.getId());
		});
		user.getGames().forEach((game) -> {
			gameService.deleteById(game.getId());
		});
		user.getLocations().forEach((location) -> {
			locationService.deleteById(location.getId());
		});
		user.getMessages().forEach((message) -> {
			messageService.deleteById(message.getId());
		});
		user.getPurchases().forEach((asset) -> {
			assetService.deleteById(asset.getId());
		});
		user.getReviews().forEach((review) -> {
			reviewService.deleteById(review.getId());
		});
		user.getSales().forEach((asset) -> {
			assetService.deleteById(asset.getId());
		});
		userRepository.deleteById(user.getId());
	}

	BaseRepository<User, UUID> getRepository() {
		return userRepository;
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

	@Override
	public User update(User user) {
		Optional<User> userExist = super.findById(user.getId());
		if (userExist.isPresent()) {
			BeanUtils.copyProperties(userRepository, user, "password");
			return userRepository.save(user);
		} else {
			return null;
		}
	}

	public Boolean updateAvailable(UUID userId, Boolean gdpr) {
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

	public Boolean updateNotify(UUID userId, Boolean notify) {
		Optional<User> user = super.findById(userId);
		if (user.isPresent()) {
			User u = user.get();
			u.setNotify(notify);
			userRepository.save(u);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User save(User user) {
		if (!user.getPassword().isEmpty()) {
			authenticationService.changePassword(user);
		}
		return userRepository.save(user);
	}
}