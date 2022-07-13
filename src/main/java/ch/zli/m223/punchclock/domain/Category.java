package ch.zli.m223.punchclock.domain;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="category")
    private ArrayList<Entry> entries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }
}
