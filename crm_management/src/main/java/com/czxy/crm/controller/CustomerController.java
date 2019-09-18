package com.czxy.crm.controller;

import com.czxy.crm.domain.Customer;
import com.czxy.crm.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CustomerController
 * @Author
 * @Date 2018/9/7 17:06
 * Version 1.0
 **/
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @GetMapping("/noAssociationCustomers")
    public ResponseEntity<List<Customer>> findNoAssociationCustomers(){
        final List<Customer> customers;
        try {
            customers = customerService.findNoAssociationCustomers();
            if (customers.size()!=0){
                return new ResponseEntity<>(customers,HttpStatus.OK);
            }
            return new ResponseEntity<>(customers,HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/associationFixedAreaCustomers")
    public ResponseEntity<List<Customer>> findHasAssociationFixedAreaCustomers(String fixedAreaId){
        final List<Customer> customers;
        try {
            customers = customerService.findHasAssociationFixedAreaCustomers(fixedAreaId);
            if (customers.size()!=0){
                return new ResponseEntity<>(customers,HttpStatus.OK);
            }
            return new ResponseEntity<>(customers,HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/associationCustomersToFixedarea")
    public ResponseEntity<String> associationCustomersToFixedArea(@RequestParam("customerIdStr") String customerIdStr, @RequestParam("fixedAreaId") String fixedAreaId){
        customerService.associationCustomersToFixedArea(customerIdStr, fixedAreaId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @PostMapping("/saveCustomer")
    public ResponseEntity<Void> saveCustomer(@RequestBody Customer customer){
        try {
            customerService.saveCustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findCustomerByTelephone")
    public ResponseEntity<Customer> findCustomerByTelephone(String telephone){
        Customer customer = customerService.findCustomerByTelephone(telephone);

        return new ResponseEntity<>(customer,HttpStatus.OK);

    }

    @GetMapping("/updateType")
    public ResponseEntity<Void> updateType(String telephone){

        customerService.updateCustomerByTelephone(telephone);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
