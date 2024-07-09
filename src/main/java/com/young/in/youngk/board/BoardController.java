package com.young.in.youngk.board;

import com.young.in.youngk.board.entity.Board;
import com.young.in.youngk.board.request.entity.BoardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/{id}")
    public Optional<Board> getPostWithComments(@PathVariable String id) {
        return boardService.getPostWithComments(id);
    }

    @PostMapping("")
    public Board saveBoard(@RequestBody BoardRequest request) {
        return boardService.save(request);

    }

}
