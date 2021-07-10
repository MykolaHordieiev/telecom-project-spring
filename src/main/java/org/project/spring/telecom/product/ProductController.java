package org.project.spring.telecom.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get/all/product")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("/product/all.jsp");
        modelAndView.addObject("products", productService.getAllProduct());
        return modelAndView;
    }
}
