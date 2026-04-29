package kz.main.security_orda.service;

import kz.main.security_orda.model.Permission;
import kz.main.security_orda.model.User;
import kz.main.security_orda.repository.PermissionRepository;
import kz.main.security_orda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public Permission getPermission(){
        return permissionRepository.getStandartPermission();
    }


    public void addUser(User user, String repassword) {

        User userFromBase = userRepository.findUserByEmail(user.getEmail());

        if(userFromBase!=null){
            return;
        }

        if(!user.getPassword().equals(repassword)){
            return;
        }

        user.setPermissions(List.of(getPermission()));

        user.setPassword(passwordEncoder.encode(repassword));

        userRepository.save(user);

    }
}
