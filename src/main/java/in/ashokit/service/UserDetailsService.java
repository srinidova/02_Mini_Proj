package in.ashokit.service;

import in.ashokit.binding.SignUpForm;
import in.ashokit.entity.UserDetailsEntity;

public interface UserDetailsService {
	
	public boolean signup(SignUpForm signUpFrm) throws Exception;
	
}
