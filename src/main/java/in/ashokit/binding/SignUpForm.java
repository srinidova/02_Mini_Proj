package in.ashokit.binding;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SignUpForm {
	
	private String name;
	private String email;
	private Long phoneNumber;
}
