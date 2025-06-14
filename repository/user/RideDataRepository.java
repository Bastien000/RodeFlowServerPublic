package org.rodeflow.RodeFlowServer.repository.user;

import org.rodeflow.RodeFlowServer.entity.user.RideDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RideDataRepository extends JpaRepository<RideDataEntity, Long>, JpaSpecificationExecutor<RideDataEntity>  {


}
