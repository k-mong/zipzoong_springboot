package zip.zipzoong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import zip.zipzoong.dto.response.BoardDetailDto;
import zip.zipzoong.service.BoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardViewController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/boardDetail/{id}")
    public String showBoardDetail(Model model, @PathVariable Long id) {
        BoardDetailDto boardDetail = boardService.findBoardDetail(id);
        model.addAttribute("boardDetail", boardDetail);
        return "common/boardDetail";
    }
}
