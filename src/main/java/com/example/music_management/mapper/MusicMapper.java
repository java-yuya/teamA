package com.example.music_management.mapper;

import com.example.music_management.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MusicMapper{

  // 口座ごとに支出情報を取り出す
    @Select("select * from music where album_id = #{albumId}")
    List<Music> selectMusicsByAlbumId(long albumId);

  // 口座ごとの収入を取得
    @Select("select * from music where album_id = #{albumId} and artist = 0")
    List<Music> selectBsByAlbumId(long albumId);

  // 口座ごとの収入の合計を取得
    @Select("select sum(Price) from music where album_id = #{albumId} and artist = 0")
    Integer selectBSumByAlbumId(long albumId);

  // 口座ごとの支出を取得
    @Select("select * from music where album_id = #{albumId} and artist = 1")
    List<Music> selectPsByAlbumId(long albumId);

  // 口座ごとの支出の合計を取得
    @Select("select sum(Price) from music where aalbum_id = #{albumId} and artist = 1")
    Integer selectPSumByAlbumId(long albumId);

  // 口座に収支を追加
    @Insert("insert into music (title, Price, album_id) values (#{title}, #{Price}, #{albumId})")
    @Options(useGeneratedKeys = true, keyProperty = "musicId")
    void insertMusic(Music music);

  // 収支を削除
    @Delete("delete from music where music_id = #{musicId}")
    void deleteMusicById(long musicId);

  // 収支IDで金額を取得
    @Select("select * from music where music_id = #{musicId}")
    Music selectMusicById(long musicId);
}