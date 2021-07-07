package org.project.spring.telecom.user;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.project.spring.telecom.user.dto.UserLoginDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static Logger log = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

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
