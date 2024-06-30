
package com.young.in.youngk.user;

import com.young.in.youngk.commnet.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findById(ObjectId id);
}