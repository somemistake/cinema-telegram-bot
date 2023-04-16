package ru.somemistake.cinema.kinopoisk.config;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.kinopoisk-api")
public class KinopoiskConfig {

    private String host;
    private int port;

    @Bean
    public Channel kinopoiskChannel() {
        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
