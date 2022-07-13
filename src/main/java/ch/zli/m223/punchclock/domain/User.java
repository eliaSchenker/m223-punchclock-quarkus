package ch.zli.m223.punchclock.domain;

import javax.persistence.*;
import io.quarkus.security.jpa.UserDefinition;

@Entity
@Table(name = "test_user")
@UserDefinition 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique=true)
    private String username;
    
    @Column(nullable = false)
    private String password;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}