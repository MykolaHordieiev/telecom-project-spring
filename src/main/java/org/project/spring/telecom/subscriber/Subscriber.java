package org.project.spring.telecom.subscriber;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.project.spring.telecom.user.User;
import org.project.spring.telecom.user.UserRole;


@Data
@EqualsAndHashCode(callSuper = true)
public class Subscriber extends User {

    private double balance;
    private boolean lock;

    public Subscriber() {
        this.setUserRole(UserRole.SUBSCRIBER);
    }


    public Subscriber(Long id, String login, String password, double balance, boolean lock) {
        super(id, login, password, UserRole.SUBSCRIBER);
        this.balance = balance;
        this.lock = lock;
    }
}
