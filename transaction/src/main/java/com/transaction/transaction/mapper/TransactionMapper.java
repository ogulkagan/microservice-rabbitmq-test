package com.transaction.transaction.mapper;


import org.apache.ibatis.annotations.*;

import com.transaction.transaction.dbo.TransactionDBO;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Select("select * from moves where account_id = #{account_id}")
    List<TransactionDBO> findAllTransactions(int account_id);

    @Insert("insert into moves(account_id,amount,currency,direction,description) values(#{account_id},#{amount},#{currency},#{direction},#{description})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insert(TransactionDBO transactionDBO);

}
