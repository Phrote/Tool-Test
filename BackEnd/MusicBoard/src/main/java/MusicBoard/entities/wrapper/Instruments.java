package MusicBoard.entities.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import MusicBoard.entities.Instrument;

public class Instruments extends CollectionResource<Instrument> {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public Instruments(@JsonProperty("items") List<Instrument> items) {
        super(items);
    }

}
