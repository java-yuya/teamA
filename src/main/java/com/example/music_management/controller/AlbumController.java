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
import com.example.music_management.form.AlbumForm;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.music_management.entity.Music;
import com.example.music_management.service.MusicService;
import com.example.music_management.form.MusicForm;
import com.example.music_management.viewmodel.AlbumViewModel;
import com.example.music_management.security.CustomUserDetails;
import com.example.music_management.viewmodel.MusicViewModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
//@RequestMapping は Controller クラス全体のルーティングを設定します。ここで設定した値が各メソッドの @GetMapping や @PostMapping の値の前に設定されます。そのため、URL は次のようなルールで決まります。「http://localhost 」 + 「/albums」+「各メソッドの設定値」
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;
    private final MusicService musicService;
    // albumsのcategoryには0か1が入っている（0は普通預金、1は定期預金）
    private static final String[] CATEGORIES = {"普通預金", "定期預金"};
    // musicのtypeには0か1が入っている（0は収入、1は支出）
    private static final String[] TYPES = {"収入", "支出"};

    public AlbumController(AlbumService albumService, MusicService musicService) {
        this.albumService = albumService;
        this.musicService = musicService;
    }
//@GetMapping に値を設定していません。そのため、このメソッドの URL は「http://localhost 」 + 「/albums」+「メソッドの設定値はなし」なので、「http://localhost/albums」 となります
    @GetMapping
    public String albums(Model model) {
        // List<Album> albums = albumService.getAllAlbums();
        List<AlbumViewModel> albums = albumService.getAllAlbumsWithMusicCount();
        
        model.addAttribute("albums", albums);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("types", TYPES);
        /*今回は templates フォルダにサブフォルダ albums を作成しています。
サブフォルダに入った Thymeleaf テンプレートを指定する場合は templates 
フォルダ以下のパスを記述します。
そのため、こちらは「templates フォルダの下の album フォルダの中の 
album-list.html」を表示するという意味になります*/
        return "album/album-list";
    }
    /*アルバム登録フォームの画面を表示するためのルーティング
     * 空のAlbumFormクラスのインスタンスを作成し、album-form画面に渡す*/
    @GetMapping("/new")
    public String albumForm(Model model) {
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumForm", albumForm);
        model.addAttribute("categories", CATEGORIES);
        return "album/album-form";
    }
    /*フォームからの登録処理を受けるルーティング
     * 
    */
    @PostMapping("/new")
    public String createAlbum(AlbumForm albumForm,
                            @RequestParam("title") String title,
                            RedirectAttributes redirectAttributes) {
        if (title.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "口座名は空白にできません");
            return "redirect:/albums/new";
        }
        albumService.createAlbum(albumForm);
        return "redirect:/albums";
    }

    @GetMapping("/{albumId}")
    public String album(@PathVariable long albumId,
                        Model model,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Album album = albumService.getAlbumById(albumId);
        // List<Music> musics = musicService.getMusicsByAlbumId(albumId);
        List<MusicViewModel> musics = musicService.selectMusicsWithFavorite(albumId, userDetails.getUserId());
        model.addAttribute("album", album);
        model.addAttribute("musics", musics);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("types", TYPES);
        return "album/album-detail";
    }

    @PostMapping("/{albumId}/delete")
    public String deleteAlbum(@PathVariable long albumId) {
        albumService.deleteAlbum(albumId);
        return "redirect:/albums";
    }

    @GetMapping("/{albumId}/edit")
    public String editAlbum(@PathVariable long albumId, Model model) {
        Album album = albumService.getAlbumById(albumId);
        model.addAttribute("album", album);

        return "album/album-edit";
    }

    @PostMapping("/{albumId}/edit")
    public String updateAlbum(@PathVariable long albumId, Album album,
                                @RequestParam("title") String title,
                                RedirectAttributes redirectAttributes) {
        if (title.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "口座名は空白にできません");
            return "redirect:/albums/{albumId}/edit";
        }
        albumService.updateAlbum(albumId, album);
        return "redirect:/albums";
    }

    @GetMapping("/{albumId}/musics/new")
    public String createMusicForm(@PathVariable long albumId, Model model) {
        MusicForm musicForm = new MusicForm();
        musicForm.setAlbumId(albumId);
        model.addAttribute("musicForm", musicForm);

        return "music/music-form";
    }

    @PostMapping("/{albumId}/musics/new")
    public String createMusic(@PathVariable long albumId, MusicForm musicForm,
                                @RequestParam("title") String title,
                                @RequestParam("duration") LocalDate duration,
                                RedirectAttributes redirectAttributes) {
        if (title.trim().isEmpty() || duration == null) {
            redirectAttributes.addFlashAttribute("error", "内容と決済日は空白にできません");
            return "redirect:/albums/{albumId}/musics/new";
        }
        musicService.createMusic(musicForm);
        return "redirect:/albums/" + albumId;
    }

    @PostMapping("/{albumId}/musics/{musicId}/delete")
    public String deleteMusic(@PathVariable long albumId, @PathVariable long musicId) {
        musicService.deleteMusic(musicId);
        return "redirect:/albums/" + albumId;
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
                                @RequestParam("duration") LocalDate duration,
                                RedirectAttributes redirectAttributes) {
        if (title.trim().isEmpty() || duration == null) {
            redirectAttributes.addFlashAttribute("error", "内容と決済日は空白にできません");
            return "redirect:/albums/{albumId}/musics/{musicId}/edit";
        }
        musicService.updateMusic(musicId, music);
        return "redirect:/albums/" + albumId;
    }
    
}