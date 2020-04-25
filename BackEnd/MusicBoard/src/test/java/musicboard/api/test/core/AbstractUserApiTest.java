package musicboard.api.test.core;

import java.net.URI;

import org.springframework.http.ResponseEntity;

import MusicBoard.entities.User;
import MusicBoard.entities.wrapper.Users;

public abstract class AbstractUserApiTest extends BaseApiTest {
	
	@Override
	protected String getRequestMapping() {
		return "/user";
	}
	
	@Override
	protected void clearRepository() {
		getUsers().getBody().getItems().forEach(user -> deleteUser(user.getId()));
	}
	
	protected ResponseEntity<Users> getUsers() {
		final URI uri = createUri(getUrl());
		final ResponseEntity<Users> result = getRestTemplate().getForEntity(uri, Users.class);
		return result;
	}

	protected ResponseEntity<User> getUser(final Integer id) {
		final URI uri = createUri(getUrl() + JOINER + id.toString());
		final ResponseEntity<User> result = getRestTemplate().getForEntity(uri, User.class);
		return result;
	}
	
	protected void deleteUser(final Integer id) {
		final URI uri = createUri(getUrl() + JOINER + id.toString());
		getRestTemplate().delete(uri);
	}

	protected ResponseEntity<User> postUser(final User user) {
		final URI uri = createUri(getUrl());
		final ResponseEntity<User> result = getRestTemplate().postForEntity(uri, user, User.class);
		return result;
	}
	
	protected void updateUser(final Integer id, final User user) {
		final URI uri = createUri(getUrl() + JOINER + id.toString());
		getRestTemplate().put(uri, user);
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
