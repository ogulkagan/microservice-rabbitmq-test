package com.webservice.webservice.mybatis.dbo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDBO {
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
    private String currency;

    @Getter
    @Setter
    private String direction;

    @Getter
    @Setter
    private String description;
}
