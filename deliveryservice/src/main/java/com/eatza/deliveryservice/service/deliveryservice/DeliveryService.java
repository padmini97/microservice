package com.eatza.deliveryservice.service.deliveryservice;

import javax.mail.MessagingException;

import com.eatza.deliveryservice.dto.CustomerDto;
import com.eatza.deliveryservice.exception.DeliveryIdNotFounException;
import com.eatza.deliveryservice.model.Delivery;

public interface DeliveryService {
	
	public Delivery fetchDeliveryDetails(int deliveryId) throws DeliveryIdNotFounException;

	public String deliverFood(int deliveryId) throws DeliveryIdNotFounException, MessagingException; 
	
//	public CustomerDto getCustomerDetails(int deliveryId) throws DeliveryIdNotFounException; 
}
