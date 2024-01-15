package ru.tickets.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightData {
    private String origin;
    private String destination;
    private long flightMillis;
    private String carrier;
    private Integer price;
}
