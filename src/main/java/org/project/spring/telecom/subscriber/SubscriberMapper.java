package org.project.spring.telecom.subscriber;

import org.project.spring.telecom.subscriber.dto.SubscriberCreateDTO;
import org.project.spring.telecom.subscriber.dto.SubscriberReplenishDTO;
import org.springframework.stereotype.Component;

@Component
public class SubscriberMapper {

    public Subscriber getSubscriberFromCreateDTO(SubscriberCreateDTO dto) {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(dto.getId());
        subscriber.setLogin(dto.getLogin());
        return subscriber;
    }

    public Subscriber getSubscriberFromReplenishDTO(SubscriberReplenishDTO replenishDTO) {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(replenishDTO.getId());
        subscriber.setBalance(replenishDTO.getBalance());
        return subscriber;
    }
}
