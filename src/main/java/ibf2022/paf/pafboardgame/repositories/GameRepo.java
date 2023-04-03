package ibf2022.paf.pafboardgame.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.paf.pafboardgame.models.Game;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Repository
public class GameRepo {

    public final String COLLECTION_GAMES = "games";
    public final String COLLECTION_COMMENTS = "comments";

    @Autowired
    private MongoTemplate mongoTemplate;

    public JsonObject findGamesByName(Integer limit, Integer offset) {
        Criteria criteria = Criteria.where("gid").exists(true);
        Query query = Query.query(criteria).with(Sort.by(Sort.Direction.ASC, "gid")).limit(limit).skip(offset);
        List<Game> listOfGames = mongoTemplate.find(query, Game.class, COLLECTION_GAMES);
        long count = mongoTemplate.count(query, COLLECTION_GAMES);
        return toJsonObject(limit, offset, listOfGames, count);
    }

    public JsonObject toJsonObject(Integer limit, Integer offset, List<Game> listOfGames, long count) {
        JsonArrayBuilder gamesArrayBuilder = Json.createArrayBuilder();
        for (Game game : listOfGames) {
            JsonObjectBuilder gameObject = Json.createObjectBuilder();
            gameObject.add("game_id", game.getGid())
                    .add("name", game.getName()).build();
            gamesArrayBuilder.add(gameObject);
        }
        JsonArray gamesArray = gamesArrayBuilder.build();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonObject finalJsonObject = jsonObjectBuilder.add("games", gamesArray)
                .add("offset", offset)
                .add("limit", limit)
                .add("total", count)
                .add("timestamp", LocalDateTime.now().toString())
                .build();

        return finalJsonObject;
    }

}
