package ru.somemistake.cinema.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.somemistake.cinema.model.exception.KinopoiskApiException;
import ru.somemistake.cinema.service.KinopoiskService;

@Configuration
@ConfigurationProperties("application.server")
public class ServerConfig {

    private int port;

    @Bean
    public Server server(KinopoiskService kinopoiskService) {
        Server server = ServerBuilder
                .forPort(port)
                .addService(kinopoiskService)
                .build();

        try {
            server.start();
            server.awaitTermination();
        } catch (Exception exception) {
            throw new KinopoiskApiException("Cannot start server", exception);
        }

        return server;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
