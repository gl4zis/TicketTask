package ru.tickets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
// DTO, representing the json data
@Data
public class Ticket {
    private String origin;
    private String origin_name;
    private String destination;
    private String destination_name;
    @JsonFormat(pattern = "dd.MM.yy", timezone = "Europe/Moscow")
    private Date departure_date;
    @JsonFormat(pattern = "HH:mm", timezone = "Europe/Moscow")
    private Date departure_time;
    @JsonFormat(pattern = "dd.MM.yy", timezone = "Europe/Moscow")
    private Date arrival_date;
    @JsonFormat(pattern = "HH:mm", timezone = "Europe/Moscow")
    private Date arrival_time;
    private String carrier;
    private Integer stops;
    private Integer price;
}
