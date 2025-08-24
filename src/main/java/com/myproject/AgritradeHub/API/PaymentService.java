package com.myproject.AgritradeHub.API;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;

import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    private RazorpayClient razorpayClient;

    public PaymentService() throws Exception {
        this.razorpayClient = new RazorpayClient("rzp_live_Io1s9ctQtD0G1b", "HO60ThPu65xyvH7ewH5eVcWp");
    }

    public Order createRazorpayOrder(int amount) throws Exception {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", String.valueOf(amount * 100)); // ₹1 = 100 paise, and passed as string
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
        return razorpayClient.orders.create(orderRequest);
    }
    
    
    public Refund refundPayment(String paymentId) throws RazorpayException {
        JSONObject refundRequest = new JSONObject();
        refundRequest.put("payment_id", paymentId);

        // Optional: You can also refund partially with amount
        // refundRequest.put("amount", 5000); // Amount in paise
        System.err.println("Error : "+paymentId);
        return razorpayClient.payments.refund(refundRequest);
    }
}
