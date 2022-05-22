package ru.javaops.topjava2.web.vote;

import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.web.SecurityUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {

    static final String REST_URL = "/api/votes";

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get {}", id);

        return voteService.get(id, SecurityUtil.authId());
    }

    @GetMapping("/by-date")
    public Vote getByDate(@RequestParam LocalDate date) {
        log.info("get by date {}", date);

        return voteService.getByDate(date, SecurityUtil.authId());
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll");
        return voteService.getAll(SecurityUtil.authId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "empty", content = @Content)
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId) {
        log.info("vote for restaurant with id= {}", restaurantId);

        Vote created = voteService.create(restaurantId, SecurityUtil.authId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote, @PathVariable int id) {

        int userId = SecurityUtil.authId();
        log.info("update {} with id={}", vote, id);
        assureIdConsistent(vote, id);
        voteService.update(vote, userId);
    }
}