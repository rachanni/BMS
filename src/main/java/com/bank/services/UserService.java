package com.bank.services;

import com.bank.dto.ProfileDto;
import com.bank.entity.Account;
import com.bank.entity.UserInfo;
import com.bank.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public String register(UserInfo user) {
        try {
            Account account = new Account();
            account.setUser(user);
            account.setAccountType("Saving");
            account.setBalance(0);
            String userName = user.getEmailAddress();
            String passWord = passwordEncoder.encode(user.getPassword());

            user.setUserName(userName);
            user.setPassword(passWord);
            user.setAccount(account);

            userInfoRepository.save(user);
            return "User registration successful";
        }catch (Exception e) {
            return "Failed to register user";
        }
    }

    public ProfileDto viewProfile(String userName) {
        UserInfo user = userInfoRepository.findByUserName(userName).get();
        ProfileDto profile = new ProfileDto();

        profile.setAccountNumber(user.getAccount().getAccountNumber());
        profile.setEmail(user.getEmailAddress());
        profile.setAddress(user.getAddress());
        profile.setUserName(user.getUsername());
        profile.setName(user.getName());
        profile.setDateOfBirth(user.getDateOfBirth());
        profile.setBalance(user.getAccount().getBalance());
        profile.setAccountType(user.getAccount().getAccountType());
        profile.setMobileNumber(user.getMobileNumber());

        return profile;
    }
}
