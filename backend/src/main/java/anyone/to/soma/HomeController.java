package anyone.to.soma;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
