package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

import java.util.List;

import static ru.javaops.topjava2.web.dish.DishTestData.*;

public class RestaurantTestData {

    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");

    public static final int RESTAURANT_ID1 = 1;
    public static final int RESTAURANT_ID2 = 2;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID1, "FirstRestaurant");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID2, "SecondRestaurant");
    public static final Restaurant restaurant1WithoutDishes = new Restaurant(RESTAURANT_ID1, "FirstRestaurant");
    public static final Restaurant restaurant2WithoutDishes = new Restaurant(RESTAURANT_ID2, "SecondRestaurant");

    static {
        restaurant1.setDishes(List.of(dish1, dish2, dish3));
        restaurant2.setDishes(List.of(dish4, dish5, dish6));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID1, "Updated Restaurant");
    }
}