package com.example.demo.integration.tests;

import com.example.demo.DemoRestController;
import com.example.demo.model.OrderResponse;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoRestController.class)
class GetOrdersTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@MockBean
	private OrderRepository repository;

	@Test
	public void getOrdersSuccessTest() throws Exception {

		OrderResponse order1 = new OrderResponse();
		order1.setId(1L);
		OrderResponse order2 = new OrderResponse();
		order2.setId(2L);
		Page<OrderResponse> orders = new PageImpl<>(List.of(order1, order2));

		String expectedResponse = "[{\"id\":1,\"distance\":0,\"status\":null},{\"id\":2,\"distance\":0,\"status\":null}]";

		when(orderService.getAllOrders(1,1)).thenReturn(orders);

		mockMvc.perform(get("/orders?page=1&limit=1")
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedResponse));


	}



}
