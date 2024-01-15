package ru.tickets.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import ru.tickets.Utils;
import ru.tickets.dto.Ticket;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class FlightService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Ticket> parseTicketsFromJson(String filepath) throws IOException {
        Map<String, List<Ticket>> jsonData = objectMapper.readValue(new File(filepath), new TypeReference<>() {});
        return jsonData.get("tickets");
    }

    public List<Ticket> getFlightsVVOtoTLV(List<Ticket> tickets) {
        return tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals("VVO"))
                .filter(ticket -> ticket.getDestination().equals("TLV"))
                .collect(Collectors.toList());
    }

    public List<String> getAllCarriers(List<Ticket> tickets) {
        return tickets.stream()
                .map(Ticket::getCarrier)
                .collect(Collectors.toList());
    }

    public long getMinimumFlightTime(List<Ticket> tickets) {
        return tickets.stream()
                .mapToLong(Utils::getFlightMillis)
                .min().orElseThrow();
    }

    public double getAveragePrice(List<Ticket> tickets) {
        return tickets.stream()
                .mapToInt(Ticket::getPrice)
                .average().orElseThrow();
    }

    public double getMedianPrice(List<Ticket> tickets) {
        int size = tickets.size();
        int[] sortedPrices = tickets.stream()
                .mapToInt(Ticket::getPrice)
                .sorted()
                .toArray();

        if (size % 2 == 0)
            return ((double) (sortedPrices[size / 2] + sortedPrices[size / 2 - 1])) / 2d;
        else
            return sortedPrices[size / 2];
    }
}
