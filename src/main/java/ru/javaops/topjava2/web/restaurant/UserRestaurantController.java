package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Restaurant;

import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    @GetMapping("/with-menu-items")
    public List<Restaurant> getWithMenuItems() {
        return super.getWithMenuItems();
    }
}