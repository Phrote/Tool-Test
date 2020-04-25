package MusicBoard.entities.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import MusicBoard.entities.User;

public class Users extends CollectionResource<User> {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public Users(@JsonProperty("items") List<User> items) {
        super(items);
    }

}
