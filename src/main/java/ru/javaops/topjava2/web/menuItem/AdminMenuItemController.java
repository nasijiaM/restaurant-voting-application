package ru.javaops.topjava2.web.menuItem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.repository.MenuItemRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuItemController {

    static final String REST_URL = "/api/admin/menu-items";

    @Autowired
    private MenuItemRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public MenuItem get(@PathVariable int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @GetMapping("/by-restaurant")
    public List<MenuItem> getAllByRestaurant(@RequestParam int restaurantId) {
        log.info("getAll by restaurant with id {}", restaurantId);
        return repository.getAllByRestaurant(restaurantId);
    }

    @GetMapping("/by-restaurant-and-date")
    public List<MenuItem> getAllByRestaurantAndDate(@RequestParam int restaurantId,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        log.info("getAll by restaurantId {} and date {}", restaurantId, localDate);
        return repository.getAllByRestaurantAndDate(restaurantId, localDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem,
                                                       @RequestParam int restaurantId) {
        log.info("create {}", menuItem);
        checkNew(menuItem);
        menuItem.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        MenuItem created = repository.save(menuItem);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int id) {
        log.info("update {} with id={}", menuItem, id);
        assureIdConsistent(menuItem, id);
        checkNotFoundWithId(repository.save(menuItem), menuItem.id());
    }
}