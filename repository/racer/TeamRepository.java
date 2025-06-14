package org.rodeflow.RodeFlowServer.repository.racer;

import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;
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
public interface TeamRepository extends JpaRepository<TeamEntity, Long>, JpaSpecificationExecutor<TeamEntity>  {

    Optional<TeamEntity> findByName(String name);

    @Query(value = "SELECT c.teamid FROM team_entity c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))" , nativeQuery = true)
    List<Integer> findFullText(@Param("query") String query);

    @Query(value = "SELECT team_entity.teamid FROM team_entity" , nativeQuery = true)
    Page<List<Integer>> findAllID (Pageable pageable);
}
