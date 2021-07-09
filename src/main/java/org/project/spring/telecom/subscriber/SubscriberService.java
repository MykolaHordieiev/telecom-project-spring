package org.project.spring.telecom.subscriber;

import lombok.RequiredArgsConstructor;
import org.project.spring.telecom.subscriber.dto.SubscriberCreateDTO;
import org.project.spring.telecom.subscriber.dto.SubscriberReplenishDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;

    @Transactional
    public Subscriber create(SubscriberCreateDTO subscriberCreateDTO) {
        SubscriberCreateDTO dto = subscriberRepository.insertSubscriber(subscriberCreateDTO);
        return subscriberMapper.getSubscriberFromCreateDTO(dto);
    }

    public Subscriber getSubscriberById(Long id) {
        return subscriberRepository.getById(id)
                .orElseThrow(() -> new SubscriberException("subscriber with id: " + id + " doesn't exist"));
    }

    public List<Subscriber> getAll(int page) {
        int index = (page - 1) * 5;
        return subscriberRepository.getAll(index);
    }

    public double getCountOfHref() {
        double countOfRows = subscriberRepository.getCountOfRows();
        return Math.ceil(countOfRows / 5);
    }

    public int lockSubscriberById(Long id) {
        return subscriberRepository.lockSubById(id);
    }

    public int unlockSubscriberById(Long id) {
        return subscriberRepository.unlockSubById(id);
    }

    public Subscriber replenishBalance(SubscriberReplenishDTO replenishDTO, Double amount) {
        Subscriber subscriberBeforeReplenish = getSubscriberById(replenishDTO.getId());
        double balanceBefore = subscriberBeforeReplenish.getBalance();
        replenishDTO.setBalance(getNewBalance(balanceBefore, amount));
        subscriberRepository.replenishBalanceById(replenishDTO);
        Subscriber subscriberAfterReplenish = subscriberMapper.getSubscriberFromReplenishDTO(replenishDTO);
        if (balanceBefore < 0 && replenishDTO.getBalance() >= 0) {
            subscriberRepository.unlockSubById(subscriberAfterReplenish.getId());
            subscriberAfterReplenish.setLock(false);
            subscriberAfterReplenish.setLogin(subscriberBeforeReplenish.getLogin());
        }
        return subscriberAfterReplenish;
    }

    private double getNewBalance(Double currentBalance, Double amount) {
        BigDecimal currBalance = BigDecimal.valueOf(currentBalance);
        BigDecimal amountBigDec = BigDecimal.valueOf(amount);
        return Double.parseDouble(currBalance.add(amountBigDec).toString());
    }

    public Subscriber getSubscriberByLogin(String login) {
        return subscriberRepository.getByLogin(login)
                .orElseThrow(() -> new SubscriberException("Subscriber with login: " + login + " not found"));
    }
}
