package stepSort;

import java.io.*;

/**
 * Counting sort. 계수정렬
 * 1. input 받기 -> inputs
 *  MAX 1000만개
 * 2. inputs 순회하면서 각 값이 몇 번 나오는지 기록 -> eachNumberCounts
 *  1~10000 (no zero)
 * 3. eachNumberCounts 를 누적합으로 바꾼다 (eachNumberAccumulatedCounts)
 * 4. inputs 역으로 순회하면서 inputs[N] 값을 index1로 하는
 * 4-1. 정렬 결과 outputs 만들어두기 (== inputs.size)
 *      eachNumberCounts[index1] 의 값을 index2로 하는
 *      outputs[index2] 에 index1 값을 넣는다
 * 4-2. eachNumberCounts[index1] 에서 1을 뺀다.
 *
 *
 *  Java spec guarantees int[]'s initiate values are all 0. https://stackoverflow.com/a/2154340
 */
public class SortOn {
    public void Problem10989LightMore(){
        final int MAX_INPUT_VALUE= 10000;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int inputTotalCount = Integer.parseInt(br.readLine());

//            1. input 받으면서 동시에 각 값이 몇 개씩 있는지 count 세기
            int []eachNumberCounts= new int[MAX_INPUT_VALUE]; // [0]자리에 1 중복 개수 넣기
            for (int i = 0; i < inputTotalCount; i++) {
                int input= Integer.parseInt(br.readLine());
                eachNumberCounts[input-1]++; // [0]자리에 1 중복 개수 넣기
            }

//            2. 누적합 없이 순서대로 내보내기-input 에 없는 값이면 넘어가고, 있으면 count
            for(int i=0; i<MAX_INPUT_VALUE; i++){
                if(0== eachNumberCounts[i]){
                    continue;
                }
                for(int j=0; j<eachNumberCounts[i]; j++){
                    bw.write(i + 1 +"\n"); // [0]자리면 1
                }
            }
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }

    public void Problem10989Light(){

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int inputTotalCount = Integer.parseInt(br.readLine());

//            1. input 받으면서 동시에 각 값이 몇 개씩 있는지 count 세기
            int []eachNumberCounts= new int[10000]; // [0]자리에 1 중복 개수 넣기
            int maxInputValue=0;
            for (int i = 0; i < inputTotalCount; i++) {
                int input= Integer.parseInt(br.readLine());
                eachNumberCounts[input-1]++; // [0]자리에 1 중복 개수 넣기

                if(input >= maxInputValue){     // 필요한 만큼만 eachNumberCounts 돌리려고
                    maxInputValue= input;
                }
            }

//            2. 누적합이 왜 필요한지? 순서대로 내보내면 되잖아
//              input 에 없는 값이면 넘어가고, 있으면 count
            for(int i=0; i<maxInputValue; i++){
                if(0== eachNumberCounts[i]){
                    continue;
                }
                for(int j=0; j<eachNumberCounts[i]; j++){
                    bw.write(Integer.toString(i+1)+"\n"); // [0]자리면 1
                }
            }

            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }

    }
    public void Problem10989() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String inputCount = br.readLine();

//			bw.write( inputCount+"\n" );
//			bw.flush();
            int cnt = Integer.parseInt(inputCount);

//            1. input 받기
            int []inputs= new int[cnt];
            int max= 0;
            for (int i = 0; i < cnt; i++) {
                int input= Integer.parseInt(br.readLine());
                inputs[i]= input;

                if(input >= max){
                    max= input;
                }
            }

//            2. inputs 각 값이 몇 개씩 있는지. [0]자리에 1 중복 개수 넣기.
            int []eachNumberCounts= new int[max];
            for(int each: inputs){
                eachNumberCounts[each-1]++;
            }

//            3. accumulated counts
            for(int i=0; i<eachNumberCounts.length-1; i++){
                eachNumberCounts[i+1]+= eachNumberCounts[i];
            }

//            4. 역으로 순회하며 누적합 eachNumberCounts 에 있는 중복횟수 참고하며 뒤에서부터 넣기
//            넣고 나서는 eachNumberCounts[] 에서 1빼기.
            int []outputs= new int[cnt];
            for (int j=cnt-1; j>=0; j--){

                int inputNumber= inputs[j];
                int outputIndex= eachNumberCounts[inputNumber-1]-1;

                outputs[outputIndex]= inputNumber;
                eachNumberCounts[inputNumber-1]--;
            }

            StringBuilder sb = new StringBuilder();
            for (int element : outputs) {
                sb.append(element).append("\n");
            }
            bw.write(String.valueOf(sb));
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }
}
