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