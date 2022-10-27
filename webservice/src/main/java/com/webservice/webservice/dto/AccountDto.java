package com.webservice.webservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Serializable {
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
    @NotNull
    private ArrayList<String> currencies;
}
