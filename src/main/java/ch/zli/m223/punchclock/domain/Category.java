package ch.zli.m223.punchclock.domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="category")
    private List<Entry> entries;

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

    public List<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }
}
