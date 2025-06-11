package com.example.music_management.mapper;

import com.example.music_management.entity.Album;
import org.apache.ibatis.annotations.Insert; //＠Insertをインポート
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options; //＠Optionをインポート
import org.apache.ibatis.annotations.Select;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import com.example.music_management.viewmodel.AlbumViewModel;

@Mapper
public interface AlbumMapper {

  @Select("SELECT * FROM albums")
  List<Album> selectAllAlbums();

  //Insert文のSQLを定義するアノテーション
  //#{title}などの記述はプレースホルダ
  //メソッドの引数から自動的にマッピングされる
  //引数のクラスで使用してるフィールド名と対応させる
  @Insert("INSERT INTO albums (title, artist, release_date) VALUES (#{title}, #{artist}, #{releaseDate})")
  //Insertしたときに自動採番されるIDを自動で引数のインスタンスにセットするためのアノテーション
  //Insert時に発行されたIDをalbumIdフィールドに自動で代入する
  @Options(useGeneratedKeys = true, keyProperty = "albumId")
  void insertAlbum(Album album);

  @Select("SELECT * FROM albums WHERE album_id = #{albumId}")
  Album selectAlbumById(long albumId);

  @Delete("DELETE FROM albums WHERE album_id = #{albumId}")
  void deleteAlbumById(long albumId);

  @Update("UPDATE albums SET title = #{title}, artist = #{artist}, release_date = #{releaseDate} WHERE album_id = #{albumId}")
  void updateAlbum(Album album);

  @Select("""
          SELECT albums.album_id, albums.title, artist, release_date, count(musics.music_id) AS music_count
          FROM albums
          LEFT OUTER JOIN musics ON albums.album_id = musics.album_id
          GROUP BY albums.album_id, albums.title, artist, release_date
          """)
  public List<AlbumViewModel> selectAllAlbumsWithMusicCount();
}