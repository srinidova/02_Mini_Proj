package in.ashokit.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.ForgotPasswordForm;
import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.entity.UserDetailsEntity;
import in.ashokit.repo.UserDetailsRepo;
import in.ashokit.service.UserDetailsService;
import in.ashokit.utils.EmailUtils;
import in.ashokit.utils.PwdUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDetailsRepo repo;
	
	@Autowired
	EmailUtils emailUtils;
	
	@Override
	public boolean signup(SignUpForm signUpFrm) throws Exception {
		String mailTo = signUpFrm.getEmail();
		String subject = "Unlock Your Account";
		
		UserDetailsEntity existEntity = repo.findByEmail(mailTo);
		
		if(existEntity != null) {
			System.out.println("From Service Impl existEntity=="+existEntity.toString());
			return false;
		}
		
		UserDetailsEntity entity = new UserDetailsEntity();
		BeanUtils.copyProperties(signUpFrm, entity);
		
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPassword(tempPwd);
		entity.setAccountStatus("LOCKED");
		
		repo.save(entity);
		

		
		StringBuffer sBuff = new StringBuffer("");
		sBuff.append("<h1> Use below temporary password to unlock your account </h1>");
		sBuff.append("Temp Pwd: "+tempPwd);
		sBuff.append("<br/>");
		//sBuff.append("<a href=\"http://localhost:8070/unlock?email=\"+mailTo+\"> Click Here to Unlock Your Account </a>");
		sBuff.append("<a href=\"http://localhost:8070/unlock?email="+mailTo+">Click Here to Unlock Your Account</a>");
		
		//<a href="http://localhost:8070/unlock?email="++">Click Here to Unlock Your Account<a>"
		
		
		String body = sBuff.toString();
		
		System.out.println("From Service Impl mailTo=="+mailTo);
		System.out.println("From Service Impl body=="+body);
		System.out.println("From Service Impl subject=="+subject);
		System.out.println("From Service Impl name=="+signUpFrm.getName());
		System.out.println("From Service Impl PhoneNumber=="+signUpFrm.getPhoneNumber());
		
		boolean bMailSent = emailUtils.sendEmail(subject, body, mailTo);
		
		return bMailSent;
	}

	@Override
	public boolean unlockAccount(UnlockForm unlockFrm) {

		UserDetailsEntity existEntity = repo.findByEmail(unlockFrm.getEmail());
		System.out.println("From unlockAccount --------------- existEntity=="+existEntity);
		
		if(existEntity.getPassword().equals(unlockFrm.getTempPasswrod())) {
			System.out.println("From unlockAccount ----------unlock----- ");
			existEntity.setAccountStatus("UNLOCKED");
			existEntity.setPassword(unlockFrm.getNewPasswrod());
			repo.save(existEntity);
			
			return true;
		}else {
			System.out.println("From unlockAccount --------------- existEntity=="+existEntity);
			return false;
		}
		
	}

	@Override
	public String loginAccount(LoginForm LoginFrm) {
		
		UserDetailsEntity existEntity = repo.findByEmailAndPassword(LoginFrm.getEmail(), LoginFrm.getPassword());
		System.out.println("From unlockAccount --------------- existEntity=="+existEntity);
		
		if(existEntity == null) {
			return "Invalid Credentials";
		}
		
		if(existEntity.getAccountStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
		
		return "success";
	}

	@Override
	public boolean forgotPassword(ForgotPasswordForm forgotPasswordFrm) throws Exception {

		UserDetailsEntity existEntity = repo.findByEmail(forgotPasswordFrm.getEmail());
		if(existEntity == null) {
			return false;
		}else {
			String mailTo = forgotPasswordFrm.getEmail();
			String subject = "Your Account Password";
			StringBuffer sBuff = new StringBuffer("");
			sBuff.append("Your Account Password is :"+existEntity.getPassword());
			boolean bMailSent = emailUtils.sendEmail(subject, sBuff.toString(), mailTo);
			return true;
		}

	}

}
