package nl.knaw.dans.datatags.service.core.config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Home redirection to swagger api documentation
 */
@Controller
@CrossOrigin
public class HomeController {
    @RequestMapping(value = "/")
    public String index() {
//        System.out.println("swagger-ui.html");
        return "redirect:swagger-ui.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schema")
    public String index(Model model) {
        model.addAttribute("message", "DataTags Schema");
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/about")
    public String about(Model model) {
        model.addAttribute("message", "Todo: About DataTags");
        return "about";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/error")
    public String error(Model model) {
        model.addAttribute("message", "DataTags Schema");
        return "error";
    }

    @GetMapping("/redirectWithRedirectView")
    public RedirectView redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView("http://localhost:8888/dans/v1");
    }

    @GetMapping("/dataTagError")
    String errorHeaven() {
        return "You have reached the haven of errors!!!";
    }

    @GetMapping("/500")
    String error500() {
        return "error500";
    }

    @GetMapping("/400")
    String error400() {
        return "error400";
    }

    @GetMapping("/404")
    String error404() {
        return "error404";
    }
}
