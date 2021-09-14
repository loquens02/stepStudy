package stepSort;

import java.io.*;
import java.util.PriorityQueue;

/**
 * 수 정렬하기 2- https://www.acmicpc.net/problem/2751
 * 오름차순 정렬
 */
public class SortOnlogn {
    public void Problem2751() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String inputCount = br.readLine();

//			bw.write( inputCount+"\n" );
//			bw.flush();
            int cnt = Integer.parseInt(inputCount);

            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            for (int i = 0; i < cnt; i++) {
                minHeap.add(Integer.parseInt(br.readLine()));
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cnt; i++) {
                sb.append(minHeap.poll()).append("\n"); // 최대 100만개 입력에서 160ms 더 빠르다
//                bw.write( minHeap.poll()+"\n" );
			}
            bw.write(String.valueOf(sb));
            bw.flush();

        } catch (IOException io) {
            io.getStackTrace();
        }
    }
}
