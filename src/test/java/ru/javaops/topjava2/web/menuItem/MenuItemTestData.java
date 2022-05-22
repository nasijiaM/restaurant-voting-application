package ru.javaops.topjava2.web.menuItem;

import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.restaurant1WithoutMenuItems;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingEqualsComparator(MenuItem.class);

    public static final int MENUITEM_ID1 = 1;
    public static final int MENUITEM_ID2 = 2;
    public static final int MENUITEM_ID3 = 3;
    public static final int MENUITEM_ID4 = 4;
    public static final int MENUITEM_ID5 = 5;
    public static final int MENUITEM_ID6 = 6;

    public static final MenuItem MENU_ITEM_1 = new MenuItem(MENUITEM_ID1, "Fish", 250, LocalDate.now());
    public static final MenuItem MENU_ITEM_2 = new MenuItem(MENUITEM_ID2, "Rice", 100, LocalDate.now());
    public static final MenuItem MENU_ITEM_3 = new MenuItem(MENUITEM_ID3, "Tea", 20, LocalDate.now());
    public static final MenuItem MENU_ITEM_4 = new MenuItem(MENUITEM_ID4, "Meat", 200, LocalDate.now());
    public static final MenuItem MENU_ITEM_5 = new MenuItem(MENUITEM_ID5, "Pasta", 100, LocalDate.now());
    public static final MenuItem MENU_ITEM_6 = new MenuItem(MENUITEM_ID6, "Coffee", 40, LocalDate.now());

    public static MenuItem getNew() {
        return new MenuItem(null, "New Item", 150, LocalDate.now(), restaurant1WithoutMenuItems);
    }

    public static MenuItem getUpdated() {
        return new MenuItem(MENUITEM_ID1, "Updated Menu Item", 100, LocalDate.now(), restaurant1WithoutMenuItems);
    }

}