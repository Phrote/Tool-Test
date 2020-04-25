package musicboard.api.test.user.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import MusicBoard.entities.User;
import musicboard.api.test.user.core.AbstractUserApiTest;

public class UserApiNameMappingTests extends AbstractUserApiTest {

	private static final String MAPPING  = "username" + JOINER;
	private static final String USER_NAME = "pityuka12";

    @Test
    public void testGetUserByName() {
        postUser(createUserBuilder().userName(USER_NAME).build());

        final ResponseEntity<User> result = getUser(MAPPING + USER_NAME);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getUserName(), USER_NAME);
    }

    @Test
    public void testUpdateUserByName() {
        final String updateName = "ferenc1243";
        postUser(createUserBuilder().userName(USER_NAME).build());
        updateUser(MAPPING + USER_NAME, createUserBuilder().userName(updateName).build());

        final ResponseEntity<User> user = getUser(MAPPING + updateName);

        assertEquals(user.getStatusCode(), HttpStatus.OK);
        assertEquals(user.getBody().getUserName(), updateName);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testDeleteUserByName() {
        postUser(createUserBuilder().userName(USER_NAME).build());

        deleteUser(MAPPING + USER_NAME);
        getUser(MAPPING + USER_NAME);
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testGetUserByInvalidName() {
        getUser("invalidName");
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testUpdateUserByInvalidName() {
        updateUser("invalidName", createUserBuilder().build());	
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testDeleteUserByInvalidName() {
        deleteUser("invalidName");
    }
}
