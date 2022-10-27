package com.webservice.webservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionDto implements Serializable {
    @Getter
    @Setter
    @NotNull(message = "Account Id cannot be null!")
    private int id;

    @Getter
    @Setter
    @NotNull(message = "Amount cannot be null!")
    private double amount;
}
