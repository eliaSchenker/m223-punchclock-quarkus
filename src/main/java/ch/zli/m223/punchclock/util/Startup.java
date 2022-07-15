package ch.zli.m223.punchclock.util;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.domain.Location;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.CategoryService;
import ch.zli.m223.punchclock.service.LocationService;
import ch.zli.m223.punchclock.service.UserService;
import io.quarkus.runtime.StartupEvent;

/**
 * Loads test data on startup
 * Source: https://quarkus.io/guides/security-jpa
 */
@Singleton
public class Startup {
    @Inject
    UserService userService;

    @Inject
    LocationService locationService;

    @Inject
    CategoryService categoryService;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        //Create test location
        Location location = new Location();
        location.setName("Coop Filiale Sihlcity");
        locationService.createLocation(location);

        
        //Create test user (regular)
        User regularUser = new User();
        regularUser.setLocation(location);
        regularUser.setRole("User");
        regularUser.setUsername("Test-User");
        regularUser.setPassword("1234");

        userService.createNewUser(regularUser);

        //Create test user (admin)
        User adminUser = new User();
        adminUser.setLocation(location);
        adminUser.setRole("Admin");
        adminUser.setUsername("Test-Admin");
        adminUser.setPassword("1234");

        userService.createNewUser(adminUser);

        //Create test category
        Category category = new Category();
        category.setName("Work");
        categoryService.createCategory(category);

        Category category2 = new Category();
        category2.setName("Lunch break");
        categoryService.createCategory(category2);
    }
}