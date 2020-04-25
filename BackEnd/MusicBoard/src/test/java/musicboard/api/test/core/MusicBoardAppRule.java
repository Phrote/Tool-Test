package musicboard.api.test.core;

import MusicBoard.MusicBoardApplication;
import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;


public class MusicBoardAppRule extends ExternalResource {

    private ApplicationContext context;

    @Override
    protected void before() throws Throwable {
        context = SpringApplication.run(MusicBoardApplication.class, "");
    }

    @Override
    protected void after() {
        SpringApplication.exit(context);
    }
}
