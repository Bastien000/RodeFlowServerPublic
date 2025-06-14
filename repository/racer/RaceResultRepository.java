package org.rodeflow.RodeFlowServer.repository.racer;

import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceResultRepository extends JpaRepository<RaceResultEntity, Long>, JpaSpecificationExecutor<RaceResultEntity>  {
        List<RaceResultEntity> findByRacerID(Integer racerEntity);
        @Query(value = "SELECT rre.* FROM race_result_entity rre JOIN classic_race_race_result_id crr ON rre.race_resultid = crr.race_resultid JOIN classic_race_entity cre ON crr.classic_raceid = cre.classic_raceid WHERE cre.classic_raceid = :classicraceid AND rre.finished_at IS NOT NULL ORDER BY ABS(TIMESTAMPDIFF(SECOND, cre.started_at, rre.finished_at)) ASC LIMIT 1", nativeQuery = true)
        RaceResultEntity findWinnerByClassicRaceID(@Param("classicraceid") Integer classicRaceID);
}
