package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Entry;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    public EntryService() {
    }

    @Transactional 
    public Entry createEntry(Entry entry) {
        entityManager.persist(entry);
        return entry;
    }

    @SuppressWarnings("unchecked")
    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry");
        return query.getResultList();
    }

    @Transactional
    public Entry updateEntry(Entry updatedEntry, long id) {
        Entry entry = entityManager.find(Entry.class, id);
        entry.setCheckIn(updatedEntry.getCheckIn());
        entry.setCheckOut(updatedEntry.getCheckOut());
        entityManager.persist(entry);
        return entry;
    }

    @Transactional
    public Entry deleteEntry(long id) {
        Entry entry = entityManager.find(Entry.class, id);
        entityManager.remove(entry);
        return entry;
    }
}
