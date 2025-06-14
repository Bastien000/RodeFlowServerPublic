package org.rodeflow.RodeFlowServer.repository.user;

import org.rodeflow.RodeFlowServer.entity.user.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long>, JpaSpecificationExecutor<NewsEntity> {

    @Query(nativeQuery = true , value = "SELECT newsid FROM news_entity ORDER BY timestamp DESC;")
    List<Integer> findNewsOrdered();

}