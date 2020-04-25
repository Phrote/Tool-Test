package musicboard.api.test.instrument.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import MusicBoard.entities.Instrument;
import MusicBoard.entities.Instrument.InstrumentType;
import MusicBoard.entities.wrapper.Instruments;
import musicboard.api.test.instrument.core.AbstractInstrumentApiTest;

public final class InstrumentApiIdMappingTests extends AbstractInstrumentApiTest {

	private static final String NAME = "drums";
	private static final InstrumentType TYPE = InstrumentType.DRUMS;
	
    @Test
    public void testGetAll() {
        postInstrument(createInstrumentBuilder().build());
        postInstrument(createInstrumentBuilder().build());

        final ResponseEntity<Instruments> instruments = getInstruments();
        assertEquals(instruments.getStatusCode(), HttpStatus.OK);
        assertEquals(instruments.getBody().getTotal(), 2);
    }

    @Test
    public void testPostInstrument() {
    	final ResponseEntity<Instrument> instrument = postInstrument(createInstrumentBuilder().name(NAME).instrumentType(TYPE).build());
    	
    	assertEquals(instrument.getStatusCode(), HttpStatus.OK);
    	assertEquals(instrument.getBody().getName(), NAME);
    	assertEquals(instrument.getBody().getInstrumentType(), TYPE);
    }
    
    @Test
    public void testGetInstrumentById() {
        final ResponseEntity<Instrument> instrument = postInstrument(createInstrumentBuilder().name(NAME).instrumentType(TYPE).build());

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);

        final ResponseEntity<Instrument> result = getInstrument(instrument.getBody().getId().toString());

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getName(), NAME);
        assertEquals(result.getBody().getInstrumentType(), TYPE);
    }

    @Test
    public void testUpdateInstrumentById() {
        final String updateName = "grand piano";
        final InstrumentType updateType = InstrumentType.PIANO;
        final ResponseEntity<Instrument> post = postInstrument(createInstrumentBuilder().name(NAME).instrumentType(TYPE).build());
        updateInstrument(post.getBody().getId().toString(), createInstrumentBuilder().name(updateName).instrumentType(updateType).build());

        final ResponseEntity<Instrument> instrument = getInstrument(post.getBody().getId().toString());

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);
        assertEquals(instrument.getBody().getName(), updateName);
        assertEquals(instrument.getBody().getInstrumentType(), updateType);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testDeleteInstrumentById() {
        final ResponseEntity<Instrument> instrument = postInstrument(createInstrumentBuilder().build());

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);

        deleteInstrument(instrument.getBody().getId().toString());
        getInstrument(instrument.getBody().getId().toString());
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testGetInstrumentByInvalidId() {
        getInstrument("invalidId");
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testUpdateInstrumentByInvalidId() {
        updateInstrument("invalidId", createInstrumentBuilder().build());	
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testDeleteInstrumentByInvalidId() {
        deleteInstrument("invalidId");
    }
}
