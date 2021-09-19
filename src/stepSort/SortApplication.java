package stepSort;

import java.io.*;
import java.util.*;
import java.util.stream.DoubleStream;

public class SortApplication {
    /**
     * 단계별-정렬-좌표압축
     * 일단 메모리, 속도 신경쓰지말고 기능만 구현해보자.
     *
     * @link array string to double java https://stackoverflow.com/a/39307801
     * @link java 시간체크 https://hijuworld.tistory.com/2
     * @link current nanoseconds https://www.tutorialspoint.com/java/lang/system_nanotime.htm
     *
     * Stream 반환해서 쓰려고 하면 애로사항이 꽃핀다. List<> 로 반환하는 게 편하다. 그리고 한 번 쓴 stream은 재사용도 안 된다.
     */
    public void Problem18870(){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {



            br.readLine(); // 쓰지 않아서 받고 버림. int inputTotalCount =

//            inputs 을 있는 그대로 받아놔야 출력할 때 순서를 알 수 있다
//            Stream 재사용 불가: IllegalStateException: stream has already been operated upon or closed
            double []inputs= Arrays.stream(br.readLine().split(" "))
                    .mapToDouble(Double::parseDouble).toArray();

            StringBuilder checkSb = new StringBuilder();
            long startTime=System.nanoTime(); // 이거 돌리는데 45200 ns 걸린다.
            checkSb.append("\n inputs 받은 직후: ").append(System.nanoTime()-startTime);


//            inputs 을 중복제외하고 정렬한 뒤,
            double []orderedInputs= Arrays.stream(inputs).distinct().sorted().toArray();
            /*PriorityQueue<Double> minHeap = new PriorityQueue<>();
            for(double each : inputs){
                if(minHeap.contains(each)){ // ABC.contains(A)
                    continue;
                }
                minHeap.add(each);
            }*/

            checkSb.append("\n inputs 중복제거+정렬 직후: ").append(System.nanoTime()-startTime);


//            순서정보 포함된 Map 으로 만들기- 오름차순
//              List 만들어봐야 index 따오려면 기승전 이중 for 문 이라 Dictionary 가 낫겠다 싶음.
//              heap.toArray() 는 순서보장 안 되고, .toList() 도 못 믿겠다. 차피 index 넣으려면 while 돌려야함.
//              peek 이 유지, poll 이 뽑아내는 것 <-> 무한루프
            Map<Double, Integer> orderedInputsMap = new HashMap<>();
            int index= 0;
            for(Double each : orderedInputs){
                orderedInputsMap.put(each, index++);
            }
            /*while(!mineap.isEmpty()){
                ordereHdInputsMap.put(minHeap.poll(), index++);
            }*/

//            System.err.println("반응좀4 "+orderedInputsMap.get(inputs[0]));
            checkSb.append("\n 정렬된 것 map 으로 만든 직후: ").append(System.nanoTime()-startTime);


//            inputs 각각의 index 를 map 에서 찾아오기
            StringBuilder sb = new StringBuilder();
            for (double each : inputs){
                sb.append(orderedInputsMap.get(each)).append(" ");
            }

            bw.write(String.valueOf(sb));

            checkSb.append("\n 출력 직후: ").append(System.nanoTime()-startTime);
            bw.write(String.valueOf(checkSb));


        } catch (IOException io) {
            io.getStackTrace();
        }

    }

    /**
     * 단계별-정렬-통계학
     *
     * 입력 -4000 ~ 4000 정수. 0 가능
     * 총 N개 - 최대 50만개. 홀수 개
     *
     * mean, median, mod, range
     * 11
     * -3999, 0, 0, 0, 1, > 1 <, 1, 2, 2, 2, 4000
     * 3
     * 3, 3, 3
     * 11
     * 2 -4 -4 1 -3 -1 -3 -1 1 -4 2
     *
     * @link java array set all values https://stackoverflow.com/a/9128762
     * @link 정수 최대 최솟값 https://www.acmicpc.net/source/29625871
     *
     * 틀렸대. 210913 5%
     *  1. 생각한대로 돌고 있는지 확인- 디버깅
     *  2. 반례 찾아봅시다.
     **/
    public void Problem2108(){
        final int MAX_INPUT_VALUE= 8001;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int inputTotalCount = Integer.parseInt(br.readLine());


//            입력이 하나뿐일 때
            if(1==inputTotalCount){
                int input= Integer.parseInt(br.readLine());
                bw.write(input+"\n"+input+"\n"+input+"\n"+0);
                bw.flush();
                return;
            }

//            1. input 받으면서 동시에 각 값이 몇 개씩 있는지 count 세기
            int []eachNumberCounts= new int[MAX_INPUT_VALUE]; // [0]자리에 -4000의 중복 개수 넣기
//            Arrays.fill(eachNumberCounts, 0);
            int inputMax=-4001;
            int inputMin=4001;
            int inputCount= 0;
            for (int i = 0; i < inputTotalCount; i++) {
                int input= Integer.parseInt(br.readLine());
                eachNumberCounts[input+4000]++; // [0]자리에 -4000 중복 개수 넣기
                inputCount++;

                if(input >= inputMax){
                    inputMax= input;
                }
                if(input <= inputMin){
                    inputMin= input;
                }
            }


//            2-1. mean
//            [11 ea]{-3999, 0, 0, 0, > 1 <, 1, 1, 2, 2, 2, 4000} => {0, 1[1], 0, ... 0, 3[4001], 3[4002], 3[4003], 0, ..., 0, 1[8001]}
//            -1 pass -> sum+= for(-4000 ~ 4000) {sum += eachNumberCounts[index + 4000] * i }
            double sum=0;
            int median=0;
            int eachNumberCountsMax=0;
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
//            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
//            중복값 없으면 최대 inputCount 개
            class Accumulation{
                final int accumulateCount;
                final int originalValue;

                public Accumulation(int accumulateCount, int originalValue) {
                    this.accumulateCount = accumulateCount;
                    this.originalValue = originalValue;
                }
            }
            List<Accumulation> accumulations= new ArrayList<>();
            int j=0;

            for(int i=0; i<=8000; i++){
                if(0 == eachNumberCounts[i]){
                    continue;
                }
                int originValue= i-4000;
                sum+= eachNumberCounts[i] * (originValue); // -4000 ~ 0 ~ 4000

//            2-2. median
                int medianIndex= inputCount/2+1;

//                a_j < inputCount/2+1 < a_j+1
                if(0 == accumulations.size()){ //j==0
                    accumulations.add(new Accumulation(eachNumberCounts[i], originValue));
                    median= accumulations.get(0).originalValue;

                }else{
                    accumulations.add(new Accumulation(accumulations.get(j - 1).accumulateCount + eachNumberCounts[i], originValue));

                    if( accumulations.get(j-1).accumulateCount == medianIndex){
                        median= accumulations.get(j-1).originalValue;

                    } else if( accumulations.get(j-1).accumulateCount < medianIndex && medianIndex <= accumulations.get(j).accumulateCount){
                        median= accumulations.get(j).originalValue;
                    }
                }
                j++;


//             2-3. mod 최빈값
//                빈도가 가장 높은 값 찾기
                if(eachNumberCounts[i] >= eachNumberCountsMax){
                    eachNumberCountsMax= eachNumberCounts[i];
                }
            }

//            최빈값이 1개 이상일 경우
            for(int i=0; i<=8000; i++){
                if(eachNumberCounts[i] == eachNumberCountsMax){
                    int originValue= i-4000;
                    minHeap.add(originValue);
                }
            }


            double quotient= sum / (double)inputCount; // int 그냥 나누면 소수점 이하 사라진다
            long mean= Math.round(quotient);
            StringBuilder sb = new StringBuilder();
            sb.append(mean).append("\n");
            sb.append(median).append("\n");

//           여러 개면 두 번째로 작은 값
            if(minHeap.size() > 1){
                minHeap.poll(); // head 뽑아내기. minHeap 의 head => min 값
            }
            sb.append(minHeap.peek()).append("\n");
            sb.append(inputMax - inputMin);


            bw.write(String.valueOf(sb));
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }
}
