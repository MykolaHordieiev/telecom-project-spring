package org.project.spring.telecom.subscriber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberAddSubscribingDTO {

    private Long id;
    private Double balance;
}
