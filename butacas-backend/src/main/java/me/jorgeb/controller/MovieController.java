package me.jorgeb.controller;

import me.jorgeb.storage.StorageHandler;
import me.jorgeb.storage.model.MovieEntity;
import me.jorgeb.storage.repository.MovieRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/movie")
public class MovieController extends BaseController<MovieRepository, MovieEntity> {

    private StorageHandler storageHandler;

    public MovieController(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    @Override
    public MovieRepository getRepository() {
        return this.storageHandler.getMovieRepository();
    }

}
