package com.account.account.mapper;

import com.account.account.dbo.AccountDBO;
import com.account.account.dbo.AccountDetailDBO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Insert("insert into account(customer_id) values(#{customer_id})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insert(AccountDBO accountDBO);

    @Insert("insert into account_detail(account_id,currency,amount) values(#{account_id},#{currency},#{amount})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertDetails(AccountDetailDBO accountDBO);

    @Update("Update account_detail SET amount=#{amount} WHERE id = #{id}")
    void updateAccountBalance(double amount, int id);

}
