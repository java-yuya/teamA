package com.example.music_management.repository;

import com.example.music_management.mapper.AlbumMapper;
import com.example.music_management.entity.Album;
import org.springframework.stereotype.Repository;
import com.example.music_management.viewmodel.AlbumViewModel;

import java.util.List;
//リポジトリークラスであることを宣言。また、このアノテーションでSpring　BootのDIの対象になります　
@Repository
public class AlbumRepository {
    //ここからAlbumMapper を DI するための記述
    private final AlbumMapper albumMapper;

    public AlbumRepository(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }
    //ここまで
    public List<Album> getAllAlbums() {
    //AlbumMapper に定義されているデータベース処理を呼び出す

        return albumMapper.selectAllAlbums();
    }

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
    }

    public List<AlbumViewModel> getAllAlbumsWithMusicCount() {
        return albumMapper.selectAllAlbumsWithMusicCount();
    }
}
//アルバム関連のビジネスロジックを定義するクラス  AlbumService　へ