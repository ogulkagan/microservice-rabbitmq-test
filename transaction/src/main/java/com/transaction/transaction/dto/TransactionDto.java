package com.transaction.transaction.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Serializable {
    @Getter
    @Setter
    @NotNull(message = "Account Id cannot be null!")
    private int account_id;

    @Getter
    @Setter
    @NotNull(message = "Amount cannot be null!")
    private double amount;

    @Getter
    @Setter
    @NotNull(message = "Currency cannot be null!")
    private String currency;

    @Getter
    @Setter
    @NotNull(message = "Direnction cannot be null!")
    private String direction;

    @Getter
    @Setter
    private String description;

}
