package com.young.in.youngk.board;


import com.young.in.youngk.board.entity.Board;
import com.young.in.youngk.board.request.entity.BoardRequest;
import com.young.in.youngk.commnet.Comment;
import com.young.in.youngk.commnet.CommentRepository;
import com.young.in.youngk.user.AppUsers;
import com.young.in.youngk.user.AppUsersRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class BoardService{

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final AppUsersRepository appUsersRepository;

    @Transactional(readOnly = true)
    public Optional<Board> getPostWithComments(String postId) {
        try {
            ObjectId objectId = new ObjectId(postId);
            Optional<Board> post = boardRepository.findById(postId);
            if (post.isPresent()) {
                System.out.println("테스트 : " + objectId);
                List<Comment> comments = commentRepository.findByPostId(objectId);
                System.out.println("Comments found: " + comments.size());
                post.get().setComments(comments);

                AppUsers appUsers = appUsersRepository.findById(post.get().getUserId());
                post.get().setAppUsers(appUsers);
            }
            return post;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid postId format: " + postId);
            return Optional.empty();
        }
    }

    public Board save(BoardRequest request){
        var saveData = BoardRequest.toSave(request);
        return boardRepository.save(saveData);
    }



}
