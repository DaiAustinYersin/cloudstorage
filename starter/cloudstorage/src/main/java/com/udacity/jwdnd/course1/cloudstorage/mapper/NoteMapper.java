package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> findAll(Integer userId);

    @Insert("INSERT INTO NOTES (noteId, noteTitle, noteDescription, userid) " +
            "values (#{noteId}, #{noteTitle}, #{noteDescription}, #{userId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Update("UPDATE NOTES " +
            "SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} " +
            "WHERE noteId = #{noteId}")
    Integer updateById(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    Integer deleteById(Integer noteId);

}
