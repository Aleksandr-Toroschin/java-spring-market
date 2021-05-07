package ru.toroschin.spring.market.error_handling;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class MarketError {
    private List<String> messages;
    private int status;
    private Date date;

    public MarketError(int status, List<String> messages) {
        this.status = status;
        this.messages = messages;
        this.date = new Date();
    }

    public MarketError(int status, String message) {
        this.status = status;
        this.messages = new ArrayList<>(Arrays.asList(message));
        this.date = new Date();
    }
}
