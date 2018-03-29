package com.example;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringBase {


    public static void main(String args[]){
        System.out.println(getEncryptionPhoneNumber("18511077845"));

        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");

//        Collections.shuffle(list);
        Random random = new Random(list.size());
        System.out.println(random.nextInt(list.size()));
        System.out.println(get_random(0,3));

//        System.out.println(list.get(0));
//        System.out.println(convertIp(1428587640));
    }


    public static String getEncryptionPhoneNumber(String phoneNumber){
        if(phoneNumber== null || phoneNumber.length() == 1){
            throw  new IllegalArgumentException("Wrong Number");
        }

        String prefixStr = phoneNumber.substring(0,3);
        String suffix = phoneNumber.substring(phoneNumber.length()-4,phoneNumber.length());

        return prefixStr+"****"+suffix;
    }

    private static String convertIp(int ip) {
        long raw = ip;
        byte[] b = new byte[] { (byte) raw, (byte) (raw >> 8), (byte) (raw >> 16), (byte) (raw >> 24) };
        try {
            return InetAddress.getByAddress(b).getHostAddress();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static  int get_random(int start,int end){
        return (int)(Math.random()*(end-start+1)+start);
    }
}
