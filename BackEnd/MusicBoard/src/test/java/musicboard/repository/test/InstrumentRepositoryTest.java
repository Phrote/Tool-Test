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

import MusicBoard.entities.Instrument;
import MusicBoard.entities.Instrument.InstrumentType;
import MusicBoard.repositories.InstrumentRepository;
import musicboard.repository.test.core.BaseRepositoryTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InstrumentRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Test
    public void testDeleteAll() {
        instrumentRepository.deleteAll();
        final Iterable<Instrument> users = instrumentRepository.findAll();

        assertFalse(users.iterator().hasNext());
    }

    @Test
    public void testFindAll() {
        instrumentRepository.deleteAll();
        addInstrumentToRepository(instrumentBuilder().name("instrument1"));
        addInstrumentToRepository(instrumentBuilder().name("instrument2"));

        final List<Instrument> instrumentList = Lists.newArrayList();
        instrumentRepository.findAll().forEach(instrument -> instrumentList.add(instrument));

        assertEquals(instrumentList.size(), 2);
    }

    @Test
    public void testFindById() {
        instrumentRepository.deleteAll();
        final String name = "guitar";
        addInstrumentToRepository(instrumentBuilder().name(name));

        final Instrument instrument = instrumentRepository.findByName(name).get();
        final Optional<Instrument> found = instrumentRepository.findById(instrument.getId());

        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), name);
    }

    @Test
    public void testFindByName() {
        final String name = "guitar";
        addInstrumentToRepository(instrumentBuilder().name(name));

        final Optional<Instrument> found = instrumentRepository.findByName(name);

        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), name);
    }

    private Instrument.Builder instrumentBuilder() {
        return Instrument.builder()
                .name("instrument")
                .instrumentType(InstrumentType.GUITAR);
    }

    private void addInstrumentToRepository(final Instrument.Builder builder) {
        final Instrument instrument = builder.build();
        entityManager.persist(instrument);
        entityManager.flush();
    }
}
