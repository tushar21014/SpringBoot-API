package com.firstAPI.firstAPI.Repo;

import com.firstAPI.firstAPI.Entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepo extends MongoRepository<UserEntity, ObjectId> {

    UserEntity findByUsername(String username);

    UserEntity deleteByUsername(String username);
}
