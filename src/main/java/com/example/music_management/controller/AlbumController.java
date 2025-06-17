package com.example.music_management.controller;

import com.example.music_management.entity.Album;
import com.example.music_management.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

import java.util.ArrayList;
import com.example.music_management.form.AlbumForm;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.music_management.entity.Music;
import com.example.music_management.service.MusicService;
import com.example.music_management.form.MusicForm;
import com.example.music_management.security.CustomUserDetails;
import com.example.music_management.viewmodel.MusicViewModel;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
//@RequestMapping は Controller クラス全体のルーティングを設定します。ここで設定した値が各メソッドの @GetMapping や @PostMapping の値の前に設定されます。そのため、URL は次のようなルールで決まります。「http://localhost 」 + 「/albums」+「各メソッドの設定値」
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;
    private final MusicService musicService;
    // albumsのcategoryには0か1が入っている（0は普通預金、1は定期預金）
    private static final String[] ARTIST = {"普通預金", "定期預金"};
    // musicのtypeには0か1が入っている（0は収入、1は支出）
    private static final String[] METHOD = {"収入", "支出"};

    public AlbumController(AlbumService albumService, MusicService musicService) {
        this.albumService = albumService;
        this.musicService = musicService;
    }

    // ユーザーごとの口座を取得
    @GetMapping
    public String banks(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

    // 変数の用意（全口座の収支の合計）
    Integer bSum = 0;
    Integer pSum = 0;

    // 変数の用意（全口座の収支の詳細）
    List<Music> bList = new ArrayList<>();
    List<Music> pList = new ArrayList<>();

    // ログインしているユーザーIDをもとに、全口座の詳細を取得
    List<Album> albumList = albumService.getAllAlbum(userDetails.getUserId());

    // 取得した全口座の情報を1つずつ使っていく
    for (Album album : albumList) {

      // それぞれゲッターを使用して口座IDを取得して使用し、それぞれの値を変数に代入している
      // データベースにデータが存在しないときはnullが返されてしまうため、nullを受け取った時の処理が必要
      // 今回はnullを受け取った場合は0を合計に加算するようにしている
      bSum += musicService.getBSumByAlbumId(album.getAlbumId()) != null ? musicService.getBSumByAlbumId(album.getAlbumId()) : 0;
      pSum += musicService.getPSumByAlbumId(album.getAlbumId()) != null ? musicService.getPSumByAlbumId(album.getAlbumId()) : 0;

      // 収入と支出を分けている
      // この段階ではまだListとして値を取得している
      List<Music> bs = musicService.getBsByAlbumId(album.getAlbumId());
      List<Music> ps = musicService.getPsByAlbumId(album.getAlbumId());

      // ここで変数に先ほど受け取った収支のデータを1つずつ入れている
      for (Music b : bs) {
        bList.add(b);
      }
      for (Music p : ps) {
        pList.add(p);
      }
    }

        // bank-listにそれぞれの値を渡す
        model.addAttribute("artist", ARTIST);//アルバム
        model.addAttribute("method", METHOD);//ミュージック
        model.addAttribute("Albums", albumList);
        model.addAttribute("bSum", bSum);
        model.addAttribute("pSum", pSum);
        model.addAttribute("bList", bList);
        model.addAttribute("pList", pList);
        model.addAttribute("bop", bSum - pSum);
        return "album/album-list";
    } 

        // ユーザーごとに口座を設立
    @GetMapping("/new")
    public String albumForm(Model model) {
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumForm", albumForm);
        model.addAttribute("artist", ARTIST);
        return "album/album-form";
    }

    // ユーザーごとに口座を設立
    @PostMapping("/new")
    public String createAlbum(@AuthenticationPrincipal CustomUserDetails userDetails, AlbumForm albumForm) {
        albumService.createAlbum(albumForm, userDetails.getUserId());
        return "redirect:/bank";
    }

    // 口座削除
    @PostMapping("/{albumId}/delete")
    public String deleteAlbum(@PathVariable long albumId) {
        albumService.deleteAlbum(albumId);
        return "redirect:/album";
    }

    // 口座の詳細に移動・口座ごとに支出情報を取り出す
    @GetMapping("/{albumId}")
    public String album(@PathVariable long albumId, Model model) {
        Integer bSum = musicService.getBSumByAlbumId(albumId);
        Integer pSum = musicService.getPSumByAlbumId(albumId);
        if (bSum == null) {
        bSum = 0;
        }
        if (pSum == null) {
        pSum = 0;
        }
        model.addAttribute("artist", ARTIST);//album
        model.addAttribute("method", METHOD);//music
        model.addAttribute("musicList", musicService.getMusicsByAlbumId(albumId));
        model.addAttribute("bList", musicService.getBsByAlbumId(albumId));//口座ごとの収入
        model.addAttribute("pList", musicService.getPsByAlbumId(albumId));//口座ごとの支出
        model.addAttribute("bSum", bSum);//収入合計
        model.addAttribute("pSum", pSum);//支出合計
        model.addAttribute("bop", bSum - pSum);//収支
        albumService.setPrice(bSum - pSum, albumId);//履歴
        model.addAttribute("album", albumService.getAlbumById(albumId));
        return "album/album-music";
    }

    // 口座に収支を追加
    @GetMapping("/{albumId}/musics/new")
    public String createMusicForm(@PathVariable long albumId, Model model) {
        MusicForm musicForm = new MusicForm();
        musicForm.setAlbumId(albumId);
        model.addAttribute("musicForm", musicForm);
        return "music/music-form";
    }

    // 口座に収支を追加
    @PostMapping("/{albumId}/musics/new")
    public String createMusic(@PathVariable long albumId, MusicForm musicForm,
                                @RequestParam("title") String title,
                                @RequestParam(value = "duration", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate duration,
                                RedirectAttributes redirectAttributes) {
        if (title.trim().isEmpty() || duration == null) {
            redirectAttributes.addFlashAttribute("error", "内容と決済日は空白にできません");
            return "redirect:/albums/{albumId}/musics/new";
        }
        musicService.createMusic(musicForm);
        return "redirect:/albums/" + albumId;
    }

    // 収支を削除
    @PostMapping("/{albumId}/musics/{musicId}/delete")
    public String deleteMusic(@PathVariable long albumId, @PathVariable long musicId) {
        musicService.deleteMusicById(musicId, albumId);
        return "redirect:/album/" + albumId;
    }

    @GetMapping("/{albumId}/musics/{musicId}/edit")
    public String editMusic(@PathVariable long albumId, @PathVariable long musicId, Model model) {
        Music music = musicService.getMusicById(musicId);
        model.addAttribute("music", music);
        return "music/music-edit";
    }

    @PostMapping("/{albumId}/musics/{musicId}/edit")
    public String updateMusic(@PathVariable long albumId, @PathVariable long musicId, Music music,
                                @RequestParam("title") String title,
                                @RequestParam(value = "duration", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate duration,
                                RedirectAttributes redirectAttributes) {
        if (title.trim().isEmpty() || duration == null) {
            redirectAttributes.addFlashAttribute("error", "内容と決済日は空白にできません");
            return "redirect:/albums/{albumId}/musics/{musicId}/edit";
        }
        musicService.updateMusic(musicId, music);
        return "redirect:/albums/" + albumId;
    }
    
}