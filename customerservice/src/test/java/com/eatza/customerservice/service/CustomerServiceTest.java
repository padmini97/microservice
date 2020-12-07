package com.eatza.customerservice.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.eatza.customerservice.dto.CustomerDto;
import com.eatza.customerservice.exception.CustomerException;
import com.eatza.customerservice.exception.CustomerNotFoundException;
import com.eatza.customerservice.exception.InputMismatchException;
import com.eatza.customerservice.model.Customer;
import com.eatza.customerservice.repository.CustomerRepository;
import com.eatza.customerservice.service.customerservice.CustomerService;
import com.eatza.customerservice.service.customerservice.CustomerServiceImpl;

@SpringBootTest
@ContextConfiguration(classes = CustomerService.class)
class CustomerServiceTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	private ModelMapper modelMapper;

	Optional<Customer> optionalCustomer = Optional.empty();
	CustomerDto customerDto=new CustomerDto("padmini", "srinagar", "anp9731@gmail.com");
	

	@Test
	void testAddCustomer() {
		CustomerDto customerDto = new CustomerDto();
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("padmini");
		customer.setEmail("anp9731@gmail.com");
		customer.setAddress("srinagar");
		when(customerRepository.save(customer)).thenReturn(customer);
		customerDto = modelMapper.map(customerRepository.save(customer), CustomerDto.class);
		assertEquals(customerServiceImpl.addCustomer(customerDto), customerDto);
	}


	@Test
	public void testCustomerPresent() {
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("padmini");
		customer.setEmail("anp9731@gmail.com");
		customer.setAddress("srinagar");
		assumeTrue(!(optionalCustomer.isPresent()));
		assertThatThrownBy(() -> customerServiceImpl.getCustomerById(200L))
				.isInstanceOf(CustomerNotFoundException.class);
	}

	@Test
	public void testEmployeeWithNegativeId() {
		Customer customerWithNegativeId = new Customer(-1L, "Kesri", "address", "email");
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("padmini");
		customer.setEmail("anp9731@gmail.com");
		customer.setAddress("srinagar");
		assumeTrue(customerWithNegativeId.getCustomerId() < 1);
		assertThatThrownBy(() -> customerServiceImpl.getCustomerById(-1L)).isInstanceOf(InputMismatchException.class);
	}

	@Test
	void testGetCustomerById() throws CustomerException {
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("padmini");
		customer.setEmail("anp9731@gmail.com");
		customer.setAddress("srinagar");
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		assertEquals(customerServiceImpl.getCustomerById(1L), customer);
	}

	@Test
	void testUpdateCustomer() throws CustomerException {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerName("padmini");
		customerDto.setEmail("anp9731@gmail.com");
		customerDto.setAddress("srinagar");
		Customer customer=new Customer(1L, "padmini", "srinagar", "anp9731@gmail.com");
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(modelMapper.map(customerDto, Customer.class)).thenReturn(customer);
		when(customerRepository.saveAndFlush(customer)).thenReturn(customer);
		assertEquals(customerServiceImpl.updateCustomer(customerDto, 1L), "Customer details updated successfully");
	}
	

	@Test
	void testGetAllCustomer() {
		List<Customer> customerList=new ArrayList<Customer>();
		Customer customer=new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("padmini");
		customer.setEmail("anp9731@gmail.com");
		customer.setAddress("srinagar");
		assertEquals(customerServiceImpl.getAllCustomer(), customerList);
	}

}
