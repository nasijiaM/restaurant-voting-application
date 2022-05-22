package ru.javaops.topjava2.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javaops.topjava2.error.AlreadyVotedException;
import ru.javaops.topjava2.error.LateToVoteException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNotFound;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private RestaurantRepository restaurantRepository;

    public static final LocalTime DEADLINE = LocalTime.of(23, 45);

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository.getWithRestaurant(id, userId)
                .orElse(null), id);
    }

    public Vote getByDate(LocalDate date, int userId) {
        return checkNotFound(voteRepository.findByDateAndUserId(date, userId).orElse(null), "vote for " + date);
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    public void update(Vote vote, int userId) {
        if (LocalTime.now().isAfter(DEADLINE)) {
            throw new LateToVoteException(DEADLINE.toString());
        }
        Assert.notNull(vote, "vote must not be null");

        if (voteRepository.findByIdAndUserId(vote.getId(), userId).orElse(null) != null) {
            checkNotFoundWithId(voteRepository.save(vote), vote.getId());
        }
    }

    public Vote create(int restaurantId, int userId) {
        Vote vote = voteRepository.findByDateAndUserId(LocalDate.now(), userId).orElse(null);
        if (vote == null) {
            vote = new Vote();
            vote.setUser(SecurityUtil.authUser());

            vote.setDate(LocalDate.now());

            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
            checkNotFoundWithId(restaurant, restaurantId);
            vote.setRestaurant(restaurant);

            return voteRepository.save(vote);
        } else {
            throw new AlreadyVotedException(String.valueOf(userId));
        }
    }
}