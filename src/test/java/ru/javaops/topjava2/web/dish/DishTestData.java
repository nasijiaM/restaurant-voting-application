package ru.javaops.topjava2.web.dish;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;
    public static final int DISH5_ID = 5;
    public static final int DISH6_ID = 6;

    public static final Dish dish1 = new Dish(DISH1_ID, "Fish", 250, LocalDate.now());
    public static final Dish dish2 = new Dish(DISH2_ID, "Rice", 100, LocalDate.now());
    public static final Dish dish3 = new Dish(DISH3_ID, "Tea", 20, LocalDate.now());
    public static final Dish dish4 = new Dish(DISH4_ID, "Meat", 200, LocalDate.now());
    public static final Dish dish5 = new Dish(DISH5_ID, "Pasta", 100, LocalDate.now());
    public static final Dish dish6 = new Dish(DISH6_ID, "Coffee", 40, LocalDate.now());
}