package com.example.springmvcpractice.serviceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.springmvcpractice.dtos.PasswordDTO;
import com.example.springmvcpractice.models.Users;
import com.example.springmvcpractice.repositories.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class UsersServiceImpl {

    private UsersRepositories usersRepositories;

    //    HOVER ON THE RED LINE TO GENERATE CONSTRUCTOR METHOD
    @Autowired
    public UsersServiceImpl(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }


    public Function<String, Users> findUserByUsername = (username)->
            usersRepositories.findByUsername(username)
                    .orElseThrow(()-> new NullPointerException("User not found"));

    public Function<Long, Users> findUserById = (id)->
            usersRepositories.findById(id)
                    .orElseThrow(()->new NullPointerException("User not found"));

    public Function<Users, Users> saveUser = (user) -> usersRepositories.save(user);

    public Function<PasswordDTO, Boolean> verifyUserPassword = passwordDTO -> BCrypt.verifyer()
            .verify(passwordDTO.getPassword().toCharArray(),
                    passwordDTO.getHashPassword().toCharArray())
            .verified;
}
