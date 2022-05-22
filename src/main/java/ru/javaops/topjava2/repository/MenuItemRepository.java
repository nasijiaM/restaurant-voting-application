package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.MenuItem;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id=:restaurantId ORDER BY m.date DESC")
    List<MenuItem> getAllByRestaurant(@Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id=:restaurantId AND m.date=:localDate ORDER BY m.name ASC")
    List<MenuItem> getAllByRestaurantAndDate(@Param("restaurantId") int restaurantId,
                                             @Param("localDate") LocalDate localDate);
}
