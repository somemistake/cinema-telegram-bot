package ru.somemistake.cinema.db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.somemistake.cinema.db.model.Favorite;

import java.util.List;

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {

    List<Favorite> findByChatId(Long chatId);

    void deleteByTitleIdIn(List<Long> ids);
}
