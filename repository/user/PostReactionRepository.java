package org.rodeflow.RodeFlowServer.repository.user;


import org.rodeflow.RodeFlowServer.entity.user.PostEntity;
import org.rodeflow.RodeFlowServer.entity.user.PostReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReactionEntity, Long>, JpaSpecificationExecutor<PostReactionEntity> {


}
