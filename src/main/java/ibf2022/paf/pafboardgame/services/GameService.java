package ibf2022.paf.pafboardgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.paf.pafboardgame.repositories.GameRepo;
import jakarta.json.JsonObject;

@Service
public class GameService {

    @Autowired
    GameRepo gameRepo;

    public JsonObject findGamesByName(Integer limit, Integer offset) {
        return gameRepo.findGamesByName(limit, offset);
    }
}
