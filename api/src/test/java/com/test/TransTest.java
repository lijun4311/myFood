package com.test;

import com.mhs66.Application;
import com.mhs66.consts.SystemConsts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransTest {


    @Test
    public void test() {
        String a=SystemConsts.getPasswordSalt();
        System.out.printf(a);
    }
}
