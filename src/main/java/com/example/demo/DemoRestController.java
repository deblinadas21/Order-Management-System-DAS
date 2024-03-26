package com.example.demo;

import com.example.demo.entities.Order;
import com.example.demo.model.OrderRequest;
import com.example.demo.model.OrderResponse;
import com.example.demo.model.OrderStatus;
import com.example.demo.services.GlobalExceptionHandler;
import com.example.demo.services.OrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoRestController {
    @Autowired
    OrderService service;

    @GetMapping("/admin")
    public String test() {
        return "Hello World";
    }

    GlobalExceptionHandler errorHanlder = new GlobalExceptionHandler();

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        try {
            validateOrder(request.getOrigin(),request.getDestination());
            OrderResponse response = new OrderResponse();
            response = service.saveOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorHanlder.handleException(e));
        }
    }

    private void validateOrder(String[] origin, String[] destination) throws Exception {
        if(origin.length != 2 || destination.length !=2){
            throw new Exception("Origin and destination must have only latitude and longitudes");
        }
    }

    @PatchMapping("/orders/:{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderStatus status) throws Exception {
        try {
            OrderStatus response = new OrderStatus();
            response = service.updateOrderStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorHanlder.handleException(e));
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) throws Exception {
        try {
            List<OrderResponse> resp = new ArrayList<OrderResponse>();
            resp = service.getAllOrders(page, limit).getContent();
            return ResponseEntity.ok(resp);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(errorHanlder.handleException(e));
        }
    }


}
