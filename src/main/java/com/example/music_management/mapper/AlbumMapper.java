package com.example.music_management.mapper;

import com.example.music_management.entity.Album;
import org.apache.ibatis.annotations.Insert; //＠Insertをインポート
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options; //＠Optionをインポート
import org.apache.ibatis.annotations.Select;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
//import com.example.music_management.viewmodel.AlbumViewModel;
//MyBatis のマッパーインタフェースであることを宣言また、このアノテーションで Spring Boot の DI の対象になります
@Mapper
//MyBatis はインタフェースです。間違ってクラスで作成しないように注意してください。
public interface AlbumMapper {
//@Select アノテーションを使って実行したい SQL を記述します。今回は albums テーブルからすべての情報を取得します。Mybatis の機能により、取得したレコードはそれぞれ Album クラスのインスタンスに変換され、List 型で return されます。戻り値を定義するだけで上記の処理を Mybatis が自動でやってくれるので、便利なライブラリです
  /*@Select("SELECT * FROM albums")
  List<Album> selectAllAlbums()

  @Insert("INSERT INTO albums (title, artist, release_date) VALUES (#{title}, #{artist}, #{releaseDate})")

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
  public List<AlbumViewModel> selectAllAlbumsWithMusicCount();*/

    // ユーザーごとの口座を取得
  @Select("select * from albums where user_id = #{userId}")
  List<Album> selectAllAlbum(long userId);

  // ユーザーごとに口座を設立
  @Insert("insert into albums (title, artist, user_id) values (#{title}, #{artist}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "albumId")
  void insertAlbum(Album album);

  // 口座の詳細に移動
  @Select("select * from albums where album_id = #{albumId}")
  Album selectAlbumById(long albumId);

  // 口座の残高を変更する
  @Update("update albums set balance = #{price} where album_id = #{albumId}")
  void updatePrice(int Price, long albumId);

  // 口座残高を取得
  @Select("select balance from albums where album_id = #{albumId}")
  int selectBalanceById(long albumId);

  // 口座削除
  @Delete("delete from albums where album_id = #{albumId}")
  void deleteAlbumById(long albumId);
}
//AlbumMapperを呼び出すためのクラス　AlbumRepositoryへ