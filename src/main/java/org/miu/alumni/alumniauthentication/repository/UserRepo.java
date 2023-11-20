package org.miu.alumni.alumniauthentication.repository;

import org.miu.alumni.alumniauthentication.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
