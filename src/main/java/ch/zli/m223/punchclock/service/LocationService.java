package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Location;

@ApplicationScoped
public class LocationService {
    @Inject
    private EntityManager entityManager;

    public LocationService() {
    }

    @Transactional 
    public Location createLocation(Location location) {
        entityManager.persist(location);
        return location;
    }

    @SuppressWarnings("unchecked")
    public List<Location> findAll() {
        var query = entityManager.createQuery("FROM Location");
        return query.getResultList();
    }

    @Transactional
    public Location updateLocation(Location location) {
        entityManager.merge(location);
        return location;
    }

    @Transactional
    public Location deleteLocation(long id) {
        Location location = entityManager.find(Location.class, id);
        entityManager.remove(location);
        return location;
    }
}
