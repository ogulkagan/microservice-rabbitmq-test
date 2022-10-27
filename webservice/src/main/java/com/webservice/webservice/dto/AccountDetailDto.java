package com.webservice.webservice.dto;


import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailDto implements Serializable {
    @Getter
    @Setter
    private double amount;

    @Getter
    @Setter
    private String currency;

}
