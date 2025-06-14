package org.rodeflow.RodeFlowServer.repository.user;

import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.user.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<RideEntity, Long>, JpaSpecificationExecutor<RacerEntity>  {


}

