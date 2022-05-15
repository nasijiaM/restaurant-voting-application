package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.LateToVoteException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.UserRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {

    static final String REST_URL = "/api/votes";
    static final LocalTime DEADLINE = LocalTime.of(11, 00);

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get {}", id);

        return checkNotFoundWithId(voteRepository.getWithRestaurant(id)
                .filter(vote -> vote.getUser().getId() == SecurityUtil.authId())
                .orElse(null), id);
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll");
        return voteRepository.getAll(SecurityUtil.authId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Vote> createWithLocation(@RequestParam int id) {
        if (LocalTime.now().isAfter(DEADLINE)) {
            throw new LateToVoteException(DEADLINE.toString());
        }
        int userId = SecurityUtil.authId();

        Vote vote = voteRepository.findByDateAndUserId(LocalDate.now(), userId).orElse(null);
        if (vote == null) {
            vote = new Vote();
            User user = userRepository.findById(userId).orElse(null);
            checkNotFoundWithId(user, userId);
            vote.setUser(user);

            vote.setDate(LocalDate.now());
        }

        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        checkNotFoundWithId(restaurant, id);
        vote.setRestaurant(restaurant);
        log.info("vote {}", vote);
        voteRepository.save(vote);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vote);
    }
}