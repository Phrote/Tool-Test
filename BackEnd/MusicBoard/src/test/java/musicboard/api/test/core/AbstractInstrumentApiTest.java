package musicboard.api.test.core;

import java.net.URI;

import org.springframework.http.ResponseEntity;

import MusicBoard.entities.Instrument;
import MusicBoard.entities.Instrument.InstrumentType;
import MusicBoard.entities.wrapper.Instruments;

public abstract class AbstractInstrumentApiTest extends BaseApiTest {

	@Override
	protected String getRequestMapping() {
		return "/instrument";
	}
	
	@Override
	protected void clearRepository() {
		getInstruments().getBody().getItems().forEach(instrument -> deleteInstrument(instrument.getId()));
	}
	
	protected ResponseEntity<Instruments> getInstruments() {
		final URI uri = createUri(getUrl());
		final ResponseEntity<Instruments> result = getRestTemplate().getForEntity(uri,Instruments.class);
		return result;
	}

	protected ResponseEntity<Instrument> getInstrument(final Integer id) {
		final URI uri = createUri(getUrl() + JOINER + id.toString());
		final ResponseEntity<Instrument> result = getRestTemplate().getForEntity(uri,Instrument.class);
		return result;
	}
	
	protected void deleteInstrument(final Integer id) {
		final URI uri = createUri(getUrl() + JOINER + id.toString());
		getRestTemplate().delete(uri);
	}

	protected ResponseEntity<Instrument> postInstrument(final Instrument instrument) {
		final URI uri = createUri(getUrl());
		final ResponseEntity<Instrument> result = getRestTemplate().postForEntity(uri, instrument, Instrument.class);
		return result;
	}
	
	protected void updateInstrument(final Integer id, final Instrument instrument) {
		final URI uri = createUri(getUrl() + JOINER + id.toString());
		getRestTemplate().put(uri, instrument);
	}
	
	protected Instrument.Builder createInstrumentBuilder() {
		return Instrument.builder()
				.name("guitar")
				.instrumentType(InstrumentType.GUITAR)
				.keybinds("");
	}
}
