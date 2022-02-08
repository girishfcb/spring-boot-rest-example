package com.khoubyari.example.graphql;

import com.khoubyari.example.dao.jpa.HotelRepository;
import com.khoubyari.example.domain.Hotel;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GraphQLDataFetchers {

    @Autowired
    HotelRepository hotelRepository;

    public DataFetcher getHotelByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Long hotelID = Long.valueOf(dataFetchingEnvironment.getArgument("id"));
            return hotelRepository.findOne(hotelID);
        };
    }

    public DataFetcher createHotelDataFetcher() {
        return dataFetchingEnvironment -> {
            String name = Optional.of(dataFetchingEnvironment.getArgument("name").toString()).orElseThrow(GraphQLException::new);
            String description = dataFetchingEnvironment.getArgument("description");
            String city = dataFetchingEnvironment.getArgument("city");
            Integer rating = dataFetchingEnvironment.getArgument("rating");
            Hotel hotel = new Hotel(name, description, rating);
            hotel.setCity(city);
            hotelRepository.save(hotel);
            return hotel;
        };
    }
}
