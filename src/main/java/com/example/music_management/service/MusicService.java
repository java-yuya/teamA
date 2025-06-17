package com.example.music_management.service;

import com.example.music_management.exception.BalanceMissingException;
import com.example.music_management.entity.Music;
import com.example.music_management.repository.MusicRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.music_management.form.MusicForm;
import com.example.music_management.entity.Album;
import com.example.music_management.repository.AlbumRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicService {
    private final MusicRepository musicRepository;
    private final AlbumRepository albumRepository;

    public MusicService(MusicRepository musicRepository, AlbumRepository albumRepository) {
        this.musicRepository = musicRepository;
        this.albumRepository = albumRepository;
    }

    /*public List<Music> getMusicsByAlbumId(long albumId) {
        return musicRepository.getMusicsByAlbumId(albumId);
    }

    @Transactional
    public void createMusic(MusicForm musicForm) {
        Album existingAlbum = albumRepository.getAlbumById(musicForm.getAlbumId());
        if (existingAlbum == null) {
            throw new AlbumNotFoundException("Album not found");
        }

        Music music = new Music();
        music.setTitle(musicForm.getTitle());
        music.setDuration(musicForm.getDuration());
        music.setPrice(musicForm.getPrice());
        music.setMethod(musicForm.getMethod());
        music.setAlbumId(musicForm.getAlbumId());
        
        musicRepository.insertMusic(music);
    }

    public void deleteMusic(long musicId) {
        musicRepository.deleteMusicById(musicId);
    }

    public Music getMusicById(long musicId) {
        return musicRepository.selectMusicById(musicId);
    }

    @Transactional
    public void updateMusic(long musicId, Music music) {
        Music existingMusic = getMusicById(musicId);
        if (existingMusic == null) {
            throw new MusicNotFoundException("Music not found", music.getAlbumId());
        }

        if (musicId != music.getMusicId()) {
            throw new  MusicNotFoundException("Music ID does not match", music.getAlbumId());
        }
        musicRepository.updateMusic(music);
    }*/

        // 口座ごとに支出情報を取り出す
    public List<Music> getMusicsByAlbumId(long albumId) {
        return musicRepository.getMusicsByAlbumId(albumId);
    }

    // 口座ごとの収入を取得
    public List<Music> getBsByAlbumId(long albumId) {
        return musicRepository.getBsByAlbumId(albumId);
    }

    // 口座ごとの収入の合計を取得
    public Integer getBSumByAlbumId(long albumId) {
        return musicRepository.getBSumByAlbumId(albumId);
    }

    // 口座ごとの支出を取得
    public List<Music> getPsByAlbumId(long albumId) {
        return musicRepository.getPsByAlbumId(albumId);
    }

    // 口座ごとの支出の合計を取得
    public Integer getPSumByAlbumId(long albumId) {
        return musicRepository.getPSumByAlbumId(albumId);
    }

    // 口座に収支を追加
    public void createMusic(MusicForm musicForm, long albumId) {
        Music music = new Music();
        if (musicForm.getMethod() == 1 && albumRepository.getBalanceById(albumId) - musicForm.getPrice() < 0) {
        throw new BalanceMissingException("Balance Missing!", albumId);
        }
        music.setTitle(musicForm.getTitle());
        music.setPrice(musicForm.getPrice());
        music.setAlbumId(albumId);
        musicRepository.insertMusic(music);
    }

    // 収支を削除
    public void deleteMusicById(long musicId, long albumId) {
        if (getMusicById(musicId).getMethod() == 0 && (getPSumByAlbumId(albumId) != null ? getPSumByAlbumId(musicId) : 0) > getBSumByAlbumId(albumId) - getMusicById(musicId).getPrice()) {
        throw new BalanceMissingException("Balance Missing!", albumId);
        }
        musicRepository.deleteMusicById(musicId);
    }

    // 収支IDで金額を取得
    public Music getMusicById(long musicId) {
        return musicRepository.getMusicById(musicId);
    }
}