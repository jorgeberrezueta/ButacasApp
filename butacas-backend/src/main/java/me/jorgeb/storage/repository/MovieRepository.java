package me.jorgeb.storage.repository;

import me.jorgeb.storage.model.MovieEntity;

import java.util.List;

public interface MovieRepository extends BaseRepository<MovieEntity, Long> {

    List<MovieEntity> findByName(String lastName);

    MovieEntity findById(long id);

}