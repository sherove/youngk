
package com.young.in.youngk.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUsersRepository extends MongoRepository<AppUsers, String> {
    AppUsers findById(ObjectId id);
}