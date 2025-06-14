package org.rodeflow.RodeFlowServer.controller;

import org.rodeflow.RodeFlowServer.service.race.classicRace.ClassicRaceService;
import org.rodeflow.RodeFlowServer.service.racer.RacerService;
import org.rodeflow.RodeFlowServer.service.team.TeamService;
import org.rodeflow.RodeFlowServer.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class testController {

    @Autowired
    private final TeamService teamService;
    private final RacerService racerService;
    private final ClassicRaceService classicRaceService;
    private static final String PASSWORD = "RodeFlowPasswordForDev";
    private final UserService userService;

    public testController(TeamService teamService, RacerService racerService, ClassicRaceService classicRaceService, UserService userService) {
        this.teamService = teamService;
        this.racerService = racerService;
        this.classicRaceService = classicRaceService;
        this.userService = userService;

    }

    @GetMapping("/writeClassicRaceInDatabase/{password}")
    public ResponseEntity<String> writeClassicRaceInDatabase(@PathVariable String password){
        if(password.equals(PASSWORD)){
            classicRaceService.createClassicRaceFromTxt();
            return  new ResponseEntity<>("Data written successfully", HttpStatus.OK);

        }
        return  new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/writeTeamsInDatabase/{password}")
    public ResponseEntity<String> writeTeamsInDatabase(@PathVariable String password){
        if(password.equals(PASSWORD)){
            teamService.createTeamFromTxt();
            return  new ResponseEntity<>("Data written successfully", HttpStatus.OK);
        }
        return  new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }


    @GetMapping("/writeRacersInDatabase/{password}")
    public ResponseEntity<String> writeRacersInDatabase(@PathVariable String password){
        if(password.equals(PASSWORD)){
            racerService.createRacerFromTxt();
            return  new ResponseEntity<>("Data written successfully", HttpStatus.OK);
        }
        return  new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/addTestUser/{password}/{email}/{userName}/{firstName}/{lastName}")
    public ResponseEntity<String> addTestUser(@PathVariable String password, @PathVariable String email, @PathVariable String userName, @PathVariable String firstName, @PathVariable String lastName){
        if(password.equals(PASSWORD)){
            if(userService.addTestUser(email,userName,firstName,lastName));
            {
                return ResponseEntity.ok("user created");
            }
        }
        return ResponseEntity.badRequest().body("Grrrr");
    }

    @GetMapping("/addNews/{password}/{name}/{description}/{signature}")
    public ResponseEntity<String> addNews(@PathVariable String password, @PathVariable String name, @PathVariable String description, @PathVariable String signature){
        if(password.equals(PASSWORD)){
            if(userService.addNews(name,description,signature));
            {
                return ResponseEntity.ok("user created");
            }
        }
        return ResponseEntity.badRequest().body("Grrrr");
    }

    @GetMapping("/helloword")
    public String helloWord(){
        return "Hello word from dev";
    }
}
