package org.rodeflow.RodeFlowServer.repository.racer;

import org.rodeflow.RodeFlowServer.entity.racer.RatingEntity;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long>, JpaSpecificationExecutor<RatingEntity> {

        @Query(nativeQuery = true, value = "SELECT * FROM rating_entity WHERE rating_entity.userID = :userID;")
        List<RatingEntity> findByUserID(@Param("userID") Integer userID);

}