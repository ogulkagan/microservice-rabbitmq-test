package com.webservice.webservice.mybatis.mapper;

import com.webservice.webservice.mybatis.dbo.TransactionDBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Select("select * from moves where account_id = #{transaction_id}")
    List<TransactionDBO> findAllTransactions(int account_id);

    @Select("select * from moves where id = #{id}")
    TransactionDBO findTransaction(int id);

}
