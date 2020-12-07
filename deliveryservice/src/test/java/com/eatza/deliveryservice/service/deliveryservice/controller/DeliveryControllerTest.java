package com.eatza.deliveryservice.service.deliveryservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eatza.deliveryservice.controller.DeliveryController;
import com.eatza.deliveryservice.model.Delivery;
import com.eatza.deliveryservice.service.deliveryservice.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebMvcTest(value= DeliveryController.class)
class DeliveryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeliveryService deliveryService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@InjectMocks
	DeliveryController deliveryController;
	

	String jwt="";
	private static final long EXPIRATIONTIME = 900000;
	@BeforeEach
	public void setup() {
		jwt = "Bearer "+Jwts.builder().setSubject("user").claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();
	}

	@Test
	void testFetchDelivery() throws Exception {
		Delivery delivery=new Delivery();
		delivery.setCustomerId(1L);
		delivery.setId(1);
		delivery.setRestaurantId(1L);
		delivery.setStatus("in process");
		delivery.setDeliveryPerson("Gouthami");
		when(deliveryService.fetchDeliveryDetails(1)).thenReturn(delivery);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/delivery/fetchDelivery/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((delivery)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
	}

	@Test
	void testDeliverFood() throws Exception {
		Delivery delivery=new Delivery();
		delivery.setCustomerId(1L);
		delivery.setId(1);
		delivery.setRestaurantId(1L);
		delivery.setStatus("in process");
		delivery.setDeliveryPerson("Gouthami");
		when(deliveryService.deliverFood(1)).thenReturn("Status updated successfully");
		RequestBuilder request = MockMvcRequestBuilders.put(
				"/delivery/fetchDelivery/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((delivery)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andReturn();
	}

}
