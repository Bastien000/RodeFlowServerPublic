package org.rodeflow.RodeFlowServer.repository.user;

import org.apache.catalina.User;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;
@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserName(String userName);

    @Query(value = "SELECT userid FROM user_entity c WHERE " + "LOWER(c.first_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " + "LOWER(c.last_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " + "LOWER(c.user_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " + "LOWER(CONCAT(c.first_name, ' ', c.last_name)) LIKE LOWER(CONCAT('%', :query, '%'))", nativeQuery = true)
    List<Integer> searchUser(@Param("query") String query);


    @Query(nativeQuery = true, value = "SELECT user_requested_following_id.userid FROM user_requested_following_id WHERE user_requested_following_id.target_userid = :userId;")
    List<Integer> findRequestedFollowers(@Param("userId") Long userId);

    @Query(nativeQuery = true , value = "SELECT user_following_id.userid FROM user_following_id WHERE user_following_id.target_userid = :userId;")
    List<Integer> findFollowers(@Param("userId") Long userId);

    @Query(nativeQuery = true , value = "SELECT userid FROM user_post_id WHERE postid = :postID;")
    Integer findUserByPost(@Param("postID") Long postID);




}
