package in.ashokit.service;

import in.ashokit.binding.ForgotPasswordForm;
import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;

public interface UserDetailsService {
	
	public boolean signup(SignUpForm signUpFrm) throws Exception;
	
	public boolean unlockAccount(UnlockForm unlockFrm);
	
	public String loginAccount(LoginForm LoginFrm);
	
	public boolean forgotPassword(ForgotPasswordForm forgotPasswordFrm) throws Exception;
}
