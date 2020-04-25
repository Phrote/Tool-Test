package musicboard.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import MusicBoard.entities.User;
import MusicBoard.entities.wrapper.Users;
import musicboard.api.test.core.AbstractUserApiTest;

public class UserApiTest extends AbstractUserApiTest {

    private static final String USER_NAME = "pityuka12";

    @Test
    public void testGetAll() {
        postUser(createUserBuilder().build());
        postUser(createUserBuilder().build());

        final ResponseEntity<Users> users = getUsers();
        assertEquals(users.getStatusCode(), HttpStatus.OK);
        assertEquals(users.getBody().getTotal(), 2);
    }

    @Test
    public void testGetUserById() {
        final ResponseEntity<User> user = postUser(createUserBuilder().userName(USER_NAME).build());

        assertEquals(user.getStatusCode(), HttpStatus.OK);

        final ResponseEntity<User> result = getUser(user.getBody().getId());

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getUserName(), USER_NAME);
    }

    @Test
    public void testPostUser() {
        final ResponseEntity<User> user = postUser(createUserBuilder().userName(USER_NAME).build());

        assertEquals(user.getStatusCode(), HttpStatus.OK);
        assertEquals(user.getBody().getUserName(), USER_NAME);
    }

    @Test
    public void testUpdateUser() {
        final String updateName = "ferenc1243";
        final ResponseEntity<User> post = postUser(createUserBuilder().userName(USER_NAME).build());
        updateUser(post.getBody().getId(), createUserBuilder().userName(updateName).build());

        final ResponseEntity<User> user = getUser(post.getBody().getId());

        assertEquals(user.getStatusCode(), HttpStatus.OK);
        assertEquals(user.getBody().getUserName(), updateName);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testDeleteInstrument() {
        final ResponseEntity<User> user = postUser(createUserBuilder().build());

        assertEquals(user.getStatusCode(), HttpStatus.OK);

        deleteUser(user.getBody().getId());
        getUser(user.getBody().getId());
    }

}
