package MusicBoard.entities.wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

public class CollectionResource<T> implements Serializable, Iterable<T> {

    private static final long serialVersionUID = -6656025304628321951L;

    private final List<T> items;

    protected CollectionResource(List<T> items) {
        this.items = items == null ? Collections.<T>emptyList() : items;
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @JsonIgnore
    public final boolean isEmpty() {
        return getItems().isEmpty();
    }

    public final List<T> getItems() {
        return items;
    }

    public final int getTotal() {
        return items.size();
    }

    public static <T> CollectionResource<T> of(List<T> items) {
        return new CollectionResource<T>(items);
    }

    @JsonCreator
    public static <T> CollectionResource<T> of(@JsonProperty("items") Collection<T> items) {
        if (items instanceof List) {
            return of((List<T>) items);
        }
        return of(ImmutableList.copyOf(items));
    }

}
