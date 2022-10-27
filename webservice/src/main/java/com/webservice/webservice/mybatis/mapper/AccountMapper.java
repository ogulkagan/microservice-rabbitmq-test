package com.webservice.webservice.mybatis.mapper;

import com.webservice.webservice.mybatis.dbo.AccountDBO;
import com.webservice.webservice.mybatis.dbo.AccountDetailDBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Select("select * from account where id = #{account_id}")
    AccountDBO findAccountWithId(int account_id);

    @Select("select * from account_detail where account_id = #{account_id}")
    List<AccountDetailDBO> findAccountDetailsWithId(int account_id);

    @Select("select * from account_detail where account_id = #{account_id} AND currency = #{currency}")
    List<AccountDetailDBO> findAccountDetailsWithIdAndCurrency(AccountDetailDBO accountDetailDBO);
}
