package in.ashokit.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.ForgotPasswordForm;
import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.service.UserDetailsService;

@Controller
public class UserDetailsController {

	@Autowired
	UserDetailsService service;

	@GetMapping("/signup")
	public String getSignup(Model model) {
		model.addAttribute("signUpFrm", new SignUpForm());
		System.out.println("-----------signup---------- ");
		return "signup";
	}

	@PostMapping("/signup")
	public String saveSignup(@ModelAttribute("signUpFrm") SignUpForm signUpFrm, Model model) throws Exception {
		System.out.println("-----------saveSignup---------- ");

		System.out.println("From UserDetailsController name==" + signUpFrm.getName());
		System.out.println("From UserDetailsController PhoneNumber==" + signUpFrm.getPhoneNumber());
		System.out.println("From UserDetailsController Email==" + signUpFrm.getEmail());

		boolean bSave = service.signup(signUpFrm);
		System.out.println("From UserDetailsController bSave==" + bSave);

		model.addAttribute("signUpFrm", new SignUpForm());

		if (bSave) {
			model.addAttribute("succMsg", "Check Your Mail");

		} else {
			model.addAttribute("errMsg", "Email already exists. Please choose another email.");
		}

		return "signup";
	}

	@GetMapping("/unlock")
	public String getUnlock(@RequestParam String email, Model model) {
		System.out.println("-----------unlock----------email== " + email);
		UnlockForm unlockFrm = new UnlockForm();
		unlockFrm.setEmail(email);

		model.addAttribute("unlockFrm", unlockFrm);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockAccount(@ModelAttribute("unlockFrm") UnlockForm unlockFrm, Model model) {

		if(!unlockFrm.getNewPasswrod().equals(unlockFrm.getConfirmPasswrod())) {
			model.addAttribute("errMsg", "New Password and Confirm Password must be same.");
			System.out.println("-----------not equals---------- ");
		}else {
			System.out.println("-----------equals---------- ");
			boolean sUnlockStatus = service.unlockAccount(unlockFrm);
			System.out.println("-----------sUnlockStatus---------- "+sUnlockStatus);
			if(sUnlockStatus) {
				model.addAttribute("succMsg", "Your Account Unlocked Successfully.");
			}else {
				model.addAttribute("errMsg", "Invalid Temporary Password. Please check mail.");
			}
			
		}
		
		model.addAttribute("unlockFrm",unlockFrm);
		
		return "unlock";
	}

	
	@GetMapping("/login")
	public String getLogin(Model model) {
		System.out.println("-----------getLogin---------- ");
		model.addAttribute("LoginFrm", new LoginForm());
		
		return "login";
	}
	
	@PostMapping("/login")
	public String accountLogin(@ModelAttribute("LoginFrm") LoginForm LoginFrm, Model model) {
		System.out.println("-----------accountLogin---------- LoginFrm=="+LoginFrm);
		
		String sLoginStatus = service.loginAccount(LoginFrm);
		System.out.println("-----------accountLogin---------- sLoginStatus=="+sLoginStatus);
		
		if(sLoginStatus.contains("success")) {
			return "redirect:/dashboard";
		}
		
		if(sLoginStatus.contains("Invalid Credentials")) {
			model.addAttribute("errMsg", "Invalid Credentilas.");
		}
		
		
		if(sLoginStatus.contains("Your Account Locked")) {
			model.addAttribute("errMsg", "Your Account Locked.");
		}		
		return "login";
	}
	
	@GetMapping("/forgot")
	public String getForgot(Model model) {
		System.out.println("-----------forgot---------- ");
		model.addAttribute("ForgotPwdForm", new ForgotPasswordForm());
		return "forgot";
	}
	
	
	@PostMapping("/forgot")
	public String getForgot(@ModelAttribute("ForgotPwdForm") ForgotPasswordForm forgotPwdForm, Model model) throws Exception {
		System.out.println("-----------getForgot---------- forgotPwdForm=="+forgotPwdForm);
		boolean bForgotPwd = service.forgotPassword(forgotPwdForm);
		System.out.println("-----------getForgot---------- bForgotPwd=="+bForgotPwd);
		if(bForgotPwd) {
			model.addAttribute("succMsg", "Password sent to your Registered Email.");
		}else {
			model.addAttribute("errMsg", "Entered Email not Registered. Please Enter correct Email.");
		}
		
		return "forgot";
	}
	
	
}
