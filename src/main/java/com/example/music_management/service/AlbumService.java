package com.example.music_management.service;

import com.example.music_management.repository.AlbumRepository;
import com.example.music_management.entity.Album;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.music_management.form.AlbumForm; //albumFormをインポート
import com.example.music_management.viewmodel.AlbumViewModel;
import com.example.music_management.exception.AlbumNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumService {
    //ここからAlbumRepository を DI するための記述
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
    //ここまで
    public List<Album> getAllAlbums() {
        //AlbumRepository に定義されているアルバム一覧取得処理を呼び出す
        return albumRepository.getAllAlbums();
    }
    /*引数にAlbumFormクラスのインスタンスを受け取り、
    それをAlbumクラスのインスタンスに変換し、Repositoryクラスに渡す*/
    public void createAlbum(AlbumForm albumForm) {
        Album album = new Album();
        album.setTitle(albumForm.getTitle());
        album.setArtist(albumForm.getArtist());
        album.setReleaseDate(albumForm.getReleaseDate());
        albumRepository.insertAlbum(album);
    }

    public Album getAlbumById(long albumId) {
        return albumRepository.getAlbumById(albumId);
    }

    public void deleteAlbum(long albumId) {
        albumRepository.deleteAlbum(albumId);
    }

    @Transactional
    public void updateAlbum(long albumId, Album album) {
        Album existingAlbum = getAlbumById(albumId);
        if (existingAlbum == null) {
            throw new AlbumNotFoundException("Album not found");
        }

        if (albumId != album.getAlbumId()) {
            throw new AlbumNotFoundException("Album ID does not match");
        }
        albumRepository.updateAlbum(album);
    }

    public List<AlbumViewModel> getAllAlbumsWithMusicCount() {
        return albumRepository.getAllAlbumsWithMusicCount();
    }
}
//AlbumController へ