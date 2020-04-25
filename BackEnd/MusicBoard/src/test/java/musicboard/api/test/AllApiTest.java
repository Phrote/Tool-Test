package musicboard.api.test;

import org.junit.ClassRule;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import musicboard.api.test.core.MusicBoardAppRule;
import musicboard.api.test.instrument.tests.AllInstrumentApiTest;
import musicboard.api.test.user.tests.AllUserApiTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllInstrumentApiTest.class,
        AllUserApiTest.class
})
public class AllApiTest {

    @ClassRule
    public static final RuleChain appRule = RuleChain
            .outerRule(new MusicBoardAppRule());
}
