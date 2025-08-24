package com.myproject.AgritradeHub.API;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.myproject.AgritradeHub.Model.AllUsers;
import com.myproject.AgritradeHub.Model.Orders;
import com.myproject.AgritradeHub.Model.Payment;

import jakarta.mail.internet.MimeMessage;

@Service
public class SendAutoEmail {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void SendOrderConfirmationEmail(Orders order) 
	{
		// Assuming these types:
		int quantity = order.getQuantity();
		BigDecimal pricePerUnit = order.getPricePerUnit();
		// Convert quantity to BigDecimal
		BigDecimal quantityBD = new BigDecimal(quantity);
		// Multiply quantity * pricePerUnit
		BigDecimal totalPrice = pricePerUnit.multiply(quantityBD);
		
		String subject = "🌿 AgriTrade Hub: Your Order Has Been Confirmed & Is Being Processed";
		String message = "<html>" +
		    "<body style='font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif; background-color: #e8f5e9; margin: 0; padding: 20px;'>" +
		    "  <div style='max-width: 600px; margin: 30px auto; background-color: #ffffff; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,128,0,0.2); padding: 30px;'>" +
		    "    <h1 style='color: #2e7d32; border-bottom: 3px solid #81c784; padding-bottom: 12px; margin-bottom: 30px; font-weight: 700;'>Order Confirmation</h1>" +
		    "    <p style='font-size: 16px; color: #424242;'>Hello <strong>" + order.getMerchant().getName() + "</strong>,</p>" +
		    "    <p style='font-size: 16px; color: #424242;'>Thank you for choosing <span style='color:#388e3c; font-weight: 600;'>AgriTrade Hub</span>. Your order has been <span style='color:#2e7d32; font-weight: 700;'>confirmed</span> and is now being processed.</p>" +
		    "    <h2 style='color: #1b5e20; margin-top: 35px;'>Order Details</h2>" +
		    "    <table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>" +
		    "      <thead>" +
		    "        <tr style='background-color: #a5d6a7;'>" +
		    "          <th style='padding: 14px; border: 1px solid #81c784; text-align: left; color: #1b5e20;'>Product Name</th>" +
		    "          <th style='padding: 14px; border: 1px solid #81c784; text-align: left; color: #1b5e20;'>Quantity</th>" +
		    "          <th style='padding: 14px; border: 1px solid #81c784; text-align: left; color: #1b5e20;'>Price (₹)</th>" +
		    "        </tr>" +
		    "      </thead>" +
		    "      <tbody>" +
		    "        <tr>" +
		    "          <td style='padding: 14px; border: 1px solid #c8e6c9;'>" + order.getProductName() + "</td>" +
		    "          <td style='padding: 14px; border: 1px solid #c8e6c9;'>" + order.getQuantity() + "</td>" +
		    "          <td style='padding: 14px; border: 1px solid #c8e6c9;'>₹" + order.getPricePerUnit() + "</td>" +
		    "        </tr>" +
		    "        <tr style='background-color: #e8f5e9; font-weight: 700;'>" +
		    "          <td colspan='2' style='padding: 14px; border: 1px solid #81c784; text-align: right; color: #2e7d32;'>Total Amount</td>" +
		    "          <td style='padding: 14px; border: 1px solid #81c784; color: #2e7d32;'>₹" + totalPrice + "</td>" +
		    "        </tr>" +
		    "      </tbody>" +
		    "    </table>" +

		    "    <h2 style='color: #1b5e20; margin-top: 40px;'>Farmer Details</h2>" +
		    "    <table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>" +
		    "      <tbody>" +
		    "        <tr style='background-color: #a5d6a7;'>" +
		    "          <th style='padding: 12px; border: 1px solid #81c784; text-align: left; color: #1b5e20;'>Farmer Name</th>" +
		    "          <td style='padding: 12px; border: 1px solid #c8e6c9;'>" + order.getFarmer().getName() + "</td>" +
		    "        </tr>" +
		    "        <tr style='background-color: #e8f5e9;'>" +
		    "          <th style='padding: 12px; border: 1px solid #81c784; text-align: left; color: #1b5e20;'>Contact</th>" +
		    "          <td style='padding: 12px; border: 1px solid #c8e6c9;'>" + order.getFarmer().getContactNumber() + "</td>" +
		    "        </tr>" +
		    "        <tr style='background-color: #a5d6a7;'>" +
		    "          <th style='padding: 12px; border: 1px solid #81c784; text-align: left; color: #1b5e20;'>Email</th>" +
		    "          <td style='padding: 12px; border: 1px solid #c8e6c9;'>" + order.getFarmer().getEmail() + "</td>" +
		    "        </tr>" +
		    "        <tr style='background-color: #e8f5e9;'>" +
		    "          <th style='padding: 12px; border: 1px solid #81c784; text-align: left; color: #1b5e20;'>Location</th>" +
		    "          <td style='padding: 12px; border: 1px solid #c8e6c9;'>" + order.getFarmer().getAddress() + "</td>" +
		    "        </tr>" +
		    "      </tbody>" +
		    "    </table>" +

		    "    <p style='margin-top: 30px; font-size: 16px; color: #424242;'>Your order will be processed and shipped within <strong>2-3 business days</strong>. We will update you once your order is dispatched.</p>" +
		    "    <p style='font-size: 16px; color: #424242;'>For any questions, please contact our support at <a href='mailto:support@agritradehub.com' style='color: #388e3c; font-weight: 600;'>support@agritradehub.com</a> or call <strong>+91-9876543210</strong>.</p>" +
		    "    <br>" +
		    "    <p style='font-size: 16px; color: #2e7d32; font-weight: 700;'>Warm regards,<br>Team AgriTrade Hub 🌱</p>" +
		    "  </div>" +
		    "</body>" +
		    "</html>";

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(order.getMerchant().getEmail());
			helper.setSubject(subject);
			helper.setText(message, true);  // enable html content
			mailSender.send(mimeMessage);
		} catch (Exception e) {
		 System.err.println("error"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void SendOrderCancellationEmail(Orders order, Payment payment) {
	    // Calculate total price
	    BigDecimal totalPrice = order.getPricePerUnit().multiply(new BigDecimal(order.getQuantity()));

	    String subject = "❌ AgriTrade Hub: Your Order Has Been Cancelled - Refund Initiated";
	    String message = "<html>" +
	        "<body style='font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif; background-color: #fff3e0; margin: 0; padding: 20px;'>" +
	        "  <div style='max-width: 600px; margin: 30px auto; background-color: #ffffff; border-radius: 12px; box-shadow: 0 4px 15px rgba(255,152,0,0.2); padding: 30px;'>" +
	        "    <h1 style='color: #e65100; border-bottom: 3px solid #ffb74d; padding-bottom: 12px; margin-bottom: 30px;'>Order Cancellation</h1>" +
	        "    <p style='font-size: 16px; color: #424242;'>Hello <strong>" + order.getMerchant().getName() + "</strong>,</p>" +
	        "    <p style='font-size: 16px; color: #424242;'>We regret to inform you that your order for <strong>" + order.getProductName() + "</strong> has been <span style='color:#d84315; font-weight: bold;'>cancelled</span>.</p>" +
	        "    <h2 style='color: #bf360c; margin-top: 35px;'>Order Summary</h2>" +
	        "    <table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>" +
	        "      <thead>" +
	        "        <tr style='background-color: #ffe0b2;'>" +
	        "          <th style='padding: 14px; border: 1px solid #ffcc80; text-align: left;'>Product Name</th>" +
	        "          <th style='padding: 14px; border: 1px solid #ffcc80; text-align: left;'>Quantity</th>" +
	        "          <th style='padding: 14px; border: 1px solid #ffcc80; text-align: left;'>Price (₹)</th>" +
	        "        </tr>" +
	        "      </thead>" +
	        "      <tbody>" +
	        "        <tr>" +
	        "          <td style='padding: 14px; border: 1px solid #ffe0b2;'>" + order.getProductName() + "</td>" +
	        "          <td style='padding: 14px; border: 1px solid #ffe0b2;'>" + order.getQuantity() + "</td>" +
	        "          <td style='padding: 14px; border: 1px solid #ffe0b2;'>₹" + order.getPricePerUnit() + "</td>" +
	        "        </tr>" +
	        "        <tr style='background-color: #fff8e1; font-weight: 700;'>" +
	        "          <td colspan='2' style='padding: 14px; border: 1px solid #ffcc80; text-align: right;'>Total Amount</td>" +
	        "          <td style='padding: 14px; border: 1px solid #ffcc80;'>₹" + totalPrice + "</td>" +
	        "        </tr>" +
	        "      </tbody>" +
	        "    </table>" +

	        "    <h2 style='color: #bf360c; margin-top: 35px;'>Refund Details</h2>" +
	        "    <table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>" +
	        "      <tbody>" +
	        "        <tr style='background-color: #ffe0b2;'>" +
	        "          <th style='padding: 12px; border: 1px solid #ffcc80; text-align: left;'>Payment Mode</th>" +
	        "          <td style='padding: 12px; border: 1px solid #ffe0b2;'>" + payment.getPaymentMode() + "</td>" +
	        "        </tr>" +
	        "        <tr style='background-color: #fff3e0;'>" +
	        "          <th style='padding: 12px; border: 1px solid #ffcc80; text-align: left;'>Transaction ID</th>" +
	        "          <td style='padding: 12px; border: 1px solid #ffe0b2;'>" + payment.getTransactionId() + "</td>" +
	        "        </tr>" +
	        "        <tr style='background-color: #ffe0b2;'>" +
	        "          <th style='padding: 12px; border: 1px solid #ffcc80; text-align: left;'>Amount</th>" +
	        "          <td style='padding: 12px; border: 1px solid #ffe0b2;'>₹" + payment.getAmount() + "</td>" +
	        "        </tr>" +
	        "        <tr style='background-color: #fff3e0;'>" +
	        "          <th style='padding: 12px; border: 1px solid #ffcc80; text-align: left;'>Payment Date</th>" +
	        "          <td style='padding: 12px; border: 1px solid #ffe0b2;'>" + payment.getPaymentDate().toLocalDate() + "</td>" +
	        "        </tr>" +
	        "      </tbody>" +
	        "    </table>" +

	        "    <p style='margin-top: 30px; font-size: 16px; color: #424242;'>We’ve initiated your refund process. The amount will be refunded to your original payment method within <strong>72 hours</strong>.</p>" +
	        "    <p style='font-size: 16px; color: #424242;'>If you have any questions, contact our support at <a href='mailto:support@agritradehub.com' style='color: #e65100; font-weight: 600;'>support@agritradehub.com</a> or call <strong>+91-9876543210</strong>.</p>" +
	        "    <br>" +
	        "    <p style='font-size: 16px; color: #e65100; font-weight: 700;'>We hope to serve you better next time.<br>Team AgriTrade Hub 🌾</p>" +
	        "  </div>" +
	        "</body>" +
	        "</html>";

	    try {
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setTo(order.getMerchant().getEmail());
	        helper.setSubject(subject);
	        helper.setText(message, true); // HTML content
	        mailSender.send(mimeMessage);
	    } catch (Exception e) {
	        System.err.println("Error sending cancellation email: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	
	public void sendApprovalEmail(AllUsers user) {
	   

	    String subject = "✅ AgriTrade Hub - " + user.getRole() + " Account Approved";
	    String message = "<html>" +
	        "<body style='font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif; background-color: #f1f8e9; margin: 0; padding: 20px;'>" +
	        "  <div style='max-width: 600px; margin: 30px auto; background-color: #ffffff; border-radius: 12px; box-shadow: 0 4px 15px rgba(76,175,80,0.2); padding: 30px;'>" +
	        "    <h2 style='color: #388e3c;'>Congratulations, " + user.getName() + "!</h2>" +
	        "    <p style='font-size: 16px; color: #424242;'>Your " + user.getRole() + " account has been <strong>successfully approved</strong> by AgriTrade Hub Admin.</p>" +
	        "    <p style='font-size: 16px; color: #424242;'>You can now log in using your registered email and password.</p>" +
	        "    <div style='margin: 30px 0; padding: 20px; background-color: #e8f5e9; border-left: 5px solid #43a047;'>" +
	        "      <p style='font-size: 16px; color: #2e7d32;'><strong>Email:</strong> " + user.getEmail() + "<br>" +
	        "      <strong>Login Portal:</strong> <a href='http://192.168.0.141:8080/"+"Login"+"' style='color: #2e7d32; font-weight: 600; font-size:15px;'>http://192.168.0.141:8181/"+"Login"+"</a></p>" +
	        "    </div>" +
	        "    <p style='font-size: 15px; color: #616161;'>If you face any issues, contact support at <a href='mailto:support@agritradehub.com'>support@agritradehub.com</a></p>" +
	        "    <br><p style='font-size: 16px; color: #2e7d32;'>Best Regards,<br>Team AgriTrade Hub 🌾</p>" +
	        "  </div>" +
	        "</body>" +
	        "</html>";

	    try {
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setTo(user.getEmail());
	        helper.setSubject(subject);
	        helper.setText(message, true);  // HTML content
	        mailSender.send(mimeMessage);
	    } catch (Exception e) {
	        System.err.println("Failed to send approval email: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


}
