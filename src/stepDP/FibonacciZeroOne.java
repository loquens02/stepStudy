package stepDP;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 문제: https://www.acmicpc.net/problem/1003
 * 목적: n번째 피보나치 계산 중 0,1 이 몇번 나왔는지 출력한다
 * 입력: 줄 수, n
 * 예외: 0번째 피보나치는 1 0 을 출력
 *
 * 준비물
 * - TotalCount
 * - TempCount
 * - fibonacci()
 * - countMemo<int, int[]>
 *
*/
public class FibonacciZeroOne {
    public void problem1003(){

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int inputCount = Integer.parseInt(br.readLine());
            StringBuilder sb = new StringBuilder();

            TotalCount totalCount= TotalCount.getInstance();
            for (int i = 0; i < inputCount; i++) {
                int n= Integer.parseInt(br.readLine());
                totalCount.setStartValue(n);  // 뭐로 시작하는 피보나치 재귀인지 판단할 기준

                fibonacci(n);
                sb.append(totalCount.toString()); //toString 필요!
                totalCount.initialize();
            }


            bw.write(String.valueOf(sb));
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }

    /**
     * TODO 구현 예정
     * - map 에 없는 걸 발견할 때마다 (0,1 은 미리 빼고)
     *      임시누적 count[] 에 값을 쌓기 시작
     *      '부재중 flag = k ' 로 갱신하고
     *      '반환전 val = 피보(n-2) + 피보(n-1) ' 로 return 전에 미리 값 받아놓고
     *      '반환전 val == 2*k -3 ' 일 때 (n-2 + n-1)
     *          map 에 해당 k를 key 로 넣고
     *          임시누적 count[] 를 value 로 넣는다
     *      전체누적 count[] 에 임시누적 count[] 를 반영한다 (0,1 각각에 더한다)
     *      임시누적 count[] 를 {0,0} 으로 초기화
     */
    public int fibonacci(int n){
        TotalCount totalCount= TotalCount.getInstance();
        TempCountForMemo tempCountForMemo= TempCountForMemo.getInstance();


        boolean zeroOne= (n==0 || n==1);
        // (하위 0 or 1) then (save in tempCountForMemo)
        if(zeroOne && !(totalCount.isStartZeroOrOne()) ){
            tempCountForMemo.plusOneCount(n);
            return n;
        }
        // 초기 입력이 0 or 1 인 경우, then (save in totalCount)
        else if(zeroOne && totalCount.isStartZeroOrOne()){
            totalCount.plusOneCount(n);
            return n;
        }


//        map 에 있니? - 어디에 두나?



        int fibonacciReturn= fibonacci(n-2) + fibonacci(n-1);


//      (fibonacciReturn == 2*n -3) then

        return fibonacciReturn;
    }



    /**
     * 목적: 0,1 이 몇번 나왔는지에 대한 총 누적 카운트
     * 재귀 depth 에 관계없이 값 유지
     *
     * 종료: 다음 사용자 입력을 위해 출력 후에는 초기화
     */
    public static class TotalCount{
        private static TotalCount totalCount;
        private Map<Integer, int[]> memo;
        private int []count;
        private int startValue;

        /**
         * 시도: getInstance 로 n을 넘기려고 시도 -> static 이라 변경이 안 될 것 같아
         *  -> totalCount static 변수를 다시 초기화할 깔끔한 방법은 없다
         *  https://stackoverflow.com/a/7979350
         */
        public static TotalCount getInstance(){
            if(totalCount == null){
                totalCount= new TotalCount();
            }
            return totalCount;
        }

        private TotalCount() {
            initialize();
        }

        /**
         * 사용자의 여러 입력에 대응하기 위함
         */
        public void initialize() {
            count= new int[] {0,0};
            memo= new HashMap<>();  // TODO 정말 초기화되는지 확인필요
            setStartValue(0);
        }

        /**
         * 목적: 시작값을 유지해서 '0 or 1' 판단시, 처음인지 혹은 재귀하위 인지 파악할 기준
         */
        public void setStartValue(int n) {
            this.startValue = n;
        }

        public boolean isStartZeroOrOne(){
            if(startValue==0 || startValue==1){
                return true;
            }
            return false;
        }

        /**
         * 입력: 0 or 1
         */
        public void plusOneCount(int n){
            if(n < 0 || n > 1){
                return;
            }
            count[n] += 1;
        }

        /**
         * 총 누적 += 여태까지 값(임시누적 카운트)
         */
        public void addSoFar(int[] tempCount){
            count[0]+= tempCount[0];
            count[1]+= tempCount[1];
        }

        @Override
        public String toString() {
            return count[0] + " " + count[1] + "\n";
        }
    }

    /**
     * 목적: 메모이제이션 별 임시 누적 카운트
     * - 재귀 depth 에 관계없이 어디서나 접근 가능해야 한다
     *
     * 시작: memo keymap 에 해당 n 값이 없을 때, count 시작
     * 종료: memo 맵에 count 넣고 난 직후, 초기화.
     */
    public static class TempCountForMemo{
        private static TempCountForMemo tempCountForMemo;
        private int []count;

        public static TempCountForMemo getInstance(){
            if(tempCountForMemo == null){
                tempCountForMemo= new TempCountForMemo();
            }
            return tempCountForMemo;
        }

        /**
         * 생긴건 이상한데 맞음.
         */
        private TempCountForMemo() {
            initialize();
        }

        /**
         * 전체 누적 카운트에 반영 후에는 초기화
         */
        public void initialize() {
            count= new int[] {0,0};
        }

        /**
         * @param n 0 or 1
         */
        public void plusOneCount(int n){
            if(n < 0 || n > 1){
                return;
            }
            count[n] += 1;
        }
    }
}
