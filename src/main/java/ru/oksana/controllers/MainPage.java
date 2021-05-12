package ru.oksana.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.oksana.algorithm.Algorithm;
import ru.oksana.model.Supplier;
import ru.oksana.services.SupplierService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPage {

    static final Logger logger = LoggerFactory.getLogger(MainPage.class);

    private final SupplierService supplierService;

    @Autowired
    public MainPage(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("listProduct", supplierService.findProducts());
        if (!model.containsAttribute("orderList")) {
            model.addAttribute("orderList", new ArrayList<>());
        }
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam String product,
                      @RequestParam Double quantity, @RequestParam Double unitPrice,
                      @RequestParam Integer capacityOfCar, @RequestParam Integer capacityPallets,
                      @RequestParam Double carPrice, Model model) {
        Supplier supplier = new Supplier(name, product, quantity, unitPrice, capacityOfCar, capacityPallets, carPrice);
        supplierService.saveSupplier(supplier);
        return index(model);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String id, Model model) {
        supplierService.deleteSupplier(id);
        return index(model);
    }

    @PostMapping("/query")
    public String query(@RequestParam Double fullOrder, @RequestParam String product, Model model) {
        List<Supplier> supplierList = supplierService.findSuppliersByProduct(product);
        Algorithm algorithm = new Algorithm(fullOrder, supplierList);
        logger.info("Calculating...");
        double ans = algorithm.calculate();
        model.addAttribute("result", ans);
        logger.info("Calculated: " + ans);
        logger.info("Getting info...");
        List<String> info = algorithm.getCheapestOrderInfo();
        logger.info("Order info: " + info.toString());
        model.addAttribute("orderList", info);
        return index(model);
    }

}
