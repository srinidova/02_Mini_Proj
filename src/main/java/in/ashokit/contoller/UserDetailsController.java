package in.ashokit.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.SignUpForm;
import in.ashokit.service.UserDetailsService;

@Controller
public class UserDetailsController {
	
	@Autowired
	UserDetailsService service;
	
	@GetMapping("/login")
	public String getIndex() {
		System.out.println("-----------login---------- ");
		return "login";
	}

	@GetMapping("/signup")
	public String getSignup(Model model) {
		model.addAttribute("signUpFrm", new SignUpForm());
		System.out.println("-----------signup---------- ");
		return "signup";
	}
	
	@PostMapping("/signup")
	public String saveSignup(@ModelAttribute("signUpFrm") SignUpForm signUpFrm, Model model) throws Exception {
		System.out.println("-----------saveSignup---------- ");

		System.out.println("From UserDetailsController name=="+signUpFrm.getName());
		System.out.println("From UserDetailsController PhoneNumber=="+signUpFrm.getPhoneNumber());
		System.out.println("From UserDetailsController Email=="+signUpFrm.getEmail());
		
		boolean bSave =  service.signup(signUpFrm);
		System.out.println("From UserDetailsController bSave=="+bSave);
		
		model.addAttribute("signUpFrm", new SignUpForm());
		
		if(bSave) {
			model.addAttribute("succMsg", "Check Your Mail");
			
		}else {
			model.addAttribute("errMsg", "Email already exists. Please choose another email.");
		}
		
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String getUnlock() {
		System.out.println("-----------unlock---------- ");
		return "unlock";
	}
	
	
	@GetMapping("/forgot")
	public String getForgot() {
		System.out.println("-----------forgot---------- ");
		return "forgot";
	}
}
