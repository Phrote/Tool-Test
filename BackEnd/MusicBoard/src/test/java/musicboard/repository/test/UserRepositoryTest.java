package musicboard.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import MusicBoard.entities.User;
import MusicBoard.repositories.UserRepository;
import musicboard.repository.test.core.BaseRepositoryTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testDeleteAll() {
        userRepository.deleteAll();
        final Iterable<User> users = userRepository.findAll();

        assertFalse(users.iterator().hasNext());
    }

    @Test
    public void testFindAll() {
        userRepository.deleteAll();
        addUserToRepository(userBuilder().userName("user1"));
        addUserToRepository(userBuilder().userName("user2"));

        final List<User> userList = Lists.newArrayList();
        userRepository.findAll().forEach(user -> userList.add(user));

        assertEquals(userList.size(), 2);
    }

    @Test
    public void testFindById() {
        userRepository.deleteAll();
        final String userName = "jancsika0123";
        addUserToRepository(userBuilder().userName(userName));

        final User user = userRepository.findByUserName(userName).get();
        final Optional<User> found = userRepository.findById(user.getId());

        assertTrue(found.isPresent());
        assertEquals(found.get().getUserName(), userName);
    }

    @Test
    public void testFindByUserName() {
        final String userName = "jancsika0123";
        addUserToRepository(userBuilder().userName(userName));

        final Optional<User> found = userRepository.findByUserName(userName);

        assertTrue(found.isPresent());
        assertEquals(found.get().getUserName(), userName);
    }

    @Test
    public void testFindByEmail() {
        final String email = "jancsika@email.hu";
        addUserToRepository(userBuilder().email(email));

        final Optional<User> found = userRepository.findByEmail(email);

        assertTrue(found.isPresent());
        assertEquals(found.get().getEmail(), email);
    }

    private User.Builder userBuilder() {
        return new User.Builder()
                .userName("username")
                .passWord("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email");
    }

    private void addUserToRepository(final User.Builder builder) {
        final User user = builder.build();
        entityManager.persist(user);
        entityManager.flush();
    }

}
