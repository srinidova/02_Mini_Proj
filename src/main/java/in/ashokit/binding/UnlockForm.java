package in.ashokit.binding;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UnlockForm {

		private String email;
		private String tempPasswrod;
		private String newPasswrod;
		private String confirmPasswrod;
}
