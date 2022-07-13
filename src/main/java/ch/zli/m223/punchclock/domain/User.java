package ch.zli.m223.punchclock.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

@Entity
@Table(name = "user")
@UserDefinition 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Username
    @Column(nullable = false, unique=true)
    private String username;
    
    @Password
    @Column(nullable = false)
    private String password;

    @Roles
    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy="user")
    private List<Entry> entries;

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


    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

}