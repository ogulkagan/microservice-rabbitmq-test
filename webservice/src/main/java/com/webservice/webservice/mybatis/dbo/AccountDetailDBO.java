package com.webservice.webservice.mybatis.dbo;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailDBO {
    @Getter
    @Setter
    private int id;

    @NotNull
    @Getter
    @Setter
    private int account_id;

    @Getter
    @Setter
    @NotNull
    private double amount;

    @Getter
    @Setter
    @NotNull
    private String currency;

}
