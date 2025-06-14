package org.rodeflow.RodeFlowServer.service.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.rodeflow.RodeFlowServer.dto.mapper.racer.RatingMapper;
import org.rodeflow.RodeFlowServer.dto.mapper.user.NewsMapper;
import org.rodeflow.RodeFlowServer.dto.mapper.user.PostMapper;
import org.rodeflow.RodeFlowServer.dto.mapper.user.PostReactionMapper;
import org.rodeflow.RodeFlowServer.dto.racer.RatingDTO;
import org.rodeflow.RodeFlowServer.dto.user.NewsDTO;
import org.rodeflow.RodeFlowServer.dto.user.PostDTO;
import org.rodeflow.RodeFlowServer.dto.user.PostReactionDTO;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RatingEntity;
import org.rodeflow.RodeFlowServer.entity.user.NewsEntity;
import org.rodeflow.RodeFlowServer.entity.user.PostEntity;
import org.rodeflow.RodeFlowServer.entity.user.PostReactionEntity;
import org.rodeflow.RodeFlowServer.repository.racer.RaceResultRepository;
import org.rodeflow.RodeFlowServer.repository.racer.RacerRepository;
import org.rodeflow.RodeFlowServer.repository.racer.RatingRepository;
import org.rodeflow.RodeFlowServer.repository.user.NewsRepository;
import org.rodeflow.RodeFlowServer.repository.user.PostReactionRepository;
import org.rodeflow.RodeFlowServer.repository.user.PostRepository;
import org.rodeflow.RodeFlowServer.repository.user.UserRepository;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;
import org.rodeflow.RodeFlowServer.dto.user.UserDTO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.rodeflow.RodeFlowServer.dto.mapper.user.UserMapper;
import org.rodeflow.RodeFlowServer.service.helper.EmailSenderService;
import org.rodeflow.RodeFlowServer.service.helper.HelperService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailSenderService senderService;
    private final HelperService helperService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final PostReactionRepository postReactionRepository;
    private final PostReactionMapper postReactionMapper;
    private  final RaceResultRepository raceResultRepository;
    private final RatingMapper ratingMapper;
    private final RacerRepository racerRepository;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    private final RatingRepository ratingRepository;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, EmailSenderService senderService, HelperService helperService, PostMapper postMapper, PostRepository postRepository, PostReactionRepository postReactionRepository, PostReactionMapper postReactionMapper, RaceResultRepository raceResultRepository, RatingMapper ratingMapper, RacerRepository racerRepository, NewsRepository newsRepository, NewsMapper newsMapper, RatingRepository ratingRepository){
        this.senderService =senderService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.helperService = helperService;
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.postReactionRepository = postReactionRepository;
        this.postReactionMapper = postReactionMapper;
        this.raceResultRepository = raceResultRepository;
        this.ratingMapper = ratingMapper;
        this.racerRepository = racerRepository;
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.ratingRepository = ratingRepository;
    }

    private boolean doesEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    private boolean doesUserNameExist(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }


    private boolean createFirebaseUser(String uid, String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest().setUid(uid).setEmail(email).setPassword(password);
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        return userRecord != null;
    }


    private boolean authToken(Long userId, String authToken) throws FirebaseAuthException {
        final FirebaseToken isAuth = FirebaseAuth.getInstance().verifyIdToken(authToken);
        return Integer.parseInt(isAuth.getUid()) == userId;
    }

    @Override
    public boolean addTestUser(String email, String userName, String firstName, String lastName){
        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
        return true;

    }

    @Override
    public UserDTO getUser(Long userId, String authToken) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId, authToken) && userEntity.isPresent()){
            return userMapper.toDTO(userEntity.get());
        }
        return null;
    }

    @Override
    public List<UserDTO> getUsers(){
        return userMapper.toDTO(userRepository.findAll());
    }



    @Override
    public boolean updateUserData(Long userID, String authToken, UserDTO userDTO) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userID);
        if(authToken(userID, authToken) && userEntity.isPresent()){
            userEntity.get().setFirstName(userDTO.getFirstName());
            userEntity.get().setLastName(userDTO.getLastName());
            userEntity.get().setBioOnProfil(userDTO.getBioOnProfil());
            userEntity.get().setPhoneNumber(userDTO.getPhoneNumber());
            userEntity.get().setGender(userDTO.getGender());
            userEntity.get().setNationality(userDTO.getNationality());
            userEntity.get().setInstagramLink(userDTO.getInstagramLink());
            userEntity.get().setStravaLink(userDTO.getStravaLink());
            userEntity.get().setGarminLink(userDTO.getGarminLink());
            userEntity.get().setBikeBrand(userDTO.getBikeBrand());
            userEntity.get().setHillsScore(userDTO.getHillsScore());
            userEntity.get().setSprintScore(userDTO.getSprintScore());
            userEntity.get().setMountainScore(userDTO.getMountainScore());
            userEntity.get().setIttScore(userDTO.getIttScore());
            userEntity.get().setGcScore(userDTO.getGcScore());
            userEntity.get().setClassicScore(userDTO.getClassicScore());
            userEntity.get().setDownHillScore(userDTO.getDownHillScore());
            userEntity.get().setCobblesScore(userDTO.getCobblesScore());
            userEntity.get().setRankingPoint(userDTO.getRankingPoint());
            userEntity.get().setWeight(userDTO.getWeight());
            userEntity.get().setHeight(userDTO.getHeight());
            userEntity.get().setBirth(userDTO.getBirth());
            userEntity.get().setProRider(userDTO.isProRider());
            userRepository.save(userEntity.get());
            return true;
        }
        return false;
    }


    @Override
    public boolean registerNewEmail(String email){
        if (!doesEmailExist(email)){
            int verificationCode = EmailSenderService.generateVerificationCode();
            UserEntity userEntity = new UserEntity();
            userEntity.setTimestamp(helperService.createTimestamp());
            userEntity.setEmail(email);
            userEntity.setVerificationCode(verificationCode);
            userEntity.setVerificated(false);
            userEntity.setProfilPrivate(true);
            userRepository.save(userEntity);
            senderService.sendEmail(email, "Verification code", "Your verification code for RodeFlow is:  " + verificationCode);
            return true;  // správně
        }
        return false; // chyba

    }


     @Override
     public boolean updateUserName(Long userID, String authToken,
             String userName) throws FirebaseAuthException {
         final Optional<UserEntity> userEntity = userRepository.findById(userID);
         if(userEntity.isPresent() && authToken(userID, authToken) && !doesUserNameExist(userName)) {
            userEntity.get().setUserName(userName);
            userRepository.save(userEntity.get());
            return true;
         }
         return false;

     }



    @Override
    public  boolean setNewPassword(String password, String email, int code) throws FirebaseAuthException {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(userEntity.isPresent() && userEntity.map(UserEntity::getVerificationCode).get() == code){
            if(createFirebaseUser(Long.toString(userEntity.get().getUserID()), email, password)){
                userEntity.get().setVerificated(true);
                userEntity.get().setVerificationCode(0);
                userRepository.save(userEntity.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteAccount(Long userID, String authToken) throws FirebaseAuthException {
        final Optional<UserEntity> userEntity = userRepository.findById(userID);
        if(userEntity.isPresent() && authToken(userID, authToken)){
            FirebaseAuth.getInstance().deleteUser(userID.toString());
            userRepository.delete(userEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> searchUser(String query){
       return userRepository.searchUser(query);
    }



    @Override
    public Integer uploadPostData(String authToken, Long userId, PostDTO postDTO) throws FirebaseAuthException, IOException {
        final Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId,authToken) && userEntity.isPresent()){
            PostEntity postEntity = postMapper.toEntity(postDTO);
            postEntity.setUserID(Math.toIntExact(userId));
            TimestampEntity timestamp = helperService.createTimestamp();
            postEntity.setTimestamp(timestamp);
            PostEntity savedPostEntity =  postRepository.save(postEntity);
            userEntity.get().addPost(savedPostEntity.getPostID());
            userRepository.save(userEntity.get());
            return savedPostEntity.getPostID();
        }
        return null;
    }

    @Override
    public boolean deletePost(String authToken, Long userId, Long postID) throws FirebaseAuthException, IOException {
        final Optional<UserEntity> userEntity = userRepository.findById(userId);
        final Optional<PostEntity> postEntity = postRepository.findById(postID);
        if(authToken(userId,authToken) && userEntity.isPresent() && postEntity.isPresent()){
            if(userEntity.get().getPostID().contains(Integer.valueOf(postID.toString())) && postEntity.get().getUserID().equals(Integer.valueOf(userId.toString()))){
                postRepository.delete(postEntity.get());
                userEntity.get().removePost(Integer.valueOf(postID.toString()));
                userRepository.save(userEntity.get());
                return true;
            }

        }
        return false;
    }


    @Override
    public boolean uploadProfilImage(String authToken, Long userId, MultipartFile image) throws FirebaseAuthException, IOException {
        final Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId,authToken) && userEntity.isPresent()){
            try {
                String jarDir = new File(System.getProperty("user.dir")).getAbsolutePath();
                String basePath = jarDir + File.separator + "uploads";
                String directoryPath = String.format("%s/%d/profilImage", basePath, userId);

                File directory = new File(directoryPath);
                if (!directory.exists() && !directory.mkdirs()) {
                    throw new IOException("Nepodařilo se vytvořit adresáře: " + directoryPath);
                }
                String originalFilename = image.getOriginalFilename();
                if (originalFilename == null) {
                    throw new IllegalArgumentException("Soubor nemá platný název.");
                }

                File destinationFile = new File(directory, originalFilename);
                image.transferTo(destinationFile);
                userEntity.get().setProfilImageName(originalFilename);
                userRepository.save(userEntity.get());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    @Override
    public Resource downloadProfilImage(Long userID) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userID);
        if (userEntity.isEmpty() || userEntity.get().getProfilImageName() == null || userEntity.get().getProfilImageName().isEmpty()) {
            return null;
        }
            try {
                    String basePath = new File(System.getProperty("user.dir")).getAbsolutePath() + File.separator + "uploads";
                    String filePath = String.format("%s/%d/profilImage/%s", basePath, userID, userEntity.get().getProfilImageName());

                    Path path = Paths.get(filePath);
                    Resource resource = new UrlResource(path.toUri());

                    if (!resource.exists() || !resource.isReadable()) {
                        return null;
                    }
                    return resource;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

    }


    // BETA
    @Override
    public ResponseEntity<Resource> streamPostVideo (Long postID, Long userID ,String authToken, HttpHeaders headers) throws FirebaseAuthException {
        if(!authToken(userID, authToken)){
            return null;
        }

        Optional<PostEntity> postEntity = postRepository.findById(postID);
        Optional<UserEntity> userEntity = userRepository.findById(userID);
        if (postEntity.isEmpty() || userEntity.isEmpty()) {
            return null;
        }
        Optional<UserEntity> targetUser = userRepository.findById(Long.valueOf(postEntity.get().getUserID()));

        if (targetUser.isEmpty()) {
            return null;
        }
        if(Math.toIntExact(userID) == (targetUser.get().getUserID())){
            try {
                String basePath = new File(System.getProperty("user.dir")).getAbsolutePath() + File.separator + "uploads";
                String filePath = String.format("%s/%d/posts/%s", basePath, targetUser.get().getUserID(), postEntity.get().getFileName());

            Path path = Paths.get(filePath);
            Resource videoResource = new UrlResource(path.toUri());

            if (!videoResource.exists() || !videoResource.isReadable()) {
                return null;
            }

            long videoSize = videoResource.contentLength();
            HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().get(0);
            if (range == null) {
                InputStream videoStream = videoResource.getInputStream();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(videoSize))
                        .body(new InputStreamResource(videoStream));
            }

            long start = range.getRangeStart(videoSize);
            long end = range.getRangeEnd(videoSize);
            long length = end - start + 1;

            InputStream partialStream = videoResource.getInputStream();
            partialStream.skip(start);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(length))
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + videoSize)
                    .body(new InputStreamResource(partialStream));

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Nastala chyba při načítání
        }

        }
        if(isAllowedToPublicProfil(authToken, userID, Long.valueOf(targetUser.get().getUserID()))){
            if(!postEntity.get().isCloseFriend() || (isAllowedToCloseFriend(authToken, userID, Long.valueOf(targetUser.get().getUserID())))) {

                try {
                    String basePath = new File(System.getProperty("user.dir")).getAbsolutePath() + File.separator + "uploads";
                    String filePath = String.format("%s/%d/posts/%s", basePath, targetUser.get().getUserID(), postEntity.get().getFileName());

                    Path path = Paths.get(filePath);
                    Resource videoResource = new UrlResource(path.toUri());

                    if (!videoResource.exists() || !videoResource.isReadable()) {
                        return null;
                    }

                    long videoSize = videoResource.contentLength();
                    HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().get(0);
                    if (range == null) {
                        InputStream videoStream = videoResource.getInputStream();
                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(videoSize))
                                .body(new InputStreamResource(videoStream));
                    }

                    long start = range.getRangeStart(videoSize);
                    long end = range.getRangeEnd(videoSize);
                    long length = end - start + 1;

                    InputStream partialStream = videoResource.getInputStream();
                    partialStream.skip(start);

                    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                            .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                            .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(length))
                            .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + videoSize)
                            .body(new InputStreamResource(partialStream));

                } catch (Exception e) {
                    e.printStackTrace();
                    return null; // Nastala chyba při načítání
                }

            }}



    return null;
    }

    @Override
    public boolean uploadPostImage(String authToken, Long userId, Long postId, MultipartFile image) throws FirebaseAuthException, IOException {
        final Optional<UserEntity> userEntity = userRepository.findById(userId);
        final Optional<PostEntity> postEntity = postRepository.findById(postId);
        if(authToken(userId,authToken) && userEntity.isPresent() && postEntity.isPresent() && postEntity.get().getUserID() == Math.toIntExact(userId) && userEntity.get().getPostID().contains(Math.toIntExact(postId))){
            try {
                // Získání relativní cesty vedle JAR souboru
                String jarDir = new File(System.getProperty("user.dir")).getAbsolutePath();
                String basePath = jarDir + File.separator + "uploads";

                String directoryPath = String.format("%s/%d/posts", basePath, userId);

                File directory = new File(directoryPath);
                if (!directory.exists() && !directory.mkdirs()) {
                    throw new IOException("Nepodařilo se vytvořit adresáře: " + directoryPath);
                }

                // Určení názvu souboru
                String originalFilename = image.getOriginalFilename();
                if (originalFilename == null) {
                    throw new IllegalArgumentException("Soubor nemá platný název.");
                }

                File destinationFile = new File(directory, postId + originalFilename);

                // Uložení souboru
                image.transferTo(destinationFile);
                postEntity.get().setFileName(postId + originalFilename);
                postRepository.save(postEntity.get());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }



        }
        return false;
    }

    @Override
    public Resource downloadPostImage(Long postID, Long userID, String authToken) throws FirebaseAuthException {
        if(!authToken(userID, authToken)){
            return null;
        }
        Optional<PostEntity> postEntity = postRepository.findById(postID);
        Optional<UserEntity> userEntity = userRepository.findById(userID);
        if (postEntity.isEmpty() || userEntity.isEmpty()) {
            return null;
        }
        Optional<UserEntity> targetUser = userRepository.findById(Long.valueOf(postEntity.get().getUserID()));
        if (targetUser.isEmpty()) {
            return null;
        }
        if(isAllowedToPublicProfil(authToken, userID, Long.valueOf(targetUser.get().getUserID())) || (Math.toIntExact(userID) == (targetUser.get().getUserID()))){
            if(!postEntity.get().isCloseFriend() || (isAllowedToCloseFriend(authToken, userID, Long.valueOf(targetUser.get().getUserID())))) {
                try {
                    String basePath = new File(System.getProperty("user.dir")).getAbsolutePath() + File.separator + "uploads";
                    String filePath = String.format("%s/%d/posts/%s", basePath, targetUser.get().getUserID(), postEntity.get().getFileName());

                    Path path = Paths.get(filePath);
                    Resource resource = new UrlResource(path.toUri());

                    if (!resource.exists() || !resource.isReadable()) {
                        return null;
                    }

                    return resource;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null; // Nastala chyba při načítání
                }
            }

        }
        return  null;
    }

    @Override
    public PostDTO downloadPostData(Long postID, Long userID, String authToken) throws FirebaseAuthException {
        if(!authToken(userID, authToken)){
            return null;
        }
        Optional<PostEntity> postEntity = postRepository.findById(postID);
        Optional<UserEntity> userEntity = userRepository.findById(userID);
        if (postEntity.isEmpty() || userEntity.isEmpty()) {
            return null;
        }
        Integer targetUserID = userRepository.findUserByPost(postID);
        Optional<UserEntity> targetUser = userRepository.findById(Long.valueOf(targetUserID));
        if (targetUser.isEmpty()) {
            return null;
        }
        if(Math.toIntExact(userID) == (targetUser.get().getUserID())){
            return  postMapper.toDTO(postEntity.get());
        }

        if(isAllowedToPublicProfil(authToken, userID, Long.valueOf(targetUser.get().getUserID()))){
            if(!postEntity.get().isCloseFriend() || (isAllowedToCloseFriend(authToken, userID, Long.valueOf(targetUser.get().getUserID())))) {
                return  postMapper.toDTO(postEntity.get());

            }
        }
        return  null;
    }

    @Override
    @Transactional
    public boolean followUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            if(!targetUserEntity.get().isProfilPrivate()){
                userEntity.get().addFollowing(Math.toIntExact(targetUserId));
                userRepository.save(userEntity.get());
                return true;
            }
            userEntity.get().addRequestedFollowing(Math.toIntExact(targetUserId));
            userRepository.save(userEntity.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean unfollowUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            if(userEntity.get().getRequestedFollowingID().contains(Integer.parseInt(userId.toString()))){
                userEntity.get().removeRequestedFollowing(Math.toIntExact(targetUserId));
                userRepository.save(userEntity.get());
                return true;
            }
            userEntity.get().removeFollowing(Math.toIntExact(targetUserId));
            userRepository.save(userEntity.get());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean acceptFollowUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            if(targetUserEntity.get().getRequestedFollowingID().contains(Integer.parseInt(userId.toString()))){
                targetUserEntity.get().removeRequestedFollowing(Integer.parseInt(userId.toString()));
                targetUserEntity.get().addFollowing(Integer.parseInt(userId.toString()));
                userRepository.save(targetUserEntity.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deniFollowUser(Long userId, String authToken, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            if(targetUserEntity.get().getRequestedFollowingID().contains(Integer.parseInt(userId.toString()))){
                targetUserEntity.get().removeRequestedFollowing(Integer.parseInt(userId.toString()));
                userRepository.save(targetUserEntity.get());
            return true;
            }
            if(targetUserEntity.get().getFollowingID().contains(Integer.parseInt(userId.toString()))){
                targetUserEntity.get().removeFollowing(Integer.parseInt(userId.toString()));
                userRepository.save(targetUserEntity.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public boolean setPrivateProfil(Long userId, String authToken, boolean value) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(authToken(userId, authToken) && userEntity.isPresent()){
            if (!value && userEntity.get().isProfilPrivate()) {
                userEntity.get().setProfilPrivate(false);
                List<Integer> requestedFollowers = userRepository.findRequestedFollowers(userId);
                for(Integer targetUserID : requestedFollowers){
                    Optional<UserEntity> targetUserEntity = userRepository.findById(Long.valueOf(targetUserID));
                    if(targetUserEntity.isPresent()){
                        targetUserEntity.get().removeRequestedFollowing(Math.toIntExact(userId));
                        targetUserEntity.get().addFollowing(Math.toIntExact(userId));
                        userRepository.save(targetUserEntity.get());
                    }
                }
                userRepository.save(userEntity.get());
            } else if(value && !userEntity.get().isProfilPrivate()) {
                userEntity.get().setProfilPrivate(true);
                List<Integer> followers = userRepository.findFollowers(userId);
                for(Integer targetUserID : followers){
                    Optional<UserEntity> targetUserEntity = userRepository.findById(Long.valueOf(targetUserID));
                    if(targetUserEntity.isPresent()){
                        targetUserEntity.get().removeFollowing(Math.toIntExact(userId));
                        targetUserEntity.get().addRequestedFollowing(Math.toIntExact(userId));
                        userRepository.save(targetUserEntity.get());
                    }
                }
                userRepository.save(userEntity.get());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addUserToCloseFriend(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);
        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            userEntity.get().addCloseFriend(Math.toIntExact(targetUserId));
            userRepository.save(userEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUserFromCloseFriend(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);
        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            userEntity.get().removeCloseFriend(Math.toIntExact(targetUserId));
            userRepository.save(userEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean isAllowedToPublicProfil(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);
        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            if(userId.equals(targetUserId)){
                return true;
            }
            if(!targetUserEntity.get().isProfilPrivate()){
                return true;
            }
            if(userEntity.get().getFollowingID().contains(Integer.parseInt(targetUserId.toString()))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAllowedToCloseFriend(String authToken, Long userId, Long targetUserId) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<UserEntity> targetUserEntity = userRepository.findById(targetUserId);

        if(authToken(userId, authToken) && userEntity.isPresent() && targetUserEntity.isPresent()){
            if(userId.equals(targetUserId)){
                return true;
            }
            if(targetUserEntity.get().getCloseFriendID().contains(Integer.parseInt(userId.toString()))){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Integer> getFollowers(String authToken, String userID) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent()){

            return userRepository.findFollowers(Long.valueOf(userID));
        }
        return null;
    }

    @Override
    public List<Integer> getRequestedFollowers(String authToken, String userID) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent()){
            return userRepository.findRequestedFollowers(Long.valueOf(userID));
        }
        return null;
    }

    @Override
    public boolean addPostReaction(String userID, String authToken, PostReactionDTO postReaction) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        PostReactionEntity postReactionEntity  = postReactionMapper.toEntity(postReaction);
        Optional<PostEntity> postEntity = postRepository.findById(Long.valueOf(postReactionEntity.getPostID()));
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent() && postEntity.isPresent()){
            postReactionEntity.setUserID(Integer.valueOf(userID));
            Optional<UserEntity> targetUserEntity = userRepository.findById(Long.valueOf(postEntity.get().getUserID()));
            if(targetUserEntity.isEmpty()){
                return false;
            }
            if(isAllowedToPublicProfil(authToken, Long.valueOf(userID), Long.valueOf(targetUserEntity.get().getUserID()))){
                if(!postEntity.get().isCloseFriend() || (isAllowedToCloseFriend(authToken, Long.valueOf(userID), Long.valueOf(targetUserEntity.get().getUserID())))) {
                    PostReactionEntity result = postReactionRepository.save(postReactionEntity);
                    postEntity.get().addPostReaction(result.getPostReactionID());
                    postRepository.save(postEntity.get());
                    return true;

                }
            }
        }
        return false;
    }

    @Override
    public boolean removePostReaction(String userID, String authToken, Long postReactionID) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        Optional<PostReactionEntity> postReactionEntity = postReactionRepository.findById(postReactionID);
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent() && postReactionEntity.isPresent()){
            Optional<PostEntity> post = postRepository.findById(Long.valueOf(postReactionEntity.get().getPostID()));
            if(post.isEmpty()){
                return  false;
            }
            Optional<UserEntity> targetUser = userRepository.findById(Long.valueOf(post.get().getUserID()));
            if(targetUser.isEmpty()){
                return false;
            }
            if(postReactionEntity.get().getUserID() != Integer.parseInt(userID)){
                return false;
            }
            if(isAllowedToPublicProfil(authToken, Long.valueOf(userID), Long.valueOf(targetUser.get().getUserID()))){
                if(!post.get().isCloseFriend() || (isAllowedToCloseFriend(authToken, Long.valueOf(userID), Long.valueOf(targetUser.get().getUserID())))) {
                    postReactionRepository.delete(postReactionEntity.get());
                    post.get().removePostReaction(Math.toIntExact(postReactionID));
                    postRepository.save(post.get());
                    return true;

                }
            }
        }
        return false;
    }

    @Override
    public PostReactionDTO getPostReaction(String userID, String authToken, Long postReactionID) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        Optional<PostReactionEntity> postReactionEntity = postReactionRepository.findById(postReactionID);

        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent() && postReactionEntity.isPresent()){
            Optional<PostEntity> postEntity = postRepository.findById(Long.valueOf(postReactionEntity.get().getPostID()));
            if(postEntity.isEmpty()){
                return null;
            }
            Optional<UserEntity> targetUserEntity = userRepository.findById(Long.valueOf(postEntity.get().getUserID()));
            if(targetUserEntity.isEmpty()){
                return null;
            }
            if(isAllowedToPublicProfil(authToken, Long.valueOf(userID), Long.valueOf(targetUserEntity.get().getUserID()))){
                if(!postEntity.get().isCloseFriend() || (isAllowedToCloseFriend(authToken, Long.valueOf(userID), Long.valueOf(targetUserEntity.get().getUserID())))){
                    return postReactionMapper.toDTO(postReactionEntity.get());

                }
            }

        }
        return null;
    }


    @Override
    public boolean addRaceResultRating(String userID, String authToken, RatingDTO rating) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        Optional<RaceResultEntity> raceResultEntity = raceResultRepository.findById(Long.valueOf(rating.getTargetID()));
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent() && raceResultEntity.isPresent()){
           List<RatingEntity> entity =  ratingRepository.findByUserID(Integer.valueOf(userID));
            for(RatingEntity ratingEntity : entity){
                if(ratingEntity.getRatingID().equals(rating.getRatingID())){
                    ratingEntity.setValue(rating.getValue());
                    ratingRepository.save(ratingEntity);
                    return true;
                }
            }


            RatingEntity ratingEntity = ratingRepository.save(ratingMapper.toEntity(rating));
            raceResultEntity.get().addRating(ratingEntity.getRatingID());
            raceResultRepository.save(raceResultEntity.get());
            return true;
        }
        return  false;
    }
    @Override
    public boolean addRacerRating(String userID, String authToken, RatingDTO rating) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        Optional<RacerEntity> racer = racerRepository.findById(Long.valueOf(rating.getTargetID()));
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent() && racer.isPresent()){
            List<RatingEntity> entity =  ratingRepository.findByUserID(Integer.valueOf(userID));
            for(RatingEntity ratingEntity : entity){
                if(ratingEntity.getRatingID().equals(rating.getRatingID())){
                    ratingEntity.setValue(rating.getValue());
                    ratingRepository.save(ratingEntity);
                    return true;
                }
            }
            RatingEntity ratingEntity = ratingRepository.save(ratingMapper.toEntity(rating));
            racer.get().addRating(ratingEntity.getRatingID());
            racerRepository.save(racer.get());
            return true;
        }
        return  false;
    }

    @Override
    public RatingDTO getRacerRating(String userID, String authToken ,Integer racerID) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent()){
            List<RatingEntity> result = ratingRepository.findByUserID(Integer.valueOf(userID));
            for(RatingEntity rating : result){
                if(rating.getTargetID().equals(racerID)){
                    return ratingMapper.toDTO(rating);
                }
            }
        }
        return null;
    }

    @Override
    public RatingDTO getRaceResultRating(String userID, String authToken, Integer raceResultID) throws FirebaseAuthException {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userID));
        if(authToken(Long.valueOf(userID), authToken) && userEntity.isPresent()){
            List<RatingEntity> result = ratingRepository.findByUserID(Integer.valueOf(userID));
            for(RatingEntity rating : result){
                if(rating.getTargetID().equals(raceResultID)){
                    return ratingMapper.toDTO(rating);
                }
            }
        }
        return null;
    }

    @Override
    public boolean addCollectedRacerID(Long userID, String authToken, Integer racerID) throws FirebaseAuthException {
        Optional<UserEntity> user = userRepository.findById(userID);
        if(authToken(userID, authToken) && user.isPresent()){
            if(!user.get().getCollectedRacerID().contains(racerID)){
                user.get().addCollectedRacer(racerID);
                userRepository.save(user.get());
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean removeCollectedRacerID(Long userID, String authToken, Integer racerID) throws FirebaseAuthException {
        Optional<UserEntity> user = userRepository.findById(userID);
        if(authToken(userID, authToken) && user.isPresent()){
            if(user.get().getCollectedRacerID().contains(racerID)){
                user.get().removeCollectedRacer(racerID);
                userRepository.save(user.get());

            }
            return true;
        }
        return false;
    }
    @Override
    public boolean addNews(String name, String description, String signature){
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setName(name);
        newsEntity.setDescription(description);
        newsEntity.setSignature(signature);
        newsEntity.setTimestamp(LocalDateTime.now());
        newsRepository.save(newsEntity);
        return true;
    }

    @Override
    public NewsDTO getNewsByID(Long newsID){
        Optional<NewsEntity> newsEntity = newsRepository.findById(newsID);
        if(newsEntity.isPresent()){
            return newsMapper.toDTO(newsEntity.get());
        }

        return null;
    }
     @Override
    public List<Integer> getNewsOrdered(){
        return newsRepository.findNewsOrdered();
    }

}
