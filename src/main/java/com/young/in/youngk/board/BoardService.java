package com.young.in.youngk.board;


import com.young.in.youngk.commnet.Comment;
import com.young.in.youngk.commnet.CommentRepository;
import com.young.in.youngk.user.User;
import com.young.in.youngk.user.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class BoardService{

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public Optional<Board> getPostWithComments(String postId) {
        try {
            ObjectId objectId = new ObjectId(postId);
            Optional<Board> post = boardRepository.findById(postId);
            if (post.isPresent()) {
                System.out.println("테스트 : " + objectId);
                List<Comment> comments = commentRepository.findByPostId(objectId);
                System.out.println("Comments found: " + comments.size());
                post.get().setComments(comments);

                User user = userRepository.findById(post.get().getUserId());
                post.get().setUser(user);
            }
            return post;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid postId format: " + postId);
            return Optional.empty();
        }
    }

}
