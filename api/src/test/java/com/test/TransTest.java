package com.test;

import com.mhs66.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
public class TransTest {
    @Autowired
    private IUsersService usersService;

    //@Test
    public void test() {
        //System.out.printf(usersService.list().toString());
    }
}
