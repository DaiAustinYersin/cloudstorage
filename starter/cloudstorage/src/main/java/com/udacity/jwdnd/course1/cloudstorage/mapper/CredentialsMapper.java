package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> findAll(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (credentialId, url, username, key, password, userId) " +
            "values (#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credentials credentials);

    @Update("UPDATE CREDENTIALS " +
            "SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userId = {userId} " +
            "WHERE credentialId = #{credentialId}")
    Integer updateById(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Integer deleteById(Integer credentialId);
    
}
