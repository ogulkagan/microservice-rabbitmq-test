package com.account.account.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @Getter
    @Setter
    @NotNull(message = "Customer Id cannot be null!")
    private String customer_id;

    @Getter
    @Setter
    @NotNull(message = "Country Id cannot be null!")
    private String country;

    @Getter
    @Setter
    @NotNull(message = "Currencies cannot be null!")
    private ArrayList<String> currencies;
}
