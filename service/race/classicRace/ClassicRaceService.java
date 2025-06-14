package org.rodeflow.RodeFlowServer.service.race.classicRace;

import org.springframework.core.io.Resource;

public interface ClassicRaceService {
    void createClassicRaceFromTxt();

    Resource getRaceImageByID(Long classicRaceID);
}
