package org.project.spring.telecom.subscribing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.spring.telecom.product.Product;
import org.project.spring.telecom.rate.Rate;
import org.project.spring.telecom.subscriber.Subscriber;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscribing {

    private Subscriber subscriber;
    private Product product;
    private Rate rate;

}
