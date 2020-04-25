package musicboard.api.test.instrument.core;

import org.springframework.http.ResponseEntity;

import MusicBoard.entities.Instrument;
import MusicBoard.entities.Instrument.InstrumentType;
import MusicBoard.entities.wrapper.Instruments;
import musicboard.api.test.core.BaseApiTest;

public abstract class AbstractInstrumentApiTest extends BaseApiTest {

	@Override
	protected String getRequestMapping() {
		return JOINER + "instrument";
	}
	
	@Override
	protected final void clearRepository() {
		getInstruments().getBody().getItems().forEach(instrument -> deleteInstrument(instrument.getId().toString()));
	}
	
	protected ResponseEntity<Instruments> getInstruments() {
		return getRestTemplate().getForEntity(createUri() ,Instruments.class);
	}

	protected ResponseEntity<Instrument> getInstrument(final String mapping) {
		return getRestTemplate().getForEntity(createUri(mapping), Instrument.class);
	}
	
	protected void deleteInstrument(final String mapping) {
		getRestTemplate().delete(createUri(mapping));
	}

	protected ResponseEntity<Instrument> postInstrument(final Instrument instrument) {
		return getRestTemplate().postForEntity(createUri(), instrument, Instrument.class);
	}
	
	protected void updateInstrument(final String mapping, final Instrument instrument) {
		getRestTemplate().put(createUri(mapping), instrument);
	}
	
	protected Instrument.Builder createInstrumentBuilder() {
		return Instrument.builder()
				.name("guitar")
				.instrumentType(InstrumentType.GUITAR)
				.keybinds("");
	}
}
