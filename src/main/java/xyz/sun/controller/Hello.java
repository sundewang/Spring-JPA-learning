package xyz.sun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.sun.entity.Customer;
import xyz.sun.repository.CustomerCrudRepository;

import java.util.List;

/*
 *
 * @author sundw
 * @date 2018/7/4
 */
@RestController
@RequestMapping("/hello")
public class Hello {
    @Autowired
    CustomerCrudRepository repository;

    @RequestMapping("/get/{lastName}")
    public List<Customer> findLastName(@PathVariable String lastName) {
        List<Customer> customer = repository.findByLastName(lastName);
        return customer;
    }
}
