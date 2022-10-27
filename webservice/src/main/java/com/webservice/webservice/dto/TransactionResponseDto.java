package com.webservice.webservice.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto implements Serializable {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int account_id;

    @Getter
    @Setter
    private double amount;

    @Getter
    @Setter
    private double balance;

    @Getter
    @Setter
    private String currency;

    @Getter
    @Setter
    private String direction;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String message;
}
