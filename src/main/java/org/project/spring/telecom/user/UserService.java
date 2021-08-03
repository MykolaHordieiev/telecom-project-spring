package org.project.spring.telecom.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.project.spring.telecom.infra.annotation.Timed;
import org.project.spring.telecom.user.dto.UserLoginDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Timed
    public User loginUser(UserLoginDTO userLoginDTO) {
        log.info("Try enter user: " + userLoginDTO.getLogin());

        User foundUser = userRepository.getUserByLogin(userLoginDTO)
                .orElseThrow(() -> new UserLoginException("user by login not found"));
        if (!foundUser.getPassword().equals(userLoginDTO.getPassword())) {
            throw new UserLoginException("password no match");
        }
        log.info("User: " + userLoginDTO.getLogin() + " was enter");
        return foundUser;
    }
}
