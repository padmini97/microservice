package com.eatza.deliveryservice.service.deliveryservice.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.eatza.deliveryservice.dto.DeliveryRequestDto;
import com.eatza.deliveryservice.exception.DeliveryIdNotFounException;
import com.eatza.deliveryservice.model.Delivery;
import com.eatza.deliveryservice.repository.DeliveryRepository;
import com.eatza.deliveryservice.service.deliveryservice.DeliveryServiceImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
class DeliveryServiceTest {

	@InjectMocks
	DeliveryServiceImpl deliveryServiceImpl;

	@Mock
	DeliveryRepository deliveryRepository;
	
	@Mock
	ObjectMapper objectMapper;
	
	Optional<Delivery> optionalDelivery = Optional.empty();
	
	@Test
	void testSaveDeliveryDetails() throws JsonMappingException, JsonProcessingException, MessagingException {
		DeliveryRequestDto deliveryDto=new DeliveryRequestDto();
		Random random=new Random();
		List<String> deliveryPersons = new ArrayList<>();
		deliveryDto.setCustomerId(1L);
		deliveryDto.setRestaurantId(1L);
		deliveryDto.setStatus("In process");
		String deliveryPerson=deliveryServiceImpl.assignDeliveryPerson();
		Delivery delivery=new Delivery();
		delivery.setCustomerId(deliveryDto.getCustomerId());
		delivery.setId(1);
		delivery.setRestaurantId(deliveryDto.getRestaurantId());
		delivery.setStatus(deliveryDto.getStatus());
		delivery.setDeliveryPerson(deliveryPerson);
		String message="sending email";
		deliveryServiceImpl.outForDelivery(message);
		when(deliveryRepository.save(delivery)).thenReturn(delivery);	
		assertNotNull(delivery);
	}
	
	@Test
	void testFetchDeliveryDetails() throws DeliveryIdNotFounException {
		Delivery delivery=new Delivery();
		delivery.setCustomerId(1L);
		delivery.setId(1);
		delivery.setRestaurantId(1L);
		delivery.setStatus("In processing");
		delivery.setDeliveryPerson("Padmini");
		when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));
		assertEquals(deliveryServiceImpl.fetchDeliveryDetails(1), delivery);
	}

	@Test
	void testDeliverFood() throws DeliveryIdNotFounException, MessagingException, JsonMappingException, JsonProcessingException {
		Delivery delivery=new Delivery();
		delivery.setCustomerId(1L);
		delivery.setId(1);
		delivery.setRestaurantId(1L);
		delivery.setStatus("In processing");
		delivery.setDeliveryPerson("Padmini");
		when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));
		when(deliveryRepository.saveAndFlush(delivery)).thenReturn(delivery);
		assertEquals(deliveryServiceImpl.deliverFood("1"), "Status updated successfully");
	}
	

	@Test
	public void testDeliveryPresent() {
		Delivery delivery=new Delivery();
		delivery.setCustomerId(1L);
		delivery.setId(1);
		delivery.setRestaurantId(1L);
		delivery.setStatus("In processing");
		delivery.setDeliveryPerson("Padmini");
		assumeTrue(!(optionalDelivery.isPresent()));
		assertThatThrownBy(() ->deliveryServiceImpl.fetchDeliveryDetails(1))
				.isInstanceOf(DeliveryIdNotFounException.class);
	}
}
