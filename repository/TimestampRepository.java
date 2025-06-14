package org.rodeflow.RodeFlowServer.repository;

import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TimestampRepository extends JpaRepository<TimestampEntity, Long>, JpaSpecificationExecutor<TimestampEntity>  {


}

