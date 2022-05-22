package ru.javaops.topjava2.web.vote;

import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.admin;
import static ru.javaops.topjava2.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER_WITHOUT_USER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user");
    public static MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);


    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now(), restaurant2WithoutMenuItems);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), restaurant2WithoutMenuItems, user);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, LocalDate.now(), restaurant1WithoutMenuItems, admin);
    }
}