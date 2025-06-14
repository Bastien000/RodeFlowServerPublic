package org.rodeflow.RodeFlowServer.repository.racer;

import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RacerRepository extends JpaRepository<RacerEntity, Long>, JpaSpecificationExecutor<RacerEntity> {
        Optional<RacerEntity> findByFirstNameAndLastNameAndTeamID(String firstName, String lastName, Integer team);


        @Query(value = "SELECT racerid FROM racer_entity c WHERE " + "LOWER(c.first_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " + "LOWER(c.last_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " + "LOWER(c.nick_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " + "LOWER(CONCAT(c.first_name, ' ', c.last_name)) LIKE LOWER(CONCAT('%', :query, '%'))", nativeQuery = true)

        List<Integer> findFullText(@Param("query") String query);
}
