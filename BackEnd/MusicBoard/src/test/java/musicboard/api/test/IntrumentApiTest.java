package musicboard.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import MusicBoard.entities.Instrument;
import MusicBoard.entities.Instrument.InstrumentType;
import MusicBoard.entities.wrapper.Instruments;
import musicboard.api.test.core.AbstractInstrumentApiTest;

public class IntrumentApiTest extends AbstractInstrumentApiTest {

    @Test
    public void testGetAll() {
        postInstrument(createInstrumentBuilder().build());
        postInstrument(createInstrumentBuilder().build());

        final ResponseEntity<Instruments> instruments = getInstruments();
        assertEquals(instruments.getStatusCode(), HttpStatus.OK);
        assertEquals(instruments.getBody().getTotal(), 2);
    }

    @Test
    public void testGetInstrumentById() {
        final String name = "drums";
        final InstrumentType type = InstrumentType.DRUMS;
        final ResponseEntity<Instrument> instrument = postInstrument(createInstrumentBuilder().name(name).instrumentType(type).build());

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);

        final ResponseEntity<Instrument> result = getInstrument(instrument.getBody().getId());

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getName(), name);
        assertEquals(result.getBody().getInstrumentType(), type);
    }

    @Test
    public void testPostInstrument() {
        final String name = "piano";
        final InstrumentType type = InstrumentType.PIANO;
        final ResponseEntity<Instrument> instrument = postInstrument(createInstrumentBuilder().name(name).instrumentType(type).build());

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);
        assertEquals(instrument.getBody().getName(), name);
        assertEquals(instrument.getBody().getInstrumentType(), type);
    }

    @Test
    public void testUpdateInstrument() {
        final String name = "piano", updateName = "grand piano";
        final InstrumentType type = InstrumentType.PIANO;
        final ResponseEntity<Instrument> post = postInstrument(createInstrumentBuilder().name(name).instrumentType(type).build());
        updateInstrument(post.getBody().getId(), createInstrumentBuilder().name(updateName).instrumentType(type).build());

        final ResponseEntity<Instrument> instrument = getInstrument(post.getBody().getId());

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);
        assertEquals(instrument.getBody().getName(), updateName);
        assertEquals(instrument.getBody().getInstrumentType(), type);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testDeleteInstrument() {
        final ResponseEntity<Instrument> instrument = postInstrument(createInstrumentBuilder().build());

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);

        deleteInstrument(instrument.getBody().getId());
        getInstrument(instrument.getBody().getId());
    }
}
