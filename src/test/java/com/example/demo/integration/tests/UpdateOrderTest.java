package com.example.demo.integration.tests;

import com.example.demo.DemoRestController;
import com.example.demo.entities.Order;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoRestController.class)
@AutoConfigureMockMvc
class UpdateOrderTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderRepository repository;

	@Autowired
	private OrderService orderService;

	@Test
	public void updateOrderSuccessTest() throws Exception {
		Order order = new Order();
		order.setOrderId(1L);
		order.setStatus("UNASSIGNED");

		when(repository.findById(1L)).thenReturn(Optional.of(order));
		when(repository.save(order)).then(Answers.RETURNS_SELF);

		mockMvc.perform(patch("/orders/:1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
								"  \"status\": \"TAKEN\"\n" +
								"}") // replace this with the actual JSON body
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").hasJsonPath());
	}

	@Test
	public void updateOrderInvalidTest() throws Exception {
		Order order = new Order();
		order.setOrderId(1L);
		order.setStatus("UNASSIGNED");

		when(repository.findById(1L)).thenReturn(Optional.of(order));
		when(repository.save(order)).then(Answers.RETURNS_SELF);

		mockMvc.perform(patch("/orders/:1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
								"  \"status\": \"RANDOMTEXT\"\n" +
								"}") // replace this with the actual JSON body
				)
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.error").hasJsonPath());
	}



}
