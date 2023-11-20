package org.miu.alumni.alumniauthentication.service.impl;

import lombok.RequiredArgsConstructor;
import org.miu.alumni.alumniauthentication.entity.User;
import org.miu.alumni.alumniauthentication.repository.UserRepo;
import org.miu.alumni.alumniauthentication.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
