package com.cxp.week01;

import java.util.Arrays;

public class Hello {
    public static int maxProfit(int[] ps) {
        int[] prices = new int[ps.length + 1];
        for (int i = 1; i < prices.length; i ++) {
            prices[i] = ps[i - 1];
        }
        int[][][]dp = new int[prices.length][2][2];
        Arrays.fill(dp[0][0], -1000000);
        Arrays.fill(dp[0][1], -1000000);
        dp[0][0][0] = 0;

        for (int i = 1; i < prices.length; i ++) {
            Arrays.fill(dp[i][0], -1000000);
            Arrays.fill(dp[i][1], -1000000);
            dp[i][1][0] = Math.max(dp[i][1][0], dp[i - 1][0][0] - prices[i]);
            dp[i][0][1] = Math.max(dp[i][0][1], dp[i - 1][1][0] + prices[i]);
            for (int j = 0; j < 2; j ++) {
                for (int l = 0; l < 2; l ++) {
                    dp[i][j][0] = Math.max(dp[i][j][0], dp[i - 1][j][l]);
                }
            }
        }

        return Math.max(dp[prices.length - 1][0][1], dp[prices.length - 1][0][0]);
    }

    public static void main(String[] args) {
        int[] ps = new int[]{1,2,3,0,2};
        System.out.println(maxProfit(ps));
    }
}
