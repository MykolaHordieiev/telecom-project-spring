package org.project.spring.telecom.subscribing;

import lombok.RequiredArgsConstructor;
import org.project.spring.telecom.rate.dto.RateAddSubscribingDTO;
import org.project.spring.telecom.subscriber.dto.SubscriberAddSubscribingDTO;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribingService {

    private final SubscribingRepository subscribingRepository;

    @Transactional
    public SubscriberAddSubscribingDTO addSubscribing(SubscriberAddSubscribingDTO subscriberDTO, Long productId, RateAddSubscribingDTO rateDTO) {
        double newBalance = getNewBalance(subscriberDTO, rateDTO);
        subscriberDTO.setBalance(newBalance);
        return subscribingRepository.addSubscribing(subscriberDTO, productId, rateDTO.getRateId());
    }

    public List<Subscribing> getSubscribing(Long id) {
        return subscribingRepository.getSubscribingBySubscriberId(id);
    }

    private double getNewBalance(SubscriberAddSubscribingDTO subscriberDTO, RateAddSubscribingDTO rateDTO) {
        BigDecimal balance = BigDecimal.valueOf(subscriberDTO.getBalance());
        BigDecimal cost = BigDecimal.valueOf(rateDTO.getPrice());
        return Double.parseDouble(balance.subtract(cost).toString());
    }
}
