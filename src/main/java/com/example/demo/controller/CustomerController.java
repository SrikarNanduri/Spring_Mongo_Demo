package com.example.demo.controller;


import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") String id){
        if(repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new UsernameNotFoundException(id);
        }
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer){
        repository.save(customer);
        return customer;
    }

    @PutMapping("/update/{id}")
    public Customer modifyCustomerById(@PathVariable("id") String id, @RequestBody Customer customer){
        customer.setId(id);
        repository.save(customer);
        return customer;
    }

    @DeleteMapping("/delete/{id}")
    public void deteteCustomer(@PathVariable("id") String id){
        if(repository.findById(id).isPresent()) {
            repository.delete(repository.findById(id).get());
        } else {
            throw new UsernameNotFoundException(id);
        }
    }
}
