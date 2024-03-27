package com.example.demo.service.test;

import com.example.demo.entities.Order;
import com.example.demo.model.OrderRequest;
import com.example.demo.model.OrderResponse;
import com.example.demo.model.OrderStatus;
import com.example.demo.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest {

    @Autowired
    OrderService orderService;


    @Test
    public void testUpdateOrder() throws Exception {

        //Create a new Order
        OrderRequest request = new OrderRequest();
        request.setOrigin(new String[]{"37.7749","-122.4194"});
        request.setDestination(new String[]{"34.0522","-118.2437" });
        OrderResponse response = orderService.saveOrder(request);

        //Update the order
        OrderStatus newStatus  = new OrderStatus();
        newStatus.setStatus("TAKEN");

        orderService.updateOrderStatus(response.getId(),newStatus) ;

        //Get list of orders
        Page<OrderResponse> page = orderService.getAllOrders(1,1);
        assertEquals(1, page.getContent().size());

        List<OrderResponse> orders = page.getContent();

        Optional<OrderResponse> resp = orders.stream().filter(orderResponse -> orderResponse.getId()==response.getId()).findFirst();
        assertEquals("SUCCESS", resp.get().getStatus());



    }

}
