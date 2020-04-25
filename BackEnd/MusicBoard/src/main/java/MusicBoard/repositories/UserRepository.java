package MusicBoard.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import MusicBoard.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
       
}
