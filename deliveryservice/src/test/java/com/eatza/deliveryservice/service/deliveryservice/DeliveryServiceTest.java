//package com.eatza.deliveryservice.service.deliveryservice;
//
//import org.mockito.InjectMocks;
//
//public class DeliveryServiceTest {
//	@InjectMocks
//    DeliveryServiceImpl deliveryServiceImpl;
//
// 
//
//    @Mock
//    DeliveryRepository deliveryRepository;
//    
//    @Mock
//    ObjectMapper objectMapper;
//    
//    Optional<Delivery> optionalDelivery = Optional.empty();
//    
//    @Test
//    void testSaveDeliveryDetails() throws JsonMappingException, JsonProcessingException, MessagingException {
//        DeliveryRequestDto deliveryDto=new DeliveryRequestDto();
//        Random random=new Random();
//        List<String> deliveryPersons = new ArrayList<>();
//        deliveryDto.setCustomerId(1L);
//        deliveryDto.setRestaurantId(1L);
//        deliveryDto.setStatus("in process");
//        String deliveryPerson=deliveryServiceImpl.assignDeliveryPerson();
//        Delivery delivery=new Delivery();
//        delivery.setCustomerId(deliveryDto.getCustomerId());
//        delivery.setId(1);
//        delivery.setRestaurantId(deliveryDto.getRestaurantId());
//        delivery.setStatus(deliveryDto.getStatus());
//        delivery.setDeliveryPerson(deliveryPerson);
//        String message="email sending";
//        deliveryServiceImpl.outForDelivery(message);
//        when(deliveryRepository.save(delivery)).thenReturn(delivery);    
//        assertNotNull(delivery);
//    }
//
// @Test
//    void testFetchDeliveryDetails() throws DeliveryIdNotFounException {
//        Delivery delivery=new Delivery();
//        delivery.setCustomerId(1L);
//        delivery.setId(1);
//        delivery.setRestaurantId(1L);
//        delivery.setStatus("in process");
//        delivery.setDeliveryPerson("Gouthami");
//        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));
//        assertEquals(deliveryServiceImpl.fetchDeliveryDetails(1), delivery);
//    }
//
// 
//
//    @Test
//    void testFoodDeliveredToCustomer() throws DeliveryIdNotFounException, MessagingException {
//        Delivery delivery=new Delivery();
//        delivery.setCustomerId(1L);
//        delivery.setId(1);
//        delivery.setRestaurantId(1L);
//        delivery.setStatus("in process");
//        delivery.setDeliveryPerson("Gouthami");
//        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));
//        when(deliveryRepository.saveAndFlush(delivery)).thenReturn(delivery);
//        assertEquals(deliveryServiceImpl.foodDeliveredToCustomer(1), "Status updated successfully");
//    }
//    
//
// 
//
//    @Test
//    public void testDeliveryPresent() {
//        Delivery delivery=new Delivery();
//        delivery.setCustomerId(1L);
//        delivery.setId(1);
//        delivery.setRestaurantId(1L);
//        delivery.setStatus("in process");
//        delivery.setDeliveryPerson("Gouthami");
//        assumeTrue(!(optionalDelivery.isPresent()));
//        assertThatThrownBy(() ->deliveryServiceImpl.fetchDeliveryDetails(1))
//                .isInstanceOf(DeliveryIdNotFounException.class);
//    }
//}
