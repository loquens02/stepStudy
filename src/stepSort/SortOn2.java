package stepSort;

import java.io.*;

/**
 * 정렬 O(n^2)
 * 입력 받고
 *
 * 이슈1: 왜 똑같은걸 출력하는지?
 * 해결1: element 가 아니고 inputCount 를 출력하고 있었다. 우측 상단의 warning 보고 알게 됨.
 *
 * @link 백준 java input- 엔터쳐서 들어온 입력: https://girawhale.tistory.com/37  >> https://www.acmicpc.net/problem/17219
 * @link BufferedWriter- sout 보다 빠르다: https://snacky.tistory.com/10
 */
public class SortOn2 {
	public void Problem2750() {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
			String inputCount = br.readLine();

//			System.out.println(inputCount);
//			bw.write( inputCount+"\n" );
//			bw.flush();
//			bw.close();

			int cnt = Integer.parseInt(inputCount);

			int []arr = new int[cnt];
			for (int i = 0; i < cnt; i++) {
				arr[i] = Integer.parseInt(br.readLine());
			}

/*
			test- 정렬 전
			for (int element : arr) {
				bw.write( element+"\n" );
				bw.flush();
			}
*/

			int []sortArr= new int[cnt];
			for(int i=0; i<cnt; i++){
				int largerCnt= 0;
				for(int j=0; j<cnt; j++){
					if(i==j) continue;
					if(arr[i] > arr[j]){
						largerCnt++;
					}
				}
				sortArr[largerCnt] = arr[i];
			}

//			test- 정렬 후
			for (int element : sortArr) {
				bw.write( element+"\n" );
				bw.flush();
			}
		} catch (IOException io) {
			io.getStackTrace();
		}
	}
}
