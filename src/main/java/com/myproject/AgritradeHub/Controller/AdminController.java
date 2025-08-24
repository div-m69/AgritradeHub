package com.myproject.AgritradeHub.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myproject.AgritradeHub.API.SendAutoEmail;
import com.myproject.AgritradeHub.Model.AllUsers;
import com.myproject.AgritradeHub.Model.AllUsers.UserRole;
import com.myproject.AgritradeHub.Model.AllUsers.UserStatus;
import com.myproject.AgritradeHub.Model.Category;
import com.myproject.AgritradeHub.Model.Enquiry;
import com.myproject.AgritradeHub.Model.Orders.OrderStatus;
import com.myproject.AgritradeHub.Model.Payment;
import com.myproject.AgritradeHub.Repository.AllUsersRepository;
import com.myproject.AgritradeHub.Repository.CategoryRepository;
import com.myproject.AgritradeHub.Repository.EnquiryRepository;
import com.myproject.AgritradeHub.Repository.OrdersRepository;
import com.myproject.AgritradeHub.Repository.PaymentRepository;
import com.myproject.AgritradeHub.Repository.ProductsRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Admin")
public class AdminController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AllUsersRepository usersRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private EnquiryRepository enquiryRepo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private ProductsRepository productsRepo;
	@Autowired
	private OrdersRepository ordersRepo;
	
	@Autowired
	private SendAutoEmail sendAutoEmail;
	
	@GetMapping("/Dashboard")
	public String showDashboard(Model model) {
		
		if (session.getAttribute("loggedInAdmin")==null){
			return "redirect:/Login";
		}
		
		// ✅ Counts
        model.addAttribute("farmerCount", usersRepo.countByRole(UserRole.FARMER));
        model.addAttribute("merchantCount", usersRepo.countByRole(UserRole.MERCHANT));
        model.addAttribute("productCount", productsRepo.count());
        model.addAttribute("orderCount", ordersRepo.count());
        model.addAttribute("categoryCount", categoryRepo.count());
        model.addAttribute("enquiryCount", enquiryRepo.count());

        // ✅ Recent Enquiries (last 5)
       // List<Enquiry> recentEnquiries = enquiryRepo.findTop5ByOrderByEnquiryDateDesc();
       // model.addAttribute("recentEnquiries", recentEnquiries);

        // Orders by status
        long cancelledOrders = ordersRepo.countByOrderStatus(OrderStatus.CANCELLED); 
        long confirmedOrders = ordersRepo.countByOrderStatus(OrderStatus.CONFIRMED);
        long deliveredOrders = ordersRepo.countByOrderStatus(OrderStatus.DELIVERED);
        
        model.addAttribute("cancelledOrders", cancelledOrders);
        model.addAttribute("confirmedOrders", confirmedOrders);
        model.addAttribute("deliveredOrders", deliveredOrders); 
		
		
		
		return "Admin/Dashboard";
	}
	
	@GetMapping("/ManageFarmers")
	public String showManageFarmers(Model model)
	{ 
		if (session.getAttribute("loggedInAdmin")==null) {
			return "redirect:/Login";
		}
		
		List<AllUsers> farmerList = usersRepo.findAllByRole(UserRole.FARMER);
		model.addAttribute("farmerList", farmerList);
		return "Admin/ManageFarmers";
	}
	
	@GetMapping("/ManageMerchant")
	public String showManageMerchants(Model model)
	{
		if (session.getAttribute("loggedInAdmin")==null) {
			return "redirect:/Login";
		}
		
		List<AllUsers> merchantList = usersRepo.findAllByRole(UserRole.MERCHANT);
		model.addAttribute("merchantList", merchantList);
		return "Admin/ManageMerchant"; 
		 
	}
	
	@GetMapping("/UpdateUserStatus")
	public String updateUserStatus(@RequestParam long id, RedirectAttributes attributes) {
	    try {
	        AllUsers user = usersRepo.findById(id).get();

	        if (user.getStatus().equals(UserStatus.PENDING)) {
	            user.setStatus(UserStatus.VERIFIED);
	            
	        } else if (user.getStatus().equals(UserStatus.VERIFIED)) {
	            user.setStatus(UserStatus.DISABLED);
	        } else { 
	            user.setStatus(UserStatus.VERIFIED);
	        }

	        usersRepo.save(user);
	        sendAutoEmail.sendApprovalEmail(user); 

	        attributes.addFlashAttribute("msg", user.getName() + " status successfully updated");
	        if ("FARMER".equals(user.getRole().toString())) {
	            return "redirect:/Admin/ManageFarmers";
	        } else if ("MERCHANT".equals(user.getRole().toString())) {
	            return "redirect:/Admin/ManageMerchant";
	        } 
	    } catch (Exception e) {
	        attributes.addFlashAttribute("msg", e.getMessage());
	        return "redirect:/Admin/Dashboard";
	    }
	    return "redirect:/Admin/Dashboard";
	}
    
	@GetMapping("/enquiry")
    public String ShowEnquiry(Model model) {
		if (session.getAttribute("loggedInAdmin")==null) {
			return "redirect:/Login";
		}
		List<Enquiry> enqList = enquiryRepo.findAll();
		model.addAttribute("enqList", enqList );
		return "Admin/enquiry"; 
    }
	
	
	@GetMapping("/ViewOrder")
    public String ShowViewOrder(Model model) {
		if (session.getAttribute("loggedInAdmin")==null) {
			return "redirect:/Login";
		}
		
		List<Payment> payList = paymentRepo.findAll();

	    model.addAttribute("payList", payList);
	    return "Admin/ViewOrder"; 
    }
	
	
	
	
	@GetMapping("/AddCategory")
	public String showAddCategory(Model model)
	{
		if (session.getAttribute("loggedInAdmin")==null) {
			return "redirect:/Login";
		}
		
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		
		return "Admin/AddCategory";
	}
	
	@PostMapping("/AddCategory")
	public String AddCategory( @RequestParam("categoryName") String categoryName, RedirectAttributes attributes) {
		try {
			Category cat = new Category();
			cat.setCategoryName(categoryName);
			categoryRepo.save(cat);
			attributes.addFlashAttribute("msg", "category Successfully Added");
			
			return "redirect:/Admin/AddCategory";
		} catch (Exception e) {
			attributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/Admin/AddCategory";
		}
		
	} 
	
	@GetMapping("/DeleteCategory")
	public String DeleteCategory(@RequestParam long id) {
		
		categoryRepo.deleteById(id);
		return "redirect:/Admin/AddCategory";
	}
	
	
	@GetMapping("/DeleteEnquiry")
	public String DeleteEnquiry(@RequestParam long id) {
		
		enquiryRepo.deleteById(id);
		return "redirect:/Admin/enquiry";
	}
	
	
	
	@GetMapping("/ChangePassword")
	public String showChangePassword() {
		
		if (session.getAttribute("loggedInAdmin")==null){
			return "redirect:/Login";
		}
		
		return "Admin/ChangePassword";
	}
	
	
	@PostMapping("/ChangePassword")
	public String ChangePassword(HttpServletRequest request, RedirectAttributes attributes)
	{
		try {
			
			String oldPassword = request.getParameter("oldPassword");
			
			String newPassword = request.getParameter("newPassword");
			
			String confirnPassword = request.getParameter("confirmPassword");
			
			if (!newPassword.equals(confirnPassword)) {
				attributes.addFlashAttribute("msg", "New and Confirm Password Must Be Same!!!");
				return "redirect:/Admin/ChangePassword";
				
			}
			
			AllUsers merchant = (AllUsers) session.getAttribute("loggedInAdmin");
			
			if (newPassword.equals(merchant.getPassword())) {
				attributes.addFlashAttribute("msg", "Can't Change New and Old Password Are same!!");
				return "redirect:/Admin/ChangePassword";
			}
			
			if (oldPassword.equals(merchant.getPassword())) {
				
				merchant.setPassword(confirnPassword);
				usersRepo.save(merchant);
				session.removeAttribute("loggedInAdmin");
				attributes.addFlashAttribute("msg", "Password SuccessFully Changed!!");
				
				return "redirect:/Login";
				
			}else {
				attributes.addFlashAttribute("msg", "Invailid Old Password!!");
			}
			
			return "redirect:/Admin/ChangePassword"; 
		} catch (Exception e) {
			return "redirect:/Admin/ChangePassword"; 
		}
		
	}
	
	
	@GetMapping("/logout")
	public String logout()
	{
		session.removeAttribute("loggedInAdmin");
		return "redirect:/Login";
	}
	
	
}
