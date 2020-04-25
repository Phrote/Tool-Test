package MusicBoard.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User extends BaseEntity implements Serializable {

	public enum Role {
		ROLE_ADMIN,
		ROLE_USER,
		ROLE_GUEST
	}

    @Column
    @NotNull
    private String userName;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column
    @NotNull
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @JsonIgnore
    @ManyToMany(targetEntity = Instrument.class, mappedBy = "users")
    private List<Instrument> instruments;
    
    public static User.Builder builder() {
    	return new User.Builder();
    }
    
    public static class Builder {
    	private String userName;
        private String passWord;
        private String firstName;
        private String lastName;
        private String email;
        private Role role = User.Role.ROLE_USER;
        private List<Instrument> instruments = new ArrayList<Instrument>();
        
        public Builder userName(final String userName) {
        	this.userName = userName;
        	return this;
        }
        
        public Builder passWord(final String passWord) {
        	this.passWord = passWord;
        	return this;
        }
        
        public Builder firstName(final String firstName) {
        	this.firstName = firstName;
        	return this;
        }
        
        public Builder lastName(final String lastName) {
        	this.lastName = lastName;
        	return this;
        }
        
        public Builder email(final String email) {
        	this.email = email;
        	return this;
        }
        
        public Builder role(final User.Role role) {
        	this.role = role;
        	return this;
        }
        
        public Builder instruments(final List<Instrument> instruments) {
        	this.instruments = instruments;
        	return this;
        }
        
        public User build() {
        	return new User(userName, passWord, firstName, lastName, email, role, instruments);
        }
    }

}
