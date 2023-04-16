package ru.somemistake.cinema.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Movie(
        int id,
        ExternalId externalId,
        String name,
        String alternativeName,
        String enName,
        Name[] names,
        String type,
        int typeNumber,
        int year,
        String description,
        String shortDescription,
        String slogan,
        String status,
        Rating rating,
        Votes votes,
        int movieLength,
        String ratingMpaa,
        int ageRating,
        Logo logo,
        ShortImage poster,
        ShortImage backdrop,
        VideoTypes videos,
        ItemName[] genres,
        ItemName[] countries,
        PersonInMovie[] persons,
        ReviewInfo reviewInfo,
        SeasonInfo[] seasonsInfo,
        CurrencyValue budget,
        Fees fees,
        Premiere premiere,
        LinkedMovie[] similarMovies,
        LinkedMovie[] sequelsAndPrequels,
        Watchability watchability,
        YearRange[] releaseYears,
        int top10,
        int top250,
        FactInMovie[] facts,
        Images imagesInfo,
        VendorImage[] productionCompanies
) {
}
