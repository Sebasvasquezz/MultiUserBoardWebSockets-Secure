package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private Map<String, TicketInfo> ticketStore = new HashMap<>();

    /**
     * Verifies if a ticket is valid.
     * 
     * @param ticket The ticket to validate.
     * @return True if the ticket is valid, false otherwise.
     */
    public boolean isValid(String ticket) {
        if (ticketStore.containsKey(ticket)) {
            TicketInfo ticketInfo = ticketStore.get(ticket);
            if (ticketInfo != null && !ticketInfo.isExpired()) {
                return true;
            } else {
                ticketStore.remove(ticket);
            }
        }
        return false;
    }

    /**
     * Generates a new ticket with a random 3-letter string.
     * 
     * @return The generated ticket.
     */
    public String generateTicket() {
        String ticket = generateUniqueTicket();
        TicketInfo ticketInfo = new TicketInfo();
        ticketStore.put(ticket, ticketInfo);
        return ticket;
    }

    /**
     * Removes a ticket from the store (e.g., on logout or expiration).
     * 
     * @param ticket The ticket to remove.
     */
    public void removeTicket(String ticket) {
        ticketStore.remove(ticket);
    }

    private String generateUniqueTicket() {
        // Generate a random 3-letter string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private static class TicketInfo {
        private long expirationTime;

        public TicketInfo() {
            this.expirationTime = System.currentTimeMillis() + 3600000; // 1 hour in milliseconds
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}
