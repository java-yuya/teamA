package com.example.music_management.repository;

import com.example.music_management.entity.Music;
import com.example.music_management.mapper.MusicMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MusicRepository {
    /*private final MusicMapper musicMapper;
    
    public MusicRepository(MusicMapper musicMapper) {
        this.musicMapper = musicMapper;
    }

    public List<Music> getMusicsByAlbumId(long albumId) {
        return musicMapper.selectMusicsById(albumId);
    }

    public void insertMusic(Music music) {
        musicMapper.insertMusic(music);
    }

    public void deleteMusicById(long musicId) {
        musicMapper.deleteMusicById(musicId);
    }

    public void updateMusic(Music music) {
        musicMapper.updateMusic(music);
    }

    public Music selectMusicById(long musicId) {
        return musicMapper.selectMusicById(musicId);
    }*/

    private final MusicMapper musicMapper;

  public MusicRepository(MusicMapper musicMapper) {
    this.musicMapper = musicMapper;
  }

  // 口座ごとに支出情報を取り出す
  public List<Music> getMusicsByAlbumId(long albumId) {
    return musicMapper.selectMusicsByAlbumId(albumId);
  }

  // 口座ごとの収入を取得
  public List<Music> getBsByAlbumId(long albumId) {
    return musicMapper.selectBsByAlbumId(albumId);
  }

  // 口座ごとの収入の合計を取得
  public Integer getBSumByAlbumId(long albumId) {
    return musicMapper.selectBSumByAlbumId(albumId);
  }

  // 口座ごとの支出を取得
  public List<Music> getPsByAlbumId(long albumId) {
    return musicMapper.selectPsByAlbumId(albumId);
  }

  // 口座ごとの支出の合計を取得
  public Integer getPSumByAlbumId(long albumId) {
    return musicMapper.selectPSumByAlbumId(albumId);
  }

  // 口座に収支を追加
  public void insertMusic(Music music) {
    musicMapper.insertMusic(music);
  }

  // 収支を削除
  public void deleteMusicById(long musicId) {
    musicMapper.deleteMusicById(musicId);
  }

  // 収支IDで金額を取得
  public Music getMusicById(long musicId) {
    return musicMapper.selectMusicById(musicId);
  }

  public void updateMusic(Music music) {
        musicMapper.updateMusic(music);
  }
}