package musicboard.api.test.user.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import MusicBoard.entities.User;
import MusicBoard.entities.wrapper.Users;
import musicboard.api.test.user.core.AbstractUserApiTest;

public class UserApiIdMappingTests extends AbstractUserApiTest {

	private static final String USER_NAME = "pityuka12";

    @Test
    public void testGetAll() {
        postUser(createUserBuilder().build());
        postUser(createUserBuilder().build());

        final ResponseEntity<Users> users = getUsers();
        assertEquals(users.getStatusCode(), HttpStatus.OK);
        assertEquals(users.getBody().getTotal(), 3);
    }

    @Test
    public void testPostUser() {
    	final ResponseEntity<User> user = postUser(createUserBuilder().userName(USER_NAME).build());
    	
    	assertEquals(user.getStatusCode(), HttpStatus.OK);
    	assertEquals(user.getBody().getUserName(), USER_NAME);
    }

    @Test
    public void testGetUserById() {
        final ResponseEntity<User> user = postUser(createUserBuilder().userName(USER_NAME).build());

        assertEquals(user.getStatusCode(), HttpStatus.OK);

        final ResponseEntity<User> result = getUser(user.getBody().getId().toString());

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getUserName(), USER_NAME);
    }

    @Test
    public void testUpdateUserById() {
        final String updateName = "ferenc1243";
        final ResponseEntity<User> post = postUser(createUserBuilder().userName(USER_NAME).build());
        updateUser(post.getBody().getId().toString(), createUserBuilder().userName(updateName).build());

        final ResponseEntity<User> user = getUser(post.getBody().getId().toString());

        assertEquals(user.getStatusCode(), HttpStatus.OK);
        assertEquals(user.getBody().getUserName(), updateName);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testDeleteUserById() {
        final ResponseEntity<User> user = postUser(createUserBuilder().build());

        assertEquals(user.getStatusCode(), HttpStatus.OK);

        deleteUser(user.getBody().getId().toString());
        getUser(user.getBody().getId().toString());
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testGetUserByInvalidId() {
        getUser("invalidId");
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testUpdateUserByInvalidId() {
        updateUser("invalidId", createUserBuilder().firstName("first").lastName("last").build());	
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testDeleteUserByInvalidId() {
        deleteUser("invalidId");
    }

}
