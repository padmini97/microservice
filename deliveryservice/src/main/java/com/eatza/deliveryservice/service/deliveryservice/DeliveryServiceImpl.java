package com.eatza.deliveryservice.service.deliveryservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eatza.deliveryservice.dto.CustomerDto;
import com.eatza.deliveryservice.dto.DeliveryRequestDto;
import com.eatza.deliveryservice.exception.DeliveryIdNotFounException;
import com.eatza.deliveryservice.model.Delivery;
import com.eatza.deliveryservice.proxy.CustomerProxy;
import com.eatza.deliveryservice.repository.DeliveryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	

	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private CustomerProxy customerProxy;

	private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

	private ObjectMapper objectMapper = new ObjectMapper();
	

	@KafkaListener(topics = "deliveryTopic", groupId = "group_json")
	public String deliverFood(String byteData)
			throws JsonMappingException, JsonProcessingException, MessagingException {
		logger.debug("In place delivery method, creating delivery object to persist");
		DeliveryRequestDto deliveryDto = objectMapper.readValue(byteData, DeliveryRequestDto.class);
		String deliveryPerson = assignDeliveryPerson();
		Delivery delivery = new Delivery();
		delivery.setCustomerId(deliveryDto.getCustomerId());
		delivery.setRestaurantId(deliveryDto.getRestaurantId());
		delivery.setStatus("In Processing");
		delivery.setDeliveryPerson(deliveryPerson);
		deliveryRepository.save(delivery);
		String message = "Dear Customer," + System.lineSeparator()
				+ " your order have been placed successfully CustomerId: " + deliveryDto.getCustomerId()
				+ " with RestaurantId: " + deliveryDto.getRestaurantId() + ". Will be delivered by  " + deliveryPerson
				+ System.lineSeparator() + System.lineSeparator() + ". Enjoy your food";
		outForDelivery(message);
		return "Delivery details are stored successfully";
	}

	public String assignDeliveryPerson() {
		logger.debug("Assigning Delivery Person randomly");
		List<String> deliveryPersons = new ArrayList<>();
		deliveryPersons.add("Nithin");
		deliveryPersons.add("Kushal");
		deliveryPersons.add("Deepan");
		deliveryPersons.add("Padmini");
		deliveryPersons.add("Achyutha");
		Random random=new Random();
		return deliveryPersons.get(random.nextInt(deliveryPersons.size()));
	}

	public void outForDelivery(String message) throws MessagingException {
		logger.debug("Email to that particular person");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("rcb9731@gmail.com", "SeethaRaama@1");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rcb9731@gmail.com"));
		msg.setSubject("Order Status");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();

		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		MimeBodyPart attachPart = new MimeBodyPart();
		msg.setContent(multipart);
		Transport.send(msg);
	}

	public Delivery fetchDeliveryDetails(int deliveryId) throws DeliveryIdNotFounException {
		logger.debug("Fetching the details of delivery");
		Optional<Delivery> deliveryDetails = deliveryRepository.findById(deliveryId);
		if (deliveryDetails.isPresent()) {
			return deliveryDetails.get();
		} else {
			logger.debug("No delivery found for given Id");
			throw new DeliveryIdNotFounException("Delivery Id not found");
		}
	}

	@Override
	public String deliverFood(int deliveryId)
			throws DeliveryIdNotFounException, MessagingException {
		Optional<Delivery> deliveryDetails = deliveryRepository.findById(deliveryId);
		if (!(deliveryDetails.isPresent())) {
			throw new DeliveryIdNotFounException("Delivery Id not found");
		}
		deliveryDetails.get().setId(deliveryId);
		deliveryDetails.get().setStatus("Food Delivered");
		deliveryRepository.saveAndFlush(deliveryDetails.get());
		String deliveryPerson = assignDeliveryPerson();
		String message = "Dear Customer," + System.lineSeparator() + " your order have been delivered with CustomerId: "
				+ deliveryDetails.get().getCustomerId() + " and RestaurantId: " + deliveryDetails.get().getRestaurantId()
				+ ". Food is delivered by  " + deliveryPerson + System.lineSeparator() + ". Enjoy your food. "+System.lineSeparator() + "Why so hungry get some yummy with Eatzaaa.";
		outForDelivery(message);
		return "Status updated successfully";
		
	}
//
//	@Override
//	public CustomerDto getCustomerDetails(int deliveryId) throws DeliveryIdNotFounException {
//		Optional<Delivery> deliveryDetails = deliveryRepository.findById(deliveryId);
//		if (!(deliveryDetails.isPresent())) {
//			throw new DeliveryIdNotFounException("Delivery Id not found");
//		}
//		return customerProxy.getCustomerById(deliveryDetails.get().getCustomerId()).getBody();
//	}

}
