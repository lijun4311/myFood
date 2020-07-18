package com.test;

import com.mhs66.Application;
import com.mhs66.consts.UserConsts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransTest {


    @Test
    public void test() {
        LocalDate a= UserConsts.getDefaultBirhday();
        System.out.printf(a.toString());
    }
}
