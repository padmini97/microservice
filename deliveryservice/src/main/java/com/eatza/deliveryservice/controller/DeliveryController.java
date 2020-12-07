package com.eatza.deliveryservice.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.deliveryservice.dto.CustomerDto;
import com.eatza.deliveryservice.exception.DeliveryIdNotFounException;
import com.eatza.deliveryservice.model.Delivery;
import com.eatza.deliveryservice.service.deliveryservice.DeliveryService;



@RestController
public class DeliveryController {
	
	@Autowired
	DeliveryService deliveryService;
	
	@GetMapping("/delivery/deliveryDetails/{deliveryId}")
	public ResponseEntity<Delivery> getDeliveryDetails(@RequestHeader String authorization,@PathVariable int deliveryId) throws DeliveryIdNotFounException{
		return new ResponseEntity<Delivery>(deliveryService.fetchDeliveryDetails(deliveryId),HttpStatus.OK);	
	}
	
	@PutMapping("/delivery/deliverFood/{deliveryId}")
	public ResponseEntity<String> deliverFood(@RequestHeader String authorization,@PathVariable int deliveryId) throws DeliveryIdNotFounException, MessagingException{
		 return new ResponseEntity<String>(deliveryService.deliverFood(deliveryId),HttpStatus.OK);
	}
	
//	@GetMapping("/delivery/customerDetails/{deliveryId}")
//	public ResponseEntity<CustomerDto> getCustomerDetails(@PathVariable int deliveryId) throws DeliveryIdNotFounException{
//		return new ResponseEntity<CustomerDto>(deliveryService.getCustomerDetails(deliveryId),HttpStatus.OK);
//	}


}
