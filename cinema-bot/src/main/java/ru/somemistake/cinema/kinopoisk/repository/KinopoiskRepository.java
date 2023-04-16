package ru.somemistake.cinema.kinopoisk.repository;

import io.grpc.Channel;
import org.springframework.stereotype.Repository;
import ru.somemistake.cinema.grpc.KinopoiskGrpc;
import ru.somemistake.cinema.grpc.KinopoiskServiceGrpc;
import ru.somemistake.cinema.model.api.Parameter;
import ru.somemistake.cinema.model.dto.MovieDto;
import ru.somemistake.cinema.helpers.GrpcHelpers;

import java.util.List;

@Repository
public class KinopoiskRepository {

    private final KinopoiskServiceGrpc.KinopoiskServiceBlockingStub stub;

    public KinopoiskRepository(Channel kinopoiskChannel) {
        this.stub = KinopoiskServiceGrpc.newBlockingStub(kinopoiskChannel);
    }

    public MovieDto getRandomMovie() {
        KinopoiskGrpc.MovieResponse randomMovie = stub.getRandomMovie(KinopoiskGrpc.MovieRequest.newBuilder().build());
        return MovieDto.of(randomMovie.getMovies(0));
    }

    public List<MovieDto> getMoviesByParameters(List<Parameter> parameters) {
        KinopoiskGrpc.MovieRequest request = KinopoiskGrpc.MovieRequest.newBuilder()
                .setParameters(GrpcHelpers.grpcParameters(parameters))
                .build();
        KinopoiskGrpc.MovieResponse moviesByParameters = stub.getMoviesByParameters(request);
        return moviesByParameters.getMoviesList().stream().map(MovieDto::of).toList();
    }

    public MovieDto getMovieById(Long id) {
        KinopoiskGrpc.MovieRequest request = KinopoiskGrpc.MovieRequest.newBuilder()
                .setTitleId(id)
                .build();
        KinopoiskGrpc.MovieResponse randomMovie = stub.getMovieById(request);
        return MovieDto.of(randomMovie.getMovies(0));
    }

}
