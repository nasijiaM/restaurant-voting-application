package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menuitem",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "name"}, name = "uk_menuitem_date_name")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class MenuItem extends NamedEntity{
    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference
    @ToString.Exclude
    private Restaurant restaurant;

    public MenuItem(Integer id, String name, Integer price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.name, menuItem.price, menuItem.date);
    }

    public MenuItem(Integer id, String name, Integer price, LocalDate date, Restaurant restaurant) {
        this(id, name, price, date);
        this.restaurant = restaurant;
    }
}