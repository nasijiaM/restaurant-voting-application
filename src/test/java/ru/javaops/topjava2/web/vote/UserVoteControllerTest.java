package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.service.VoteService.DEADLINE;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.NOT_FOUND;
import static ru.javaops.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava2.web.vote.UserVoteController.REST_URL;
import static ru.javaops.topjava2.web.vote.VoteTestData.*;

class UserVoteControllerTest extends AbstractControllerTest {

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + VOTE1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER_WITHOUT_USER.contentJson(vote1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + VOTE1_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Vote updated = getUpdated();
        if (LocalTime.now().isAfter(DEADLINE)) {
            perform(MockMvcRequestBuilders.put(REST_URL + "/" + VOTE1_ID).contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isMethodNotAllowed());
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL + "/" + VOTE1_ID).contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isNoContent());

            VOTE_MATCHER.assertMatch(voteRepository.getById(VOTE1_ID), updated);
        }
    }
}