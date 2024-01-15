package ru.tickets.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import ru.tickets.Utils;
import ru.tickets.dto.Ticket;
import ru.tickets.model.FlightData;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class FlightService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Ticket> parseTicketsFromJson(String filepath) throws IOException {
        Map<String, List<Ticket>> jsonData = objectMapper.readValue(new File(filepath), new TypeReference<>() {
        });
        return jsonData.get("tickets");
    }

    public List<FlightData> getFlightsVVOtoTLV(List<Ticket> tickets) {
        return tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals("VVO"))
                .filter(ticket -> ticket.getDestination().equals("TLV"))
                .map(Utils::flightFromTicket)
                .collect(Collectors.toList());
    }

    public List<String> getAllCarriers(List<FlightData> flights) {
        return flights.stream()
                .map(FlightData::getCarrier)
                .collect(Collectors.toList());
    }

    public long getMinimumFlightTime(List<FlightData> flights) {
        return flights.stream()
                .mapToLong(FlightData::getFlightMillis)
                .min().orElseThrow();
    }

    public double getAveragePrice(List<FlightData> flights) {
        return flights.stream()
                .mapToInt(FlightData::getPrice)
                .average().orElseThrow();
    }

    public double getMedianPrice(List<FlightData> flights) {
        int size = flights.size();
        int[] sortedPrices = flights.stream()
                .mapToInt(FlightData::getPrice)
                .sorted()
                .toArray();

        if (size % 2 == 0)
            return ((double) (sortedPrices[size / 2] + sortedPrices[size / 2 - 1])) / 2d;
        else
            return sortedPrices[size / 2];
    }
}
