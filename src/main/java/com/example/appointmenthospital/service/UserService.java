package com.example.appointmenthospital.service;

import com.example.appointmenthospital.model.Users;
import com.example.appointmenthospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Users findUserByUsername(String username){
        Optional<Users> u = userRepository.findByUsername(username);
         if (u.isEmpty()){
             return null;
         }
         return u.get();
    }

    public boolean createUser(Users user){
        Users created = userRepository.save(user);
        if(created == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateUser(Users user){
        Optional<Users> fetch = userRepository.findById(user.getId());
        if(fetch.isEmpty()){
            return false;
        }else{
            userRepository.saveAndFlush(user);
            return true;
        }
    }

}
