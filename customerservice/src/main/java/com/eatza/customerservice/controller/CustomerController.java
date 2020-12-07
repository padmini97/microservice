package com.eatza.customerservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.customerservice.dto.CustomerDto;
import com.eatza.customerservice.exception.CustomerException;
import com.eatza.customerservice.model.Customer;
import com.eatza.customerservice.service.customerservice.CustomerService;

@RestController
@RefreshScope
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@PostMapping("/customer")
	public ResponseEntity<CustomerDto> addCustomer(@RequestHeader String authorization,@RequestBody CustomerDto customerDto) {
		logger.info("customer added successfully");
		return new ResponseEntity<CustomerDto>(customerService.addCustomer(customerDto), HttpStatus.OK);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId) throws CustomerException {
		logger.info("fetching customer by using id  successfully");
		return new ResponseEntity<Customer>(customerService.getCustomerById(customerId), HttpStatus.OK);
	}

//	@DeleteMapping("/customer/deactivate/{customerId}")
//	public ResponseEntity<String> deleteCustomer(@RequestHeader String authorization,
//			@PathVariable long customerId) throws CustomerException {
//		logger.info("customer deleted successfully");
//		return new ResponseEntity<String>(customerService.deleteCustomer(customerId), HttpStatus.OK);
//	}
	
	@PutMapping("/customer/edit/{customerId}")
	public ResponseEntity<String>updateCustomer(@RequestHeader String authorization,@RequestBody CustomerDto customerDto, @PathVariable long customerId) throws CustomerException{
		logger.info("customer updated successfully");
		return new ResponseEntity<String>(customerService.updateCustomer(customerDto,customerId), HttpStatus.OK);
	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>>getAllCustomer(){
		logger.info("Fetch all Customer");
		return new ResponseEntity<List<Customer>>(customerService.getAllCustomer(), HttpStatus.OK);
	}
	
//	@PostMapping("/customer/review")
//	public ResponseEntity<String> provideReview(@RequestHeader String authorization,@RequestBody ReviewDto reviewDto) throws RestaurantNotVerifiedException, ReviewException{
//		return new ResponseEntity<String>(customerService.provideReview(authorization,reviewDto), HttpStatus.OK);
//		
//	}

}
