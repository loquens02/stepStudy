package stepDP;

import java.io.*;

public class DynamicSilver {
    /**
     * 입력1: T. test case 개수
     * 입력2: 1~T 줄
     *
     * count 정보는 따로 관리. 재귀는 n만 넘기고.
     * 시간초과 ㅜㅜ
     */
    public static void Problem1003_2(){

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int inputCount = Integer.parseInt(br.readLine());
            StringBuilder sb = new StringBuilder();

            FibonacciCount fibonacciCount= FibonacciCount.getInstance();

            for (int i = 0; i < inputCount; i++) {
                int n= Integer.parseInt(br.readLine());
                FibonacciMemo(n);
                sb.append(fibonacciCount.toString());
            }


            bw.write(String.valueOf(sb));
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }

    static int FibonacciMemo(int n) {
        if (n < 0) {
            return n;
        }
        FibonacciCount fibonacciCount= FibonacciCount.getInstance();

        if (n == 0) {
            fibonacciCount.plusCount0();
            return 0;
        }
        else if (n == 1) {
            fibonacciCount.plusCount1();
            return 1;
        }

//        memoization ?
        if(n - 2 ==0){ //&& (n - 1 ==1)
            fibonacciCount.plusCount0();
            fibonacciCount.plusCount1();
            return -1;
        }
//        else if(n - 2 ==1){
//            fibonacciCount.plusCount1();
//            return -1;
//        }

        return FibonacciMemo(n - 1) + FibonacciMemo(n - 2);
    }


    public static class FibonacciCount{
        private static FibonacciCount fibonacciCount;
        private int []count;

        public static FibonacciCount getInstance(){
            if(fibonacciCount == null){
                fibonacciCount= new FibonacciCount();
            }
            return fibonacciCount;
        }

        private FibonacciCount() {
            count= new int[] {0,0};
        }

        public void plusCount0(){
            count[0]++;
        }
        public void plusCount1(){
            count[1]++;
        }




        @Override
        public String toString() {
            return count[0] + " " + count[1] + "\n";
        }
    }


    /**
     * 인자 뚫어서 다 넘기는건 절차지향
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
        else if (fibonacciInfo.n == 0) {
            fibonacciInfo.count0++;
            return fibonacciInfo;
        }
        else if (fibonacciInfo.n == 1) {
            fibonacciInfo.count1++;
            return fibonacciInfo;
        }

//        memoization ?
        if(fibonacciInfo.n - 2 ==0){ // && (fibonacciInfo.n - 1 ==1)
            fibonacciInfo.count0++;
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
