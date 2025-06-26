package viniciusprojects.investimentsaggreegator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viniciusprojects.investimentsaggreegator.entity.User;
import viniciusprojects.investimentsaggreegator.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody CreateUserDTO user) {
        return ResponseEntity.created(URI.create("/v1/users/" + userService.createUser(user))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        var user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable String id, @RequestBody UpdateUserDTO user) {
        userService.updateUserById(id, user);
        return ResponseEntity.noContent().build();
    }
}
