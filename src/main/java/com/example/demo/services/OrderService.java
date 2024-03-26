package com.example.demo.services;

import com.example.demo.entities.Order;
import com.example.demo.model.OrderRequest;
import com.example.demo.model.OrderResponse;
import com.example.demo.model.OrderStatus;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private GoogleMapIntegration googleService;

    // Save order
    public OrderResponse saveOrder(OrderRequest orderReq) throws Exception {

        Order order = new Order();

        order.setStart_latitude(Double.valueOf(orderReq.getOrigin()[0]));
        order.setStart_longitude(Double.valueOf(orderReq.getOrigin()[1]));

        order.setEnd_latitude(Double.valueOf(orderReq.getDestination()[0]));
        order.setEnd_longitude(Double.valueOf(orderReq.getDestination()[1]));

        // int dist = googleService.findDistance(order.getStart_latitude(), order.getStart_longitude(), order.getEnd_latitude(), order.getEnd_longitude());
        int dist = 10000;
        order.setDistance(dist);
        order.setStatus("UNASSIGNED");
        Order res = orderRepository.save(order);

        OrderResponse response = new OrderResponse();
        response.setId(res.getOrderId());
        response.setDistance(res.getDistance());
        response.setStatus(res.getStatus());

        return response;

    }

    // Fetch all orders
    public Page<OrderResponse> getAllOrders(int page, int limit) throws Exception {
        if (page < 1 || limit < 1) {
            throw new Exception("Page and Limit must be greater than 0");
        } else {
            int zeroBasedPage = Math.max(0, page - 1);
            PageRequest pageable = PageRequest.of(zeroBasedPage, limit);
            Page<Order> entityPage = orderRepository.findAll(pageable);
            return entityPage.map(this::convertToDto);
        }
    }

    private OrderResponse convertToDto(Order order) {
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setDistance(order.getDistance());
        resp.setStatus(order.getStatus());
        return resp;
    }

    @Transactional
    public OrderStatus updateOrderStatus(Long id, OrderStatus status) throws Exception{
        Order order = orderRepository.findById(id).get();
        if (order!= null && status.getStatus().equals("TAKEN")) {
            order.setStatus("SUCCESS");
            status.setStatus("SUCCESS");
            orderRepository.save(order);
        }else{
            throw new Exception("Invalid input:"+status.getStatus());
        }
        return status;
    }
}
