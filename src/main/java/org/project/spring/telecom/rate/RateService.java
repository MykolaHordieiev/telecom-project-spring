package org.project.spring.telecom.rate;

import lombok.RequiredArgsConstructor;

import org.project.spring.telecom.infra.annotation.Timed;
import org.project.spring.telecom.rate.dto.RateAddRequestDTO;
import org.project.spring.telecom.rate.dto.RateChangeRequestDTO;
import org.project.spring.telecom.subscriber.Subscriber;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;

    @Timed
    public List<Rate> getRatesByProductId(Long productId) {
        return rateRepository.getRatesByProduct(productId);
    }

    public List<Rate> getRatesBySubscriberId(Long subscriberId) {
        return rateRepository.getRatesBySubscriberId(subscriberId);
    }

    public Rate getRateById(Long id) {
        return rateRepository.getRateById(id).orElseThrow(() -> new RateException("rate by id: " +
                id + " not found"));
    }

    public RateChangeRequestDTO changeRateById(RateChangeRequestDTO rateDTO) {
        return rateRepository.changeRateById(rateDTO);
    }

    public RateAddRequestDTO addRateForProduct(RateAddRequestDTO rateDTO) {
        return rateRepository.addRateByProductId(rateDTO);
    }

    public Long deleteRateById(Long id) {
        return rateRepository.deleteRateById(id);
    }

    public List<Subscriber> checkUsingRateBeforeDelete(Long id) {
        return rateRepository.checkUsingRateBySubscribers(id);
    }

    public Rate doUnusableRate(Rate rate) {
        return rateRepository.doUnusableRateByRateId(rate);
    }

}
