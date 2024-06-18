
package com.young.in.youngk.board;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(ObjectId postId);
}