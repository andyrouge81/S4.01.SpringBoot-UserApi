package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.entities.User;
import cat.itacademy.s04.t01.userapi.exceptions.UserAlreadyExistsException;

import cat.itacademy.s04.t01.userapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
       //given
        User user1 = new User(null, "Ramona", "againstWorld@example.com");
        User user2 = new User(null, "Ramoncin","againstWorld@example.com");

        //when
        when(userRepository.findByEmail("againstWorld@example.com"))
                .thenReturn(Optional.of(user1));

        //then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user2));

        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    void createUser_shouldGenerateAnIdAndSaveUser_whenEmailDoesNotExist(){
        //given
        User user1 = new User(null, "Manolito", "gafotas@example.com");

        //when
        when(userRepository.findByEmail("gafotas@example.com"))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        User result = userService.createUser(user1);

        //then
        assertNotNull(result.getId());

        verify(userRepository).findByEmail("gafotas@example.com");

        verify(userRepository).save(any(User.class));




    }

}