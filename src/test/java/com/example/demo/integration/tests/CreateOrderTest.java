package com.example.demo.integration.tests;

import com.example.demo.DemoRestController;
import com.example.demo.entities.Order;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoRestController.class)
@AutoConfigureMockMvc
class CreateOrderTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderRepository repository;
    @Autowired
    private OrderService orderService;

    //Success Case
    @Test
    public void createOrderSuccessTest() throws Exception {
        Order o = new Order();
        o.setId(1L);
        when(repository.save(any())).thenReturn(o);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"origin\": [\n" +
                                "    \"37.7749\",\"-122.4194\"\n" +
                                "  ],\n" +
                                "  \"destination\": [\n" +
                                "   \"34.0522\",\"-118.2437\"\n" +
                                "  ]\n" +
                                "}\n")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    //passing incorrect parameter name origin-error instead of origin
    @Test
    public void createOrderInvalidInputFailedTest() throws Exception {
        Order o = new Order();
        o.setId(1L);
        when(repository.save(any())).thenReturn(o);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"origin-error\": [\n" +
                                "    \"37.7749\",\"-122.4194\"\n" +
                                "  ],\n" +
                                "  \"destination\": [\n" +
                                "   \"34.0522\",\"-118.2437\"\n" +
                                "  ]\n" +
                                "}\n")
                )
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error").isString());

    }

    //Passing more than 2 parameters in Origin
    @Test
    public void createOrderInvalidInputFailedTest2() throws Exception {
        Order o = new Order();
        o.setId(1L);
        when(repository.save(any())).thenReturn(o);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"origin\": [\n" +
                                "    \"37.7749\",\"37.7749\",\"-122.4194\"\n" +
                                "  ],\n" +
                                "  \"destination\": [\n" +
                                "   \"34.0522\",\"-118.2437\"\n" +
                                "  ]\n" +
                                "}\n")
                )
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error").isString());
    }

}
