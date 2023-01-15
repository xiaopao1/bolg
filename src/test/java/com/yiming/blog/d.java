package com.yiming.blog;

import java.util.ArrayList;

class Solution {
    public int findProphet(int n, int[][] trust) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (trust[j][0] == i) {
                    break;
                }else {
                    temp.add(i);
                }
            }
        }
        if(temp.size() == 0) {
            return -1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < temp.size(); j++) {
                if(temp.get(i) != trust[j][1]){
                    break;
                }else {
                    return temp.get(i);
                }
            }
        }


        return -1;
    }
}
