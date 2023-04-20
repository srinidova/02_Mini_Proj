package in.ashokit.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String getIndex() {
		System.out.println("-----------getIndex---------- ");
		return "index";
	}

}
