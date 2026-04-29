package kz.main.security_orda.conroller;

import kz.main.security_orda.model.User;
import kz.main.security_orda.repository.PermissionRepository;
import kz.main.security_orda.repository.UserRepository;
import kz.main.security_orda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public String getMain(){
        return "index";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/sign-in")
    public String signIn(){
        return "sign-in";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin-page")
    public String getAdminPage(){
        return "admin-page";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/forbidden")
    public String getForbiddenPage(){
        return "forbidden";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/log-out")
    public String getLogOut(){
        return "/sign-in";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/register")
    public String registerPage(){
        return "register";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/register")
    public String getRegister(User user, String repassword){

        userService.addUser(user, repassword);
        return "redirect:/";
    }


}
