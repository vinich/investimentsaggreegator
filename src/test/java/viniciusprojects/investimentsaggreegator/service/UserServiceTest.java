package viniciusprojects.investimentsaggreegator.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import viniciusprojects.investimentsaggreegator.controller.CreateUserDTO;
import viniciusprojects.investimentsaggreegator.controller.UpdateUserDTO;
import viniciusprojects.investimentsaggreegator.entity.User;
import viniciusprojects.investimentsaggreegator.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    @Captor
    private ArgumentCaptor<UUID> UUIDArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with sucess")
        void shouldCreateUser() {

            var user = new User(UUID.randomUUID(),
                            "username",
                               "email@gmail.com",
                            "password",
                                     Instant.now(),
                            null);

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDTO("userName", "email", "password");

            var output = userService.createUser(input);

            var userCaptured = userArgumentCaptor.getValue();

            assertNotNull(output);
            assertEquals(input.userName(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowException() {
            var user = new User(UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null);

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO("userName", "email", "password");

            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }

    }

    @Nested
    class getUser {

        @Test
        @DisplayName("Should successfully get a user when optional is present")
        void shouldGetUserWhenOptionalIsPresent() {
            var user = new User(UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null);

            doReturn(Optional.of(user)).when(userRepository).findById(UUIDArgumentCaptor.capture());

            var output = userService.getUserById(user.getId().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getId(), UUIDArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should successfully get a user when optional is empty")
        void shouldGetUserWhenOptionalIsEmpty() {

            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(UUIDArgumentCaptor.capture());

            var output = userService.getUserById(userId.toString());

            assertTrue(output.isEmpty());
            assertEquals(userId, UUIDArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Should list all users with success")
        void shouldReturnAllUsersWithSuccess() {

            var user = new User(UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null);

            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();

            var output = userService.listUsers();

            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteUserById {

        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists() {
            doReturn(true).when(userRepository).existsById(UUIDArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(UUIDArgumentCaptor.capture());

            var userID = UUID.randomUUID();
            userService.deleteUserById(userID.toString());

           var idList = UUIDArgumentCaptor.getAllValues();
           assertEquals(userID, idList.getFirst());
           assertEquals(userID, idList.get(1));

           verify(userRepository, times(1)).existsById(idList.getFirst());
           verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should not delete user with success when user not exists")
        void shouldNotDeleteUserWithSuccessWhenUserNotExists() {
            doReturn(false).when(userRepository).existsById(UUIDArgumentCaptor.capture());

            var userID = UUID.randomUUID();
            userService.deleteUserById(userID.toString());

            assertEquals(userID, UUIDArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(UUIDArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());
        }

    }

    @Nested
    class updateUser {

        @Test
        @DisplayName("Should update user by id when user exists and username and password is filled")
        void shouldUpdateByIdWhenUserExistsAndUserNameAndPasswordIsFilled() {

            var user = new User(UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null);

            var updateUserDTO = new UpdateUserDTO("newUsername",  "newPassword");

            doReturn(Optional.of(user)).when(userRepository).findById(UUIDArgumentCaptor.capture());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.updateUserById(user.getId().toString(), updateUserDTO);

            assertEquals(user.getId(), UUIDArgumentCaptor.getValue());
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(updateUserDTO.userName(), userCaptured.getUsername());
            assertEquals(updateUserDTO.password(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(UUIDArgumentCaptor.capture());
            verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        }

        @Test
        @DisplayName("Should not update user by id when user not exists")
        void shouldNotUpdateByIdWhenUserNotExists() {

            var userId = UUID.randomUUID();
            var updateUserDTO = new UpdateUserDTO("newUsername",  "newPassword");

            doReturn(Optional.empty()).when(userRepository).findById(UUIDArgumentCaptor.capture());

            userService.updateUserById(userId.toString(), updateUserDTO);

            assertEquals(userId, UUIDArgumentCaptor.getValue());

            verify(userRepository, times(1)).findById(UUIDArgumentCaptor.capture());
            verify(userRepository, times(0)).save(any());
        }
    }
}