package com.young.in.youngk.user;

import com.young.in.youngk.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/app-users")
public class AppUsersController {

    @Autowired
    private BoardService boardService;

    @PostMapping("")
    public Optional<AppUsers> save(@RequestBody AppUsers appUsers) {
        return null;
    }

}
