package stepSort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 입력 -4000 ~ 4000 정수. 0 가능
 * 총 N개 - 최대 50만개. 홀수 개
 *
 * mean, median, mod, range
 * 11
 * -3999, 0, 0, 0, 1, > 1 <, 1, 2, 2, 2, 4000
 * 3
 * 3, 3, 3
 *
 * java array set all values https://stackoverflow.com/a/9128762
 *
 * TODO 틀렸대. 210913 5%
 *  1. 생각한대로 돌고 있는지 확인- 디버깅
 *  2. 반례 찾아봅시다.
 **/
public class SortApplication {
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
            int inputMax=0;
            int inputMin=4000;
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
