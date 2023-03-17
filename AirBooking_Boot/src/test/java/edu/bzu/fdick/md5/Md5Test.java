package edu.bzu.fdick.md5;

import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class Md5Test {


    @Test
    public void test1(){
        MD5 md5 = new MD5();
        String s = md5.digestHex("123");
        System.out.println(s);


    }

    @Test
    public void test2() {
        String s = UUID.randomUUID().toString();

        System.out.println(s);
    }


}
