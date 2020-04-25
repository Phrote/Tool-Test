package musicboard.repository.test.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class BaseRepositoryTest {
	
    @Autowired
    protected TestEntityManager entityManager;
    
}
