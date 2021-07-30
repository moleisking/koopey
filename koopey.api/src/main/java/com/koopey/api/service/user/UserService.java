package com.koopey.api.service.user;

import com.koopey.api.model.entity.User;
import com.koopey.api.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service(value = "userService")
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	private static Logger LOGGER = Logger.getLogger(UserService.class.getName());

	@PostConstruct
	private void postConstruct() {

		// LOGGER.info(bcryptEncoder.encode("test"));
		// LOGGER.info(bcryptEncoder.encode("12345"));
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(UUID id) {
		userRepository.deleteById(id);
	}

	public User findOne(String alias) {
		return userRepository.findByUsername(alias);
	}

	public User findById(UUID id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	public void update(User user) {
		User userExist = findById(user.getId());
		if (userExist != null) {
			BeanUtils.copyProperties(userRepository, user, "password");
			userRepository.save(user);
		}
	}

	public void save(User user) {
	/*	User newUser = User.builder().birthday(user.getBirthday()).email(user.getEmail()).mobile(user.getMobile())
				.name(user.getName()).username(user.getUsername()).build();
				newUser.setPassword(bcryptEncoder.encode(user.getPassword()));*/
		//newUser.setUsername(user.getUsername());
		//newUser.setName(user.getName());
	//	newUser.setEmail(user.getEmail());
	//	newUser.setMobile(user.getMobile());
		//newUser.setPassword();
		//newUser.setBirthday(user.getBirthday());
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}