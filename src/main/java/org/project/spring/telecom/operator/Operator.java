package org.project.spring.telecom.operator;

import lombok.NoArgsConstructor;
import org.project.spring.telecom.user.User;
import org.project.spring.telecom.user.UserRole;

@NoArgsConstructor
public class Operator extends User {

    public Operator(Long id, String login, String password, UserRole userRole) {
        super(id, login, password, userRole);
    }
}
