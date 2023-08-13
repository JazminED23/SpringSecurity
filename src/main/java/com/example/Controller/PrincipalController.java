package com.example.Controller;

import com.example.Models.ERole;
import com.example.Models.RoleEntity;
import com.example.Models.UserEntity;
import com.example.Repositories.UserRepository;
import com.example.request.CreateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController

public class PrincipalController {
    @Autowired
    private UserRepository userRepository;
@GetMapping("/hello")
    public String hello(){
    return "Hola Mundo not secured";
}
@GetMapping("/helloSecured")
    public String helloSecured(){
        return "Hola Mundo Secured";
}
@PostMapping("/createUser")
        public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO){
            Set<RoleEntity> roles=createUserDTO.getRoles().stream()
            .map(role->RoleEntity.builder().name(ERole.valueOf(role))
                    .build())
            .collect(Collectors.toSet());
            UserEntity userEntity=UserEntity.builder()
            .username(createUserDTO.getUsername())
            .password(createUserDTO.getPassword())
            .email(createUserDTO.getEmail())
            .roles(roles)
            .build();
            userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
}
        @DeleteMapping("/deleteUser")
        public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id".concat(id);
        }

}
