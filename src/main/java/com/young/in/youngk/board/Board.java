package com.young.in.youngk.board;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "board")
@Data
public class Board {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private Date createdAt;
    private String userId;
    private List<Comment> comments;  // 댓글 리스트 필드 추가

    // Getter and Setter for comments
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}