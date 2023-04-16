package ru.somemistake.cinema.service;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import ru.somemistake.cinema.grpc.KinopoiskGrpc;
import ru.somemistake.cinema.grpc.KinopoiskServiceGrpc;
import ru.somemistake.cinema.model.api.Movie;
import ru.somemistake.cinema.model.api.MovieDocsResponseDto;
import ru.somemistake.cinema.repository.KinopoiskRepository;
import ru.somemistake.cinema.util.GrpcHelpers;

import java.util.Arrays;

@Service
public class KinopoiskService extends KinopoiskServiceGrpc.KinopoiskServiceImplBase {

    private final KinopoiskRepository repository;

    public KinopoiskService(KinopoiskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getRandomMovie(KinopoiskGrpc.MovieRequest request, StreamObserver<KinopoiskGrpc.MovieResponse> responseObserver) {
        Movie randomMovie = repository.getRandomMovie();
        responseObserver.onNext(
                KinopoiskGrpc.MovieResponse.newBuilder()
                        .addMovies(GrpcHelpers.grpcMovie(randomMovie))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getMoviesByParameters(KinopoiskGrpc.MovieRequest request, StreamObserver<KinopoiskGrpc.MovieResponse> responseObserver) {
        MovieDocsResponseDto moviesByParameters = repository.getMoviesByParameters(request.getParameters());
        responseObserver.onNext(
                KinopoiskGrpc.MovieResponse.newBuilder()
                        .addAllMovies(Arrays.stream(moviesByParameters.docs()).map(GrpcHelpers::grpcMovie).toList())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getMovieById(KinopoiskGrpc.MovieRequest request, StreamObserver<KinopoiskGrpc.MovieResponse> responseObserver) {
        Movie movieById = repository.getMovieById(request.getTitleId());
        responseObserver.onNext(
                KinopoiskGrpc.MovieResponse.newBuilder()
                        .addMovies(GrpcHelpers.grpcMovie(movieById))
                        .build()
        );
        responseObserver.onCompleted();
    }
}
