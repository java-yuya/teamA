package com.example.music_management.repository;

import com.example.music_management.mapper.AlbumMapper;
import com.example.music_management.entity.Album;
import org.springframework.stereotype.Repository;

import java.util.List;
//リポジトリークラスであることを宣言。また、このアノテーションでSpring　BootのDIの対象になります　
@Repository
public class AlbumRepository {
    //ここからAlbumMapper を DI するための記述
    /*private final AlbumMapper albumMapper;

    public AlbumRepository(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }
    //ここまで
    public List<Album> getAllAlbums() {
    //AlbumMapper に定義されているデータベース処理を呼び出す

        return albumMapper.selectAllAlbums();
    }
    //Mapperの処理を呼び出すメソッド
    public void insertAlbum(Album album) {
        albumMapper.insertAlbum(album);
    }

    public Album getAlbumById(long albumId) {
        return albumMapper.selectAlbumById(albumId);
    }

    public void deleteAlbum(long albumId) {
        albumMapper.deleteAlbumById(albumId);
    }

    public void updateAlbum(Album album) {
        albumMapper.updateAlbum(album);
    }*/

    private final AlbumMapper albumMapper;

    // ユーザーごとの口座を取得
    public AlbumRepository(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }

    // ユーザーごとに口座を設立
    public List<Album> getAllAlbum(long userId) {
        return albumMapper.selectAllAlbum(userId);
    }

    // ユーザーごとに口座を設立
    public void insertAlbum(Album album) {
        albumMapper.insertAlbum(album);
    }

    // 口座の詳細に移動
    public Album getAlbumById(long albumId) {
        return albumMapper.selectAlbumById(albumId);
    }

    // 口座の残高を変更する
    public void setPrice(int Price, long albumId) {
        albumMapper.updatePrice(Price, albumId);
    }

    // 口座残高を取得
    public int getBalanceById(long albumId) {
        return albumMapper.selectBalanceById(albumId);
    }

    // 口座削除
    public void deleteAlbum(long albumId) {
        albumMapper.deleteAlbumById(albumId);
    }
}
//アルバム関連のビジネスロジックを定義するクラス  AlbumService　へ