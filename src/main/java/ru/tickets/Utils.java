package ru.tickets;

import ru.tickets.dto.Ticket;

public class Utils {

    // Returns flight duration in milliseconds
    public static long getFlightMillis(Ticket ticket) {
        long takeOffMillis = ticket.getDeparture_date().getTime() + ticket.getDeparture_time().getTime();
        long landMillis = ticket.getArrival_date().getTime() + ticket.getArrival_time().getTime();

        return landMillis - takeOffMillis;
    }

    // Formats milliseconds in string like 'HH:mm'
    public static String timeFromMillis(long millis) {
        int allMinutes = (int) (millis / 1000 / 60);

        if (allMinutes < 60)
            return String.valueOf(allMinutes);

        int minutes = allMinutes % 60;
        int hours = allMinutes / 60;

        if (minutes == 0)
            return String.format("%d:00", hours);

        if (minutes < 10)
            return String.format("%d:0%d", hours, minutes);

        return String.format("%d:%d", hours, minutes);
    }
}
