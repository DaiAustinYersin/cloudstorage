package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> findAll(Integer userId);

    @Insert("INSERT INTO FILES (fileId, fileName, contentType, fileSize, fileData, userId) " +
            "values (#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Update("UPDATE FILES " +
            "SET fileName = #{fileName}, contentType = #{contentType}, fileSize = #{fileSize}, fileData = #{fileData} " +
            "WHERE fileId = #{fileId}")
    Integer updateById(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteById(Integer fileId);
    
}
