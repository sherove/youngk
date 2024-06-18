package com.young.in.youngk.board;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "comment")
@Data
public class Comment {
    @Id
    private String id;
    private ObjectId postId;
    private String content;
    private String author;
    private Date createdAt;
    private String userId;

}