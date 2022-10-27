package com.webservice.webservice.dto;


import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto implements Serializable {
    @Getter
    @Setter
    private int account_id;

    @Getter
    @Setter
    private String customer_id;

    @Getter
    @Setter
    private ArrayList<AccountDetailDto> currencies;

    @Getter
    @Setter
    private String message;
}
