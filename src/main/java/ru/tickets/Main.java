package ru.tickets;

import ru.tickets.dto.Ticket;
import ru.tickets.service.FlightService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You should type json filepath in first commandline argument");
            return;
        }

        // Get filepath to json file (should be in command line arguments)
        String filepath = args[0];
        FlightService flightService = new FlightService();

        // Parse json file
        List<Ticket> allTickets;
        try {
            allTickets = flightService.parseTicketsFromJson(filepath);
        } catch (IOException e) {
            System.out.println("Something went wrong during parsing json file. Please check if file is correct");
            return;
        }

        // Take necessary flights and carriers
        List<Ticket> flights = flightService.getFlightsVVOtoTLV(allTickets);
        List<String> carriers = flightService.getAllCarriers(flights);

        // Parse data about each carrier flight durations
        Map<String, String> minCarrierTime = new HashMap<>();
        for (String carrier : carriers) {
            List<Ticket> carrierFlight = flights.stream()
                    .filter(flight -> flight.getCarrier().equals(carrier)).toList();

            minCarrierTime.put(carrier, Utils.timeFromMillis(flightService.getMinimumFlightTime(carrierFlight)));
        }

        // Print this data
        for (String carrier : minCarrierTime.keySet())
            System.out.printf("Carrier: %s\t|\tMin flight time: %s\n", carrier, minCarrierTime.get(carrier));

        // Calculate average and median prices
        double avgPrice = flightService.getAveragePrice(flights);
        double medianPrice = flightService.getMedianPrice(flights);
        double difference = avgPrice - medianPrice;

        // Print data about prices
        if (difference >= -1E-5 && difference <= 1E-5)
            System.out.printf("Median and average prices are equals: %.2f\n", avgPrice);
        else if (difference < 0)
            System.out.printf("Median price more than average on %.2f\n", -difference);
        else
            System.out.printf("Average price more than median on %.2f\n", difference);
    }
}