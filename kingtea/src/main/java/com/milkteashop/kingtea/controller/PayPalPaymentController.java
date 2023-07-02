package com.milkteashop.kingtea.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.dto.ResponseStringDto;
import com.milkteashop.kingtea.model.Order;
import com.milkteashop.kingtea.service.OrderService;
import com.milkteashop.kingtea.service.PayPalPaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/paypal")
public class PayPalPaymentController {
	@Autowired
	private PayPalPaymentService service;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public ResponseEntity<Object> payment(@RequestBody Order order, HttpServletRequest request) {
		String baseUrl = String.format("%s://%s:%d",request.getScheme(),  request.getServerName(), request.getServerPort());
		ResponseStringDto response = new ResponseStringDto();
		try { 
			Payment payment = service.createPayment(Double.valueOf(order.getTotalPrice()*0.000043), "USD", order.getNote(), 
					baseUrl + "/milkteashop/kingtea/paypal/pay/success/" + order.getOrderId(), 
					baseUrl + "/milkteashop/kingtea/paypal/pay/cancel");
			for (Links link:payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					response.setMessage(link.getHref());
					return ResponseEntity.ok(response); 
				}
			}
		} catch(PayPalRESTException e) {
			e.printStackTrace();
		}
		
		response.setMessage("");
		return ResponseEntity.ok(response); 
	}
	
	@RequestMapping(value = "/pay/cancel", method = RequestMethod.GET)
    public ResponseEntity<Object> cancelPay() throws URISyntaxException {
		// Open another url
		URI sandbox = new URI("https://www.checkoutpage.com"); 
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(sandbox);
	    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
	
	@RequestMapping(value = "/pay/success/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, 
    		@PathVariable("id") String orderId) throws URISyntaxException {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
            	Order order = orderService.getOrder(orderId);
            	order.setState("Waiting Accept");
            	orderService.saveOrder(order);
            }
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
        }
        URI sandbox = new URI("https://www.checkoutpage.com"); 
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(sandbox);
	    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
	
}
