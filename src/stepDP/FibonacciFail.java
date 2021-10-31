package stepDP;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FibonacciFail {
    /**
     * 입력1: T. test case 개수
     * 입력2: 1~T 줄
     *
     * 이력
     * - count 정보는 따로 관리. 재귀는 n만 넘기고.
     * - 시간초과
     * - TODO 메모리 초과 - memoization 시도
     *
     * 문제
     * - {n:count[2]} 인 TODO map 에 put 시, 조건이 부족하다
     *      아직 계산이 이뤄지지 않은 n 값에 대해서는 put 하지 말아야 하는데, 그걸 어떻게 알지??
     * - 기존 key 값을 TODO 덮어씌우지 않도록 했는데 덮어씌운다.
     *      memo.containsKey(n) 가 원하는대로 동작하지 않는다?
     *      혹은 덮어씌우는 것처럼 보이지만 다른 문제?
     *
     * 구현 (의도. 실제로는 Error)
     * - FibonacciCount 에서 각 Fibonacci 계산 별 count 누적값, 메모 관리
     * - fibonacciCount 는 하나만 생성하고, 다음 입력값 계산시 누적값과 메모를 초기화한다
     * - 메모는 n을 key 로, count[] 를 value 로 한다.
     * - count[] 는 n이 종료조건인 0 or 1 일때 연산횟수를 누적한다.
     * - n이 처음 나오고, TODO 종료조건까지 다 계산이 된 경우 누적된 연산 횟수를 Map 에 넣는다
     *      tip. 종료조건 만나기전까지는 count 가 늘어나지 않는다.
     *      문득: 종료조건을 만나면 ending변수++ ?
     *      문득: Map 에 값을 넣으면 ending변수 초기화 하고?
     * - 예상하는 계산순서: 5 - [4 - [3 - [2 - 1 - 0] - 1] - [2 - 1 - 0]] - 3 -...
     *      계산순서상 더 큰 수가 나중에 나올 수 없다.(내 생각)
     *      다른 Fibonacci에서 계산했으면 count[] 값이 둘다 0이 아니다.
     *          의문: fibonacciCount 를 꼭 싱글톤으로 운영해야 하나?
     *          문득: 영구보존용 싱글톤 하나 두고, TODO 스택별 계속 초기화되는 거 또 하나 만들까?
     *      그런데 계산하기도 전에 넣고
     *      key가 있는데도 넣고?
     *
     *
     *  구현 예정 (수정안)
     *  - map 에 없는 걸 발견할 때마다 (0,1 은 미리 빼고)
     *      임시누적 count[] 에 값을 쌓기 시작
     *      '부재중 flag = k ' 로 갱신하고
     *      '반환전 val = 피보(n-2) + 피보(n-1) ' 로 return 전에 미리 값 받아놓고
     *      '반환전 val == 2*k -3 ' 일 때 (n-2 + n-1)
     *          map 에 해당 k를 key 로 넣고
     *          임시누적 count[] 를 value 로 넣는다
     *      전체누적 count[] 에 임시누적 count[] 를 반영한다 (0,1 각각에 더한다)
     *      임시누적 count[] 를 {0,0} 으로 초기화
     */
    public void Problem1003_2(){

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int inputCount = Integer.parseInt(br.readLine());
            StringBuilder sb = new StringBuilder();

//            재귀 끝나기 전까지는 count 할 공간이 하나만 있어야 하고, 루프 한번 다 돌면 정보가 초기화되어야 한다.
            FibonacciCount fibonacciCount= FibonacciCount.getInstance();
            for (int i = 0; i < inputCount; i++) {

                int n= Integer.parseInt(br.readLine());
                FibonacciMemo(n);
                sb.append(fibonacciCount.toString());
                fibonacciCount.initialize();
            }


            bw.write(String.valueOf(sb));
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }

    int FibonacciMemo(int n) {
        FibonacciCount fibonacciCount= FibonacciCount.getInstance();

//        n값이 반환된 이후에 해당 n은 더이상 재귀에 참여하지 않는다
//        System.err.println("n <0 인 경우는 없다");

        if (n == 0) {
            fibonacciCount.plusCount0();
            return 0;
        }
        if (n == 1) {
            fibonacciCount.plusCount1();
            return 1;
        }


//        memoization - {n:count[]}
        fibonacciCount.generateCountMemo();
        Map<Integer, int[]> memo= fibonacciCount.getCountMemo();

        // Map 에 이미 해당 n 값에 대한 count 정보가 있는 경우. 또다른 종료조건.
        if((memo != null) && memo.containsKey(n)){
            fibonacciCount.addCount(memo.get(n));
            return n;
        }


        // TODO ERROR: 계산이 안 된 key 도 이전 count 누적값으로 put.
        if((memo != null) && !memo.containsKey(n) && fibonacciCount.isNotInitState() ){
            memo.put(n, fibonacciCount.getCount());
        }



        return FibonacciMemo(n - 2) + FibonacciMemo(n - 1);
    }


    public static class FibonacciCount{
        private static FibonacciCount fibonacciCount;
        private Map<Integer, int[]> countMemo;
        private int []count;
        private int []tempCount;

        public static FibonacciCount getInstance(){
            if(fibonacciCount == null){
                fibonacciCount= new FibonacciCount();
            }
            return fibonacciCount;
        }

        public int[] getCount() {
            return count;
        }

        public Map<Integer, int[]> getCountMemo() {
            return countMemo;
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

        /**
         * @return 0,1 종료조건 연산여부
         */
        public boolean isNotInitState(){
            if (count[0]!=0 || count[1]!=0){
                return true;
            }

            return false;
        }

        public void addCount(int[] count) {
            this.count[0] += count[0];
            this.count[1] += count[1];
        }

        public void generateCountMemo(){
            if (countMemo == null){
                countMemo= new HashMap<>();
            }
        }

        public void initialize() {
            count= new int[] {0,0};
            countMemo= new HashMap<>();
        }

        @Override
        public String toString() {
            return count[0] + " " + count[1] + "\n";
        }
    }


    // 이하는 복사하지 않는다.

    /**
     * 인자 뚫어서 다 넘기는건 절차지향
     * @depreciate
     */
    /*public static void Problem1003(){

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
    }*/
}
