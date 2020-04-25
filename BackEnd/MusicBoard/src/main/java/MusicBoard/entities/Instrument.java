package MusicBoard.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instrument")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Instrument extends BaseEntity implements Serializable {
	
    public enum InstrumentType {
        GUITAR,
        PIANO,
        DRUMS
    }

    @Column
    @NotNull
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private InstrumentType instrumentType;

    // Ezt még ki kell találni, hogy legyen tárolva
    @Column
    @NotNull
    private String keybinds;

    @JoinTable
    @ManyToMany(targetEntity = User.class)
    private List<User> users;
    
    public static Instrument.Builder builder() {
    	return new Instrument.Builder();
    }
    
    public static class Builder {
        private String name;
        private InstrumentType instrumentType;
        private String keybinds = "";
        private List<User> users = new ArrayList<User>();
        
        public Builder name(final String name) {
        	this.name = name;
        	return this;
        }
        
        public Builder instrumentType(final Instrument.InstrumentType instrumentType) {
        	this.instrumentType = instrumentType;
        	return this;
        }
        
        public Builder keybinds(final String keybinds) {
        	this.keybinds = keybinds;
        	return this;
        }
        
        public Builder users(final List<User> users) {
        	this.users = users;
        	return this;
        }
        
        public Instrument build() {
        	return new Instrument(name, instrumentType, keybinds, users);
        }
    }

}
