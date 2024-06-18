package com.young.in.youngk.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
