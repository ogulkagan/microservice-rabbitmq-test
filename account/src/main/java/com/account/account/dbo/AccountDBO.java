package com.account.account.dbo;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDBO {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String customer_id;
}
