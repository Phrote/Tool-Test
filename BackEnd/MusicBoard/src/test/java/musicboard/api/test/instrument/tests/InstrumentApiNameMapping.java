package musicboard.api.test.instrument.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import MusicBoard.entities.Instrument;
import MusicBoard.entities.Instrument.InstrumentType;
import musicboard.api.test.instrument.core.AbstractInstrumentApiTest;

public class InstrumentApiNameMapping extends AbstractInstrumentApiTest {
	
	private static final String MAPPING = "name" + JOINER;
	private static final String NAME = "drums";
	private static final InstrumentType TYPE = InstrumentType.DRUMS;
    
    @Test
    public void testGetInstrumentByName() {
        postInstrument(createInstrumentBuilder().name(NAME).instrumentType(TYPE).build());

        final ResponseEntity<Instrument> result = getInstrument(MAPPING + NAME);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getName(), NAME);
        assertEquals(result.getBody().getInstrumentType(), TYPE);
    }

    @Test
    public void testUpdateInstrumentByName() {
        final String updateName = "grandpiano";
        final InstrumentType updateType = InstrumentType.PIANO;
        postInstrument(createInstrumentBuilder().name(NAME).instrumentType(TYPE).build());
        updateInstrument(MAPPING + NAME, createInstrumentBuilder().name(updateName).instrumentType(updateType).build());

        final ResponseEntity<Instrument> instrument = getInstrument(MAPPING + updateName);

        assertEquals(instrument.getStatusCode(), HttpStatus.OK);
        assertEquals(instrument.getBody().getName(), updateName);
        assertEquals(instrument.getBody().getInstrumentType(), updateType);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testDeleteInstrumentByName() {
    	postInstrument(createInstrumentBuilder().name(NAME).build());

        deleteInstrument(MAPPING + NAME);
        getInstrument(MAPPING + NAME);
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testGetInstrumentByInvalidName() {
        getInstrument("invalidId");
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testUpdateInstrumentByInvalidName() {
        updateInstrument("invalidId", createInstrumentBuilder().build());	
    }
    
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testDeleteInstrumentByInvalidName() {
        deleteInstrument("invalidId");
    }
}
