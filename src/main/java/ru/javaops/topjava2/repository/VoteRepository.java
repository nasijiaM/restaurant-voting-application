package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.id=?1 AND v.user.id=?2")
    Optional<Vote> getWithRestaurant(int voteId, int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 ORDER BY v.date DESC")
    List<Vote> getAll(int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.date=?1 AND v.user.id=?2")
    Optional<Vote> findByDateAndUserId(LocalDate date, int userId);

    @Query("SELECT v FROM Vote v WHERE v.id=?1 AND v.user.id=?2")
    Optional<Vote> findByIdAndUserId(int it, int userId);
}