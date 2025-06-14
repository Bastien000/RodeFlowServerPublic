package org.rodeflow.RodeFlowServer.service.user;

import com.google.firebase.auth.FirebaseAuthException;
import org.rodeflow.RodeFlowServer.dto.racer.RatingDTO;
import org.rodeflow.RodeFlowServer.dto.user.NewsDTO;
import org.rodeflow.RodeFlowServer.dto.user.PostDTO;
import org.rodeflow.RodeFlowServer.dto.user.PostReactionDTO;
import org.rodeflow.RodeFlowServer.dto.user.UserDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean addTestUser(String email, String userName, String firstName, String lastName);

    UserDTO getUser(Long userId, String authToken) throws FirebaseAuthException;

    List<UserDTO> getUsers();

    boolean updateUserData(Long userID, String authToken, UserDTO userDTO) throws FirebaseAuthException;

    boolean registerNewEmail(String email);

    boolean updateUserName(Long userID, String authToken,
                           String userName) throws FirebaseAuthException;

    boolean setNewPassword(String password, String email, int code) throws FirebaseAuthException;

    boolean deleteAccount(Long userID, String authToken) throws FirebaseAuthException;

    List<Integer> searchUser(String query);

    Integer uploadPostData(String authToken, Long userId, PostDTO postDTO) throws FirebaseAuthException, IOException;

    boolean deletePost(String authToken, Long userId, Long postID) throws FirebaseAuthException, IOException;

    boolean uploadProfilImage(String authToken, Long userId, MultipartFile image) throws FirebaseAuthException, IOException;

    Resource downloadProfilImage(Long userID) throws FirebaseAuthException;

    ResponseEntity<Resource> streamPostVideo (Long postID, Long userID,String authToken, HttpHeaders headers) throws FirebaseAuthException;

    boolean uploadPostImage(String authToken, Long userId, Long postId, MultipartFile image) throws FirebaseAuthException, IOException;

    Resource downloadPostImage(Long postID, Long userID, String authToken) throws FirebaseAuthException;

    PostDTO downloadPostData(Long postID, Long userID, String authToken) throws FirebaseAuthException;

    @Transactional
    boolean followUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException;

    @Transactional
    boolean unfollowUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException;

    @Transactional
    boolean acceptFollowUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException;

    @Transactional
    boolean deniFollowUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException;

    @Transactional
    boolean setPrivateProfil(Long userId, String authToken, boolean value) throws FirebaseAuthException;

    boolean addUserToCloseFriend(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException;

    boolean removeUserFromCloseFriend(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException;

    boolean isAllowedToPublicProfil(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException;

    boolean isAllowedToCloseFriend(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException;

    List<Integer> getFollowers(String authToken, String userID) throws FirebaseAuthException;

    List<Integer> getRequestedFollowers(String authToken, String userID) throws FirebaseAuthException;

    boolean addPostReaction(String userID, String authToken, PostReactionDTO postReaction) throws FirebaseAuthException;

    boolean removePostReaction(String userID, String authToken, Long postReactionID) throws FirebaseAuthException;

    PostReactionDTO getPostReaction(String userID, String authToken, Long postReactionID) throws FirebaseAuthException;

    boolean addRaceResultRating(String userID, String authToken, RatingDTO rating) throws FirebaseAuthException;

    boolean addRacerRating(String userID, String authToken, RatingDTO rating) throws FirebaseAuthException;

    RatingDTO getRacerRating(String userID, String authToken, Integer raceResultID) throws FirebaseAuthException;

    RatingDTO getRaceResultRating(String userID, String authToken, Integer raceResultID) throws FirebaseAuthException;

    boolean addCollectedRacerID(Long userID, String authToken, Integer racerID) throws FirebaseAuthException;

    boolean removeCollectedRacerID(Long userID, String authToken, Integer racerID) throws FirebaseAuthException;

    boolean addNews(String name, String description, String signature);

    NewsDTO getNewsByID(Long newsID);

    List<Integer> getNewsOrdered();
}
