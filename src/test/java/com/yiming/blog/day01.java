package com.yiming.blog;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class day01 {
    public static void main(String[] args) {


        extracted();

    }

    private static boolean extracted() {
        String s = "i have 1 noise and 2 hands and 114 ideas";
//        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<String>();
        for (char c : s.toCharArray()) {
//            String tempStr =
            if(c == ' '){


            }
            if(Character.isDigit(c)){
                temp.add((int) c);
            }
        }
        for (int i = 0; i < temp.size()-1; i++) {
            if(temp.get(i) > temp.get(i+1)){
                return false;
            }
        }
        return true;2
        1 2

    }
}
