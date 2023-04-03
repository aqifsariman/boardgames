package ibf2022.paf.pafboardgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.paf.pafboardgame.services.GameService;
import jakarta.json.JsonObject;

@RestController
@RequestMapping
public class GameRestController {
    @Autowired
    GameService gameSVC;

    @GetMapping("/games")
    public ResponseEntity<JsonObject> findGamesByName(@RequestParam(value = "limit", defaultValue = "25") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        JsonObject jsonResults = gameSVC.findGamesByName(limit, offset);
        return ResponseEntity.ok().body(jsonResults);
    }
}
