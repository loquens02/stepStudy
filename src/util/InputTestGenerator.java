package util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.util.Random;

public class InputTestGenerator {
    /**
     * @link excel 최빈값 https://jb-skin-149.tistory.com/25
     */
    public void caseGenerator(){
        Random random = new Random();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
//            홀수 정수 입력
            int inputCount = Integer.parseInt(br.readLine());


            StringBuilder sb = new StringBuilder();
            sb.append(inputCount).append("\n");
            for(int i=0; i<inputCount; i++){
                int rand= random.nextInt(8000)-4000;
                sb.append(rand).append("\n");
            }


            onPaste(String.valueOf(sb));
            bw.write(String.valueOf(sb));

        } catch (IOException io) {
            io.getStackTrace();
        }
    }

    /**
     * 결과물을 클립보드로
     * @link string to clipboard java https://stackoverflow.com/a/3592022
     */
    private void onPaste(String fromString){
        StringSelection selection = new StringSelection(fromString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
