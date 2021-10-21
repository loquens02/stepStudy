package stepDP;

import java.io.*;

public class DynamicSilver {
    /**
     * 입력1: T. test case 개수
     * 입력2: 1~T 줄
     */
    public static void Problem1003(){

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int inputCount = Integer.parseInt(br.readLine());
            StringBuilder sb = new StringBuilder();


            for (int i = 0; i < inputCount; i++) {
                int n= Integer.parseInt(br.readLine());
                String count01= Fibonacci(new FibonacciInfo(n)).toString();
                sb.append(count01);
            }


            bw.write(String.valueOf(sb));
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }

    static FibonacciInfo Fibonacci(FibonacciInfo fibonacciInfo) {
        if (fibonacciInfo.n < 0) {
            return fibonacciInfo;
        }
        if (fibonacciInfo.n == 0) {
            fibonacciInfo.count0++;
            return fibonacciInfo;
        }
        if (fibonacciInfo.n == 1) {
            fibonacciInfo.count1++;
            return fibonacciInfo;
        }
        FibonacciInfo fibonacciInfo1= new FibonacciInfo(fibonacciInfo.n - 1, fibonacciInfo.count0, fibonacciInfo.count1);
        FibonacciInfo fibonacciInfo2= new FibonacciInfo(fibonacciInfo.n - 2, fibonacciInfo.count0, fibonacciInfo.count1);


        return fibonacciInfo.addTo(Fibonacci(fibonacciInfo1), Fibonacci(fibonacciInfo2));
    }

    public static class FibonacciInfo{
        public int n;
        public int count0;
        public int count1;

        public FibonacciInfo(int n) {
            this.n = n;
            this.count0 = 0;
            this.count1 = 0;
        }
        public FibonacciInfo(int n, int count0, int count1) {
            this.n = n;
            this.count0 = count0;
            this.count1 = count1;
        }

        public FibonacciInfo addTo(FibonacciInfo info1, FibonacciInfo info2){
            return new FibonacciInfo(info1.n+ info2.n, info1.count0+info2.count0, info1.count1+info2.count1);
        }

        @Override
        public String toString() {
            return count0 + " " + count1 + "\n";
        }
    }
}
