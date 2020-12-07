package com.eatza.customerservice.service.customerservice;

import java.util.List;

import com.eatza.customerservice.dto.CustomerDto;
import com.eatza.customerservice.exception.CustomerException;
import com.eatza.customerservice.model.Customer;

public interface CustomerService {

	public CustomerDto addCustomer(CustomerDto customerDTO);

//	public String deleteCustomer(long customerId) throws CustomerException;

	public Customer getCustomerById(long customerId) throws CustomerException;

	public String updateCustomer(CustomerDto customerDto,long customerId) throws CustomerException;

	public List<Customer> getAllCustomer();

//	public String provideReview(String authorization, ReviewDto reviewDto) throws  ReviewException;

}
