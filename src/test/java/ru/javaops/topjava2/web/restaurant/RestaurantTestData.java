package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

import java.util.List;

import static ru.javaops.topjava2.web.menuItem.MenuItemTestData.*;

public class RestaurantTestData {

    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems");

    public static final int RESTAURANT_ID1 = 1;
    public static final int RESTAURANT_ID2 = 2;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID1, "FirstRestaurant");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID2, "SecondRestaurant");
    public static final Restaurant restaurant1WithoutMenuItems = new Restaurant(RESTAURANT_ID1, "FirstRestaurant");
    public static final Restaurant restaurant2WithoutMenuItems = new Restaurant(RESTAURANT_ID2, "SecondRestaurant");

    static {
        restaurant1.setMenuItems(List.of(MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3));
        restaurant2.setMenuItems(List.of(MENU_ITEM_4, MENU_ITEM_5, MENU_ITEM_6));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID1, "Updated Restaurant");
    }
}