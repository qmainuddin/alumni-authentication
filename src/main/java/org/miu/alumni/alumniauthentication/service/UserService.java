package org.miu.alumni.alumniauthentication.service;

import org.miu.alumni.alumniauthentication.entity.User;

public interface UserService {
    User findUserByEmail(String email);
}
