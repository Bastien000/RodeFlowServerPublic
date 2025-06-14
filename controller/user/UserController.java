package org.rodeflow.RodeFlowServer.controller.user;

import com.google.firebase.auth.FirebaseAuthException;
import org.apache.tika.Tika;
import org.rodeflow.RodeFlowServer.dto.mapper.user.UserMapper;
import org.rodeflow.RodeFlowServer.dto.racer.RatingDTO;
import org.rodeflow.RodeFlowServer.dto.user.*;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;
import org.rodeflow.RodeFlowServer.repository.user.UserRepository;
import org.rodeflow.RodeFlowServer.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final static  List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "video/mp4", "video/webm", "image/heic", "image/jpg");
    private final static List<String> TYPES = Arrays.asList(
            "upload.jpeg",
            "upload.jpg",
            "upload.png",
            "upload.gif",
            "upload.bmp",
            "upload.webp",
            "upload.mp4",
            "upload.mpeg");

    public UserController(UserService userService, UserMapper userMapper, UserRepository userRepository){
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    @PostMapping("/auth/registerEmail")
    public ResponseEntity<String> registerNewEmail(@RequestHeader String email){
        System.out.println("Auth register email: " + email);
        if(userService.registerNewEmail(email)){
            return ResponseEntity.ok().body("Verification code was send to your email.");
        }
        return ResponseEntity.badRequest().body("Email already registered.");
    }
/* EndPoint na kontrolu při testování
    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }
*/
    @GetMapping("/getUser/{userID}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("getUser userID: " + userID.toString());
            return ResponseEntity.ok().body(userService.getUser(userID, authToken));
    }


    @PostMapping("/auth/registerPassword")
    public ResponseEntity<String> setNewPassword(@RequestHeader String password,@RequestHeader String email, @RequestHeader int code) throws FirebaseAuthException {
        System.out.println("auth registerPassword: " + email);
        if(userService.setNewPassword(password, email, code)){
            return ResponseEntity.ok().body("Your password has been set.");
        }
        return ResponseEntity.badRequest().body("Bad verification code.");
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestHeader Long userID,@RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("deleteAccoutn: " + userID.toString());
        if(userService.deleteAccount(userID, authToken)){
            return ResponseEntity.ok().body("Good bye");
        }
        return ResponseEntity.badRequest().body("Not today :)");
    }

    @GetMapping("/searchUser/{query}")
    public ResponseEntity<List<Integer>> searchUser(@PathVariable String query){
        System.out.println("SearchUser :" + query);
        return ResponseEntity.ok().body(userService.searchUser(query));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("UpdateUser , userID + UserDTO: " + userID.toString() +"\n" + userDTO.toString());
        if(userService.updateUserData(userID, authToken, userDTO)){
            return ResponseEntity.ok().body("Data updated");
        }
        return ResponseEntity.badRequest().body("Bad data format");

    }

    @PostMapping("/uploadProfilImage")
    public ResponseEntity<String> uploadProfilImage(@RequestParam("image") MultipartFile file,
                                                    @RequestHeader String authToken, @RequestHeader Long userId)
            throws IOException, FirebaseAuthException {
        System.out.println("UploadPRofilImage: "+ userId.toString());
        String name = file.getOriginalFilename();
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());
        if (name == null || !TYPES.contains(name)) {
            return ResponseEntity.badRequest().body("Bad file type");
        }
        if (mimeType == null || !allowedMimeTypes.contains(mimeType)) {
            return ResponseEntity.badRequest().body("Invalid MIME type for " + mimeType);
        }
        boolean result = userService.uploadProfilImage(authToken, userId, file);
        if (result) {
            System.out.println("ok");
            return ResponseEntity.ok().body("Profil image uploaded");
        }
        System.out.println("grr");

        return ResponseEntity.badRequest().body("Profil image has not been uploaded");
    }

    @GetMapping("/downloadProfilImage/{userID}")
    public ResponseEntity<Resource> downloadProfilImage(@PathVariable Long userID) throws FirebaseAuthException {
        System.out.println("downloadProfilImage: " + userID.toString());
        Resource imageResource = userService.downloadProfilImage(userID);
        System.out.println(imageResource.getFilename());
        if(imageResource == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"")
                .body(imageResource);
    }

    @PostMapping("/deletePost/{postID}")
    public ResponseEntity<String> deletePost(@PathVariable Long postID, @RequestHeader String authToken, @RequestHeader Long userID) throws IOException, FirebaseAuthException {
        System.out.println("Delete Post: "+ userID.toString());
        if(userService.deletePost(authToken, userID, postID)){
            return ResponseEntity.ok().body("Post removed");
        }
        return ResponseEntity.badRequest().body("Post has not been removed.");
    }

    @PostMapping("/uploadPostData")
    public ResponseEntity<Integer> uploadPostData(@RequestBody PostDTO postDTO, @RequestHeader String authToken, @RequestHeader Long userID) throws IOException, FirebaseAuthException {
        System.out.println("UploadPostData userID, postDTO: " +userID.toString() + "\n" + postDTO.toString());
        Integer result = userService.uploadPostData(authToken, userID, postDTO);
        if(result == null){
            return ResponseEntity.badRequest().body(400);
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/uploadPostImage/{postID}")
    public ResponseEntity<String> uploadPostImage(@RequestParam("image") MultipartFile file, @RequestHeader String authToken, @RequestHeader Long userId, @PathVariable Long postID) throws IOException, FirebaseAuthException {
        System.out.println("UploadPostImage , userID, postID: " + userId.toString() + postID.toString());
        String name = file.getOriginalFilename();
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());
        if (name == null || !TYPES.contains(name)) {
            return ResponseEntity.badRequest().body("Bad file type");
        }
        if (mimeType == null || !allowedMimeTypes.contains(mimeType)) {
            return ResponseEntity.badRequest().body("Invalid MIME type for " + mimeType);
        }

        boolean result = userService.uploadPostImage(authToken, userId,postID ,file);
        if(result){
            return ResponseEntity.ok().body("Post image uploaded");
        }
        return ResponseEntity.badRequest().body("Post has not been uploaded");
    }

    @GetMapping("/downloadPostImage/{postID}")
    public ResponseEntity<Resource> getPostImage(@PathVariable Long postID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("DownloadPostImage, userID, postID: " + userID.toString()  + postID.toString());
        Resource imageResource = userService.downloadPostImage(postID, userID, authToken);
        if(imageResource == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"")
                .body(imageResource);
    }

    @GetMapping("/streamPostVideo/{postID}")  // EndPoint v Beta versi
    public ResponseEntity<Resource> streamPostVideo(@RequestHeader Long userID ,
                                                    @PathVariable Long postID, @RequestHeader String authToken ,
                                                    @RequestHeader HttpHeaders headers)
            throws IOException, FirebaseAuthException {
        System.out.println("StreamPostVideo BETA, userID, postID: " + userID.toString()  + postID.toString());
        return  userService.streamPostVideo(postID, userID, authToken,  headers);
    }

    @GetMapping("/downloadPostData/{postID}")
    public ResponseEntity<PostDTO> getPostData(@PathVariable Long postID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("DownloadPostData , userID, postID: " + userID.toString() + postID.toString());
        PostDTO postDTO = userService.downloadPostData(postID, userID, authToken);
        if(postDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/getPrivateProfil/{targetUserID}")
    public ResponseEntity<?> getPrivateProfil(@PathVariable Long targetUserID) throws  FirebaseAuthException {
        System.out.println("GetPrivateProfil , targetUSerID: " + targetUserID.toString());
        Optional<UserEntity> user = userRepository.findById(targetUserID);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }
        PrivateProfilDTO privateProfilDTO = userMapper.toPrivateProfilDTO(user.get());
        privateProfilDTO.setType("private");
        return ResponseEntity.ok().body(privateProfilDTO);
    }


    @GetMapping("/getUserProfil/{targetUserID}")
    public ResponseEntity<?> getUserProfil(@PathVariable Long targetUserID, @RequestHeader Long userID, @RequestHeader String authToken) throws  FirebaseAuthException {
        System.out.println("GerUserProfil , targetUserID: " + targetUserID.toString());
        Optional<UserEntity> user = userRepository.findById(targetUserID);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }
        if(userService.isAllowedToPublicProfil(authToken, userID, targetUserID)){
            PublicProfilDTO publicProfilDTO = userMapper.toPublicProfilDTO(user.get());
            publicProfilDTO.setType("public");
            return  ResponseEntity.ok().body(publicProfilDTO);
        }
        PrivateProfilDTO privateProfilDTO = userMapper.toPrivateProfilDTO(user.get());
        privateProfilDTO.setType("private");
        return ResponseEntity.ok().body(privateProfilDTO);
    }

    @PostMapping("/follow/{targetUserID}")
    public ResponseEntity<String> followUser(@PathVariable Long targetUserID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("Follow userID, targetUserID: " +userID.toString() + targetUserID.toString() );
        if(userService.followUser(userID, authToken , targetUserID)){
            return  ResponseEntity.ok().body("Follow successful");
        }
        return  ResponseEntity.badRequest().body("Follow rejected");
    }

    @PostMapping("/unfollow/{targetUserID}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long targetUserID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("UnFollow , UserID, targetUSerID: " + userID.toString() + targetUserID.toString());
        if(userService.unfollowUser(userID, authToken , targetUserID)){
            return  ResponseEntity.ok().body("Unfollow successful");
        }
        return  ResponseEntity.badRequest().body("Unfollow rejected");
    }

    @PostMapping("/acceptFollow/{targetUserID}")
    public ResponseEntity<String> acceptFollowUser(@PathVariable Long targetUserID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("AcceptFollow , UserID, targetUSerID: " + userID.toString() + targetUserID.toString());
        if(userService.acceptFollowUser(userID, authToken , targetUserID)){
            return  ResponseEntity.ok().body("Accept follow successful");
        }
        return  ResponseEntity.badRequest().body("Accept follow denied");
    }

    @PostMapping("/deniFollow/{targetUserID}")
    public ResponseEntity<String> deniFollowUser(@PathVariable Long targetUserID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("DeniFollow , UserID, targetUSerID: " + userID.toString() + targetUserID.toString());
        if(userService.deniFollowUser(userID, authToken , targetUserID)){
            return  ResponseEntity.ok().body("Deni follow successful");
        }
        return  ResponseEntity.badRequest().body("Deni follow denied");
    }

    @PostMapping("/addCloseFriend/{targetUserID}")
    public ResponseEntity<String> addCloseFriend(@PathVariable Long targetUserID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("AddCloseFriend , UserID, targetUSerID: " + userID.toString() + targetUserID.toString());
        if(userService.addUserToCloseFriend(authToken , userID, targetUserID)){
            return  ResponseEntity.ok().body("Close friend added successful");
        }
        return  ResponseEntity.badRequest().body("Close friend not added");
    }

    @PostMapping("removeCloseFriend/{targetUserID}")
    public ResponseEntity<String> removeCloseFriend(@PathVariable Long targetUserID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("RemoveCloseFriend , UserID, targetUSerID: " + userID.toString() + targetUserID.toString());
        if(userService.removeUserFromCloseFriend(authToken , userID, targetUserID)){
            return  ResponseEntity.ok().body("Close friend removed successful");
        }
        return  ResponseEntity.badRequest().body("Close friend not removed");
    }

    @PostMapping("setPrivateProfil/{value}")
    public ResponseEntity<String> setPrivateProfil(@PathVariable boolean value, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("setPrivateProfil , UserID: " + userID.toString());

        if(userService.setPrivateProfil(userID, authToken , value)){
            return  ResponseEntity.ok().body("Private profil set to" + value);
        }
        return  ResponseEntity.badRequest().body("Private profil setting not changed");
    }

    @PostMapping("updateUserName")
    public ResponseEntity<String> updateUserName(@RequestHeader String userName, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("UpdateUserName , UserID, userName: " + userID.toString() + userName);
        if(userService.updateUserName(userID, authToken , userName)){
            return  ResponseEntity.ok().body("UserName updated");
        }
        return  ResponseEntity.badRequest().body("UserName update failed");
    }

    @GetMapping("getFollowers")
    public ResponseEntity<List<Integer>> getFollowers(@RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("GetFollowers , UserID: " + userID);
        List<Integer> result = userService.getFollowers(authToken, userID);
        if(result==null) {
            return  ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("getRequestedFollowers")
    public ResponseEntity<List<Integer>> getRequestedFollowers(@RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("GetRequestedFollowers , UserID: " + userID);
        List<Integer> result = userService.getRequestedFollowers(authToken, userID);
        if(result==null) {
            return  ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("addPostReaction")
    public ResponseEntity<String> addPostReaction(@RequestBody PostReactionDTO postDTO, @RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("AddPostReaction , UserID, postreactionDTO: " + userID + " " + postDTO.toString());
        if(userService.addPostReaction(userID, authToken, postDTO)){
            return ResponseEntity.ok().body("Post reaction added");
        }
        return ResponseEntity.badRequest().body("Post reaction not added");
    }

    @PostMapping("removePostReaction/{postReactionID}")
    public ResponseEntity<String> removePostReaction(@PathVariable Long postReactionID, @RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("AddPostReaction , UserID, postreactionID: " + userID + " " + postReactionID.toString());
        if(userService.removePostReaction(userID, authToken, postReactionID)){
            return ResponseEntity.ok().body("Post reaction removed");
        }
        return ResponseEntity.badRequest().body("Post reaction not removed");
    }


    @GetMapping("getPostReaction/{postReactionID}")
    public ResponseEntity<PostReactionDTO> getPostReaction(@RequestHeader String userID, @RequestHeader String authToken, @PathVariable Long postReactionID) throws FirebaseAuthException {
        System.out.println("GetPostReaction , UserID, postreactionID: " + userID + " " + postReactionID.toString());
        PostReactionDTO result = userService.getPostReaction(userID, authToken, postReactionID);
        if( result!= null){
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("addRacerRating")
    public ResponseEntity<String> addRacerRating(@RequestBody RatingDTO ratingDTO, @RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("AddRacerRating , UserID: " + userID);
        if(userService.addRacerRating(userID, authToken, ratingDTO)){
            return ResponseEntity.ok().body("Rating added");
        }
        return ResponseEntity.badRequest().body("Ratting not added");
    }

    @PostMapping("addRaceResultRating")
    public ResponseEntity<String> addRaceResultRating(@RequestBody RatingDTO ratingDTO, @RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("addRaceResultRating , UserID, ratingDTO: " + userID + "\n"+ ratingDTO.toString());
        if(userService.addRaceResultRating(userID, authToken, ratingDTO)){
            return ResponseEntity.ok().body("Rating added");
        }
        return ResponseEntity.badRequest().body("Rating not added");
    }

    @GetMapping("getRacerRating/{racerID}")
    public ResponseEntity<RatingDTO> getRacerRating(@PathVariable Integer racerID, @RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("GetRacerRating racerID, userID:" + racerID.toString() + " " + userID);
        RatingDTO result = userService.getRacerRating(userID, authToken, racerID);
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("getRaceResultRating/{raceResultID}")
    public ResponseEntity<RatingDTO> getRaceResultRating(@PathVariable Integer raceResultID, @RequestHeader String userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("getRaceResultRating userID, raceResultID: " + userID + " " +  raceResultID.toString());
        RatingDTO result = userService.getRaceResultRating(userID, authToken, raceResultID);
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("addCollectedRacer/{racerID}")
    public ResponseEntity<String> addCollectedRacer(@PathVariable Integer racerID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("addCollectedRacer userID, racerID: " + userID + " " +  racerID.toString());
        if(userService.addCollectedRacerID(userID, authToken, racerID)){
            return ResponseEntity.ok().body("Racer added");
        }
        return ResponseEntity.badRequest().body("Racer not added");
    }

    @PostMapping("removeCollectedRacer/{racerID}")
    public ResponseEntity<String> removeCollectedRacer(@PathVariable Integer racerID, @RequestHeader Long userID, @RequestHeader String authToken) throws FirebaseAuthException {
        System.out.println("removeCollectedRacer userID, racerID: " + userID.toString() + " " + racerID.toString());
        if(userService.removeCollectedRacerID(userID, authToken, racerID)){
            return ResponseEntity.ok().body("Racer removed");
        }
        return ResponseEntity.badRequest().body("Racer not removed");
    }
}
