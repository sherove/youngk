package com.young.in.youngk.board.request.entity;

import com.young.in.youngk.board.entity.Board;
import com.young.in.youngk.commnet.Comment;
import com.young.in.youngk.user.AppUsers;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
public class BoardRequest {
    private String id;
    private String title;
    private String content;
    private Date createdAt;
    private ObjectId userId;
    private AppUsers appUsers;


    static public Board toSave(BoardRequest request) {
        return Board.builder()
                .appUsers(request.getAppUsers())
                .userId(request.getUserId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

}