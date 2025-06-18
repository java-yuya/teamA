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
    @Select("select * from musics where album_id = #{albumId}")
    List<Music> selectMusicsByAlbumId(long albumId);

  // 口座ごとの収入を取得
    @Select("select * from musics where album_id = #{albumId} and method = 0")
    List<Music> selectBsByAlbumId(long albumId);

  // 口座ごとの収入の合計を取得
    @Select("select sum(price) from musics where album_id = #{albumId} and method = 0")
    Integer selectBSumByAlbumId(long albumId);

  // 口座ごとの支出を取得
    @Select("select * from musics where album_id = #{albumId} and method = 1")
    List<Music> selectPsByAlbumId(long albumId);

  // 口座ごとの支出の合計を取得
    @Select("select sum(price) from musics where album_id = #{albumId} and method = 1")
    Integer selectPSumByAlbumId(long albumId);

  // 口座に収支を追加
    @Insert("insert into musics (title, duration, price, method, album_id) values (#{title}, #{duration}, #{price}, #{method}, #{albumId})")
    @Options(useGeneratedKeys = true, keyProperty = "musicId")
    void insertMusic(Music music);

  // 収支を削除
    @Delete("delete from musics where music_id = #{musicId}")
    void deleteMusicById(long musicId);

  // 収支IDで金額を取得
    @Select("select * from musics where music_id = #{musicId}")
    Music selectMusicById(long musicId);

    @Update("UPDATE musics SET title = #{title}, duration = #{duration}, price = #{price}, method = #{method} WHERE music_id = #{musicId}")
    void updateMusic(Music music);
}