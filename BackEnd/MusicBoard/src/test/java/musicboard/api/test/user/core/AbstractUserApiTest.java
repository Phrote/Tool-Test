package musicboard.api.test.user.core;

import org.springframework.http.ResponseEntity;

import MusicBoard.entities.User;
import MusicBoard.entities.User.Role;
import MusicBoard.entities.wrapper.Users;
import musicboard.api.test.core.BaseApiTest;

public abstract class AbstractUserApiTest extends BaseApiTest {
	
	@Override
	protected final String getRequestMapping() {
		return "/user";
	}
	
	@Override
	protected final void clearRepository() {
		getUsers().getBody()
					.getItems()
					.stream()
					.filter(user -> user.getRole() != Role.ROLE_ADMIN)
					.forEach(user -> deleteUser(user.getId().toString()));
	}
	
	protected ResponseEntity<Users> getUsers() {
		return getRestTemplate().getForEntity(createUri(), Users.class);
	}

	protected ResponseEntity<User> getUser(final String mapping) {
		return getRestTemplate().getForEntity(createUri(mapping), User.class);
	}
	
	protected void deleteUser(final String mapping) {
		getRestTemplate().delete(createUri(mapping));
	}

	protected ResponseEntity<User> postUser(final User user) {
		return getRestTemplate().postForEntity(createUri(), user, User.class);
	}
	
	protected void updateUser(final String mapping, final User user) {
		getRestTemplate().put(createUri(mapping), user);
	}
	
	protected User.Builder createUserBuilder() {
		return User.builder()
				.userName("userName")
				.firstName("firstName")
				.lastName("lastName")
				.email("email")
				.passWord("passWord");
	}
}
