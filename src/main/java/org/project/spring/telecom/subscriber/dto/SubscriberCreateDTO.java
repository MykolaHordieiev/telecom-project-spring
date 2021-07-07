package org.project.spring.telecom.subscriber.dto;

import lombok.Data;
import org.project.spring.telecom.user.UserRole;

@Data
public class SubscriberCreateDTO {

    private Long id;
    private String login;
    private String password;
    private UserRole userRole;

    public SubscriberCreateDTO() {
        this.userRole = UserRole.SUBSCRIBER;
    }

    public SubscriberCreateDTO(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userRole = UserRole.SUBSCRIBER;
    }
}
