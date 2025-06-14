package org.rodeflow.RodeFlowServer.controller.racer;

import org.rodeflow.RodeFlowServer.dto.mapper.racer.ClassicRaceMapper;
import org.rodeflow.RodeFlowServer.dto.mapper.racer.RaceResultMapper;
import org.rodeflow.RodeFlowServer.dto.mapper.racer.RacerMapper;
import org.rodeflow.RodeFlowServer.dto.mapper.racer.TeamMapper;
import org.rodeflow.RodeFlowServer.dto.racer.ClassicRaceDTO;
import org.rodeflow.RodeFlowServer.dto.racer.RaceResultDTO;
import org.rodeflow.RodeFlowServer.dto.racer.RacerDTO;
import org.rodeflow.RodeFlowServer.dto.racer.TeamDTO;
import org.rodeflow.RodeFlowServer.dto.user.NewsDTO;
import org.rodeflow.RodeFlowServer.entity.racer.ClassicRaceEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;
import org.rodeflow.RodeFlowServer.repository.racer.ClassicRaceRepository;
import org.rodeflow.RodeFlowServer.repository.racer.RaceResultRepository;
import org.rodeflow.RodeFlowServer.repository.racer.RacerRepository;
import org.rodeflow.RodeFlowServer.repository.racer.TeamRepository;
import org.rodeflow.RodeFlowServer.service.race.classicRace.ClassicRaceService;
import org.rodeflow.RodeFlowServer.service.user.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.InputStream;


@RestController
public class RacerController {

    private final UserService userService;
    private final TeamRepository teamRepository;
    private final RacerRepository racerRepository;
    private final ClassicRaceRepository classicRaceRepository;
    private final RaceResultRepository raceResultRepository;
    private final TeamMapper teamMapper;
    private final RacerMapper racerMapper;
    private final ClassicRaceMapper classicRaceMapper;
    private final RaceResultMapper raceResultMapper;
    private static final String VIDEO_PATH = "classpath:testVideo.mp4";  // test video
    private final ResourceLoader resourceLoader;
    private final ClassicRaceService classicRaceService;

    public RacerController(UserService userService, TeamRepository teamRepository, RacerRepository racerRepository, ClassicRaceRepository classicRaceRepository, RaceResultRepository raceResultRepository, TeamMapper teamMapper, RacerMapper racerMapper, ClassicRaceMapper classicRaceMapper, RaceResultMapper raceResultMapper, ResourceLoader resourceLoader, ClassicRaceService classicRaceService) {
        this.userService = userService;
        this.teamRepository = teamRepository;
        this.racerRepository = racerRepository;
        this.classicRaceRepository = classicRaceRepository;
        this.raceResultRepository = raceResultRepository;
        this.teamMapper = teamMapper;
        this.racerMapper = racerMapper;
        this.classicRaceMapper = classicRaceMapper;
        this.raceResultMapper = raceResultMapper;
        this.resourceLoader = resourceLoader;
        this.classicRaceService = classicRaceService;
    }

    private static Iterable<Long> convertToIterableLong(int intValue) {
        Long longValue = (long) intValue;
        return Collections.singletonList(longValue);
    }

    public static <T> Page<T> paginateList(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> paginatedList = list.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, list.size());
    }

    @GetMapping("/stream")
    public ResponseEntity<Resource> streamVideo(@RequestHeader HttpHeaders headers) throws IOException {
        Resource videoResource = resourceLoader.getResource(VIDEO_PATH);
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
    }

    @GetMapping("getTeams")
    public ResponseEntity<List<TeamDTO>> getTeams() {
        System.out.println("getTeams");
        List<TeamDTO> teams = teamMapper.toDTO(teamRepository.findAll());
        return ResponseEntity.ok(teams);
    }

    @GetMapping("getTeamsID")
    public ResponseEntity<Page<List<Integer>>> getTeamsID(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        System.out.println("getTeamsID: " + "Page: " + page + "Size: " + size);
        Page<List<Integer>> teams = teamRepository.findAllID(pageable);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("getRacers")
    public ResponseEntity<List<RacerDTO>> getRacers() {
        System.out.println("getRacers");
        List<RacerDTO> racers = racerMapper.toDTO(racerRepository.findAll());
        return ResponseEntity.ok(racers);
    }

    @GetMapping("getRaceResult")
    public ResponseEntity<List<RaceResultDTO>> getRaceResult() {
        System.out.println("getRaceResult");
        List<RaceResultDTO> results = raceResultMapper.toDTO(raceResultRepository.findAll());
        return ResponseEntity.ok(results);
    }

    @GetMapping("getClassicRace")
    public ResponseEntity<List<ClassicRaceDTO>> getClassicRace() {
        System.out.println("getClassicRace");
        List<ClassicRaceDTO> races = classicRaceMapper.toDTO(classicRaceRepository.findAll());
        return ResponseEntity.ok(races);
    }

    @GetMapping("getRacer/{ID}")
    public ResponseEntity<RacerDTO> getRacers(@PathVariable(name = "ID") int racerID) {
        System.out.println("getRacer: " + racerID);
        List<RacerEntity> racerEntity = racerRepository.findAllById(convertToIterableLong(racerID));
        if (!racerEntity.isEmpty()) {
            return ResponseEntity.ok(racerMapper.toDTO(racerEntity.get(0)));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getRaceResult/{ID}")
    public ResponseEntity<RaceResultDTO> getRaceResult(@PathVariable(name = "ID") Long raceResultID) {
        System.out.println("getRaceResult: " + raceResultID);
        Optional<RaceResultEntity> raceResultEntity = raceResultRepository.findById(raceResultID);
        if (raceResultEntity.isPresent()) {
            System.out.println(raceResultEntity.get().getRaceResultID());
            RaceResultDTO raceResultDTO = raceResultMapper.toDTO(raceResultEntity.get());
            System.out.println("DTO " + raceResultDTO.getRaceResultID());
            return ResponseEntity.ok(raceResultDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getTeam/{ID}")
    public ResponseEntity<TeamDTO> getTeam(@PathVariable(name = "ID") int teamID) {
        System.out.println("getTeam: " + teamID);
        List<TeamEntity> teamEntity = teamRepository.findAllById(convertToIterableLong(teamID));
        if (!teamEntity.isEmpty()) {
            return ResponseEntity.ok(teamMapper.toDTO(teamEntity.get(0)));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getClassicRace/{ID}")
    public ResponseEntity<ClassicRaceDTO> getClassicRace(@PathVariable(name = "ID") Long classicRaceID) {
        System.out.println("getClassicRace: " + classicRaceID);
        Optional<ClassicRaceEntity> classicRaceEntity = classicRaceRepository.findById(classicRaceID);
        if (classicRaceEntity.isPresent()) {  // Changed from !isEmpty() to isPresent() for Optional
            return ResponseEntity.ok(classicRaceMapper.toDTO(classicRaceEntity.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getClassicRaceWithSort/{ID}")
    public ResponseEntity<Page<Integer>> getClassicRaceWithSort(@PathVariable(name = "ID") int classicRaceID,
                                                                @RequestParam int page,
                                                                @RequestParam int size) {
        System.out.println("getClassicRaceWithSort: " + classicRaceID);
        List<Integer> classicRaceEntity = classicRaceRepository.findByIDWithSort(classicRaceID);
        if (!classicRaceEntity.isEmpty()) {
            Pageable pageable = size == 0 ? PageRequest.of(0, classicRaceEntity.size()) : PageRequest.of(page, size);
            System.out.println(classicRaceEntity.toString());
            return ResponseEntity.ok(paginateList(classicRaceEntity, pageable));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getClassicRaceWinner/{ID}")
    public ResponseEntity<RaceResultDTO> getClassicRaceWinner(@PathVariable(name = "ID") int classicRaceID) {
        System.out.println("getClassicRaceWinner: " + classicRaceID);
        RaceResultEntity raceResultEntity = raceResultRepository.findWinnerByClassicRaceID(classicRaceID);
        if (raceResultEntity != null) {
            return ResponseEntity.ok(raceResultMapper.toDTO(raceResultEntity));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("findRacerByFullText/{query}")
    public ResponseEntity<List<Integer>> findRacerByFullText(@PathVariable(name = "query") String query) {
        System.out.println("findRacerByFullText: " + query);
        List<Integer> results = racerRepository.findFullText(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("findClassicRaceByFullText/{query}")
    public ResponseEntity<List<Integer>> findClassicRaceByFullText(@PathVariable(name = "query") String query) {
        System.out.println("findClassicRaceByFullText: " + query);
        List<Integer> results = classicRaceRepository.findFullText(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("findTeamByFullText/{query}")
    public ResponseEntity<List<Integer>> findTeamByFullText(@PathVariable(name = "query") String query) {
        System.out.println("findTeamByFullText: " + query);
        List<Integer> results = teamRepository.findFullText(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("getRaceResultByRacer/{ID}")
    public ResponseEntity<List<RaceResultDTO>> getRaceResultByRacer(@PathVariable(name = "ID") int racerID) {
        System.out.println("getRaceResultByRacer: " + racerID);
        List<RacerEntity> racerEntity = racerRepository.findAllById(convertToIterableLong(racerID));
        if (!racerEntity.isEmpty()) {
            List<RaceResultDTO> results = raceResultMapper.toDTO(raceResultRepository.findByRacerID(racerEntity.get(0).getRacerID()));
            return ResponseEntity.ok(results);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getClassicRaceByRaceResult/{ID}")
    public ResponseEntity<ClassicRaceDTO> getClassicRaceByRaceResult(@PathVariable(name = "ID") int raceResultID) {
        System.out.println("getClassicRaceByRaceResult: " + raceResultID);
        Optional<ClassicRaceEntity> classicRaceEntity = classicRaceRepository.findByRaceResultID(raceResultID);
        if (classicRaceEntity.isPresent()) {
            return ResponseEntity.ok(classicRaceMapper.toDTO(classicRaceEntity.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getClassicRaceByTeam/{ID}")
    public ResponseEntity<List<Integer>> getClassicRaceByTeam(@PathVariable(name = "ID") int teamID){
        System.out.println("getClassicRaceByTeam: " + teamID);
        return ResponseEntity.ok(classicRaceRepository.findClassicRaceIDsByTeamID(teamID));
    }

    @GetMapping("/getNewsBy/{newsID}")
    public ResponseEntity<NewsDTO> getNewsBy(@PathVariable Long newsID){
        return ResponseEntity.ok(userService.getNewsByID(newsID));

    }

    @GetMapping("/getNews")
    public ResponseEntity<List<Integer>> getNews(){
        return  ResponseEntity.ok(userService.getNewsOrdered());

    }
    @GetMapping("/getRaceImage/{imageID}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageID) {
        System.out.println("getRaceImage imageID: " + imageID.toString());
        Resource image = classicRaceService.getRaceImageByID(imageID);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}





