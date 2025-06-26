package viniciusprojects.investimentsaggreegator.service;

import org.springframework.stereotype.Service;
import viniciusprojects.investimentsaggreegator.controller.CreateUserDTO;
import viniciusprojects.investimentsaggreegator.controller.UpdateUserDTO;
import viniciusprojects.investimentsaggreegator.repository.UserRepository;
import viniciusprojects.investimentsaggreegator.entity.User;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO){
        var entity = new User(null, createUserDTO.userName(), createUserDTO.email(), createUserDTO.password(), Instant.now(), null);
        return userRepository.save(entity).getId();
    }

    public Optional<User> getUserById(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(String userId){
        var entity = userRepository.findById(UUID.fromString(userId));

        if(entity.isPresent()){
            userRepository.deleteById(UUID.fromString(userId));
        }

    }

    public void updateUserById(String userId, UpdateUserDTO updateUserDTO){

        var userEntity = userRepository.findById(UUID.fromString(userId));

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDTO.userName() != null){
                user.setUsername(updateUserDTO.userName());
            }

            if(updateUserDTO.password() != null){
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }
    }
}
