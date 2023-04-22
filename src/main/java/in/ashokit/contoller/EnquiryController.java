package in.ashokit.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnquiryController {
	
	@GetMapping("/dashboard")
	public String getDashboard() {
		System.out.println("-----------dashboard-- called-------- ");
		return "dashboard";
	}
	
	@GetMapping("/add-enquiry")
	public String getAddEnquiry() {
		System.out.println("-----------add-enquiry---------- ");
		return "add-enquiry";
	}

	@GetMapping("/view-enquiries")
	public String getViewEnquiries() {
		System.out.println("-----------view-enquiries---------- ");
		return "view-enquiries";
	}
	
}
