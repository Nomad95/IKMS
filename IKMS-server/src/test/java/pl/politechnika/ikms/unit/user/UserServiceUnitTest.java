package pl.politechnika.ikms.unit.user;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import pl.politechnika.ikms.domain.user.Role;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceUnitTest {

    private static final Date DEFAULT_DATE = new Date(0L);

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private User defaultUser;

    @Before
    public void createUser(){
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setUsername("user");
        defaultUser.setPassword("user");
        defaultUser.setLastLogged(DEFAULT_DATE);
        defaultUser.setCreatedDate(DEFAULT_DATE);
        defaultUser.setEnabled(true);
        defaultUser.setRole(new Role());
        defaultUser.setEmail("dsd@localost");
    }

    //Wiem ze nie powinienem jednostkowo testować CRUDa ale powiedzmy że to będzie test
    //dzialania abstractow, taka podstawa, ewentualnie sie cos dopisze :)
    @Test
    public void getUser(){
        when(userRepository.findOne(1L)).thenReturn(defaultUser);

        User user = userService.findOne(1L);

        assertEquals(user,defaultUser);
        verify(userRepository).findOne(anyLong());
    }

    @Test
    public void getAllUsers(){
        when(userRepository.findAll()).thenReturn(Lists.newArrayList(defaultUser));

        List<User> all = userService.findAll();

        assertThat(all, hasItem(defaultUser));
        verify(userRepository).findAll();
    }

    @Test
    public void getAllUsersPaginated(){
        ArrayList<User> users = Lists.newArrayList(defaultUser);
        Page<User> pagesMock = Mockito.mock(Page.class);
        when(pagesMock.getContent()).thenReturn(users);
        when(pagesMock.getTotalElements()).thenReturn(1L);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(pagesMock);

        Page<User> pages = userService.findAllPaginated(1, 20, Optional.empty());

        assertThat(pages.getContent(), hasItem(defaultUser));
        assertEquals(pages.getTotalElements(),1L);
        verify(userRepository).findAll(any(Pageable.class));
        verify(pagesMock).getContent();
        verify(pagesMock).getTotalElements();
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateNotExistingEntity(){
        when(userRepository.findOne(anyLong())).thenReturn(null);

        User update = userService.update(defaultUser);
    }

}

