package pl.politechnika.ikms.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.politechnika.ikms.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class CacheTest {

    @Autowired
    private UserService userService;

    @Test
    public void roleCacheTest() throws InterruptedException {
        for(int i = 0 ; i< 100; i++){
            log.info(" :: {}",userService.getRoleByUsername("admin"));
        }
        for(int i = 0 ; i< 100; i++){
            log.info(" :: {}",userService.getRoleByUsername("employee"));
        }
    }
}
