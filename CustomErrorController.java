
package com.project.taxCalc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        // Add error information to the model, if needed
        return "error"; // This will look for a view named "error.html" or "error.jsp"
    }

}
