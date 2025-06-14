package org.rodeflow.RodeFlowServer.repository.racer;

import org.rodeflow.RodeFlowServer.entity.racer.ClassicRaceEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassicRaceRepository extends JpaRepository<ClassicRaceEntity, Long>, JpaSpecificationExecutor<ClassicRaceEntity>  {
        Optional<ClassicRaceEntity> findByName(String name);

        @Query("SELECT c FROM ClassicRaceEntity c JOIN c.raceResultID r WHERE r = :raceResultID")
        Optional<ClassicRaceEntity> findByRaceResultID(@Param("raceResultID") Integer raceResultID);

        @Query("SELECT DISTINCT cre.classicRaceID FROM ClassicRaceEntity cre " + "JOIN cre.raceResultID crr " + "JOIN RaceResultEntity rre ON crr = rre.raceResultID " + "JOIN RacerEntity re ON rre.racerID = re.racerID " + "WHERE re.teamID = :teamID")
        List<Integer> findClassicRaceIDsByTeamID(@Param("teamID") Integer teamID);

        @Query(value = "SELECT crr.race_resultid FROM classic_race_entity cre JOIN classic_race_race_result_id crr ON cre.classic_raceid = crr.classic_raceid JOIN race_result_entity rre ON crr.race_resultid = rre.race_resultid WHERE cre.classic_raceid = :classicRaceID ORDER BY CASE WHEN rre.finished_at IS NULL THEN 1 ELSE 0 END, rre.finished_at ASC;", nativeQuery = true)
        List<Integer> findByIDWithSort(@Param("classicRaceID") Integer classicRaceID);

        @Query(value = "SELECT c.classic_raceid FROM classic_race_entity c WHERE " +
                "LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                "LOWER(c.length) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                "LOWER(c.state) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                "LOWER(c.type) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                "LOWER(c.elevation) LIKE LOWER(CONCAT('%', :query, '%'))" , nativeQuery = true)
        List<Integer> findFullText(@Param("query") String query);
}
