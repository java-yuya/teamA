package com.example.music_management.service;

import com.example.music_management.repository.AlbumRepository;
import com.example.music_management.entity.Album;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.music_management.form.AlbumForm; //albumFormをインポート
//import com.example.music_management.viewmodel.AlbumViewModel;
//import com.example.music_management.exception.AlbumNotFoundException;
//import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumService {
    //ここからAlbumRepository を DI するための記述
    private final AlbumRepository albumRepository;

    /*public void createAlbum(AlbumForm albumForm) {
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
    }*/

        // ユーザーごとの口座を取得
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    // ユーザーごとに口座を設立
    public List<Album> getAllAlbum(long userId) {
        return albumRepository.getAllAlbum(userId);
    }

    // ユーザーごとに口座を設立
    public void createAlbum(AlbumForm albumForm, long userId) {
        Album album = new Album();
        album.setTitle(albumForm.getTitle());
        album.setArtist(albumForm.getArtist());
        album.setReleaseDate(albumForm.getReleaseDate());
        album.setUserId(userId);
        albumRepository.insertAlbum(album);
    }

    // 口座の詳細に移動
    public Album getAlbumById(long albumId) {
        return albumRepository.getAlbumById(albumId);
    }

    // 口座の残高を変更する
    public void setPrice(int price, long albumId) {
        albumRepository.setPrice(price, albumId);
    }

    // 口座残高を取得
    public int getBalanceById(long albumId) {
        return albumRepository.getBalanceById(albumId);
    }

    // 口座削除
    public void deleteAlbum(long albumId) {
        albumRepository.deleteAlbum(albumId);
    }
}
//AlbumController へ