import java.lang.*;
import java.io.*;
import java.nio.file.*;

/*
 *
 * 인공지능 Assignment2
 * 이 프로그램은 N-Queens 문제를 Hill-climbing의 방법으로 풀이하는 프로그램 입니다.
 *  Hill-climbing 알고리즘은 현재 state에서 이웃 state로 이동 시에 현재 state보다 더 나은 이웃으로만 이동하는 알고리즘 입니다.
 * 시간의 단위는 second입니다.
 */

public class Main{

    public static void write(int n, Path filename, String name, State result, double time) throws IOException {
        PrintWriter output;
        if (filename.toFile().exists()) output = new PrintWriter(new FileWriter(filename.toString(), true));
        else output = new PrintWriter(filename.toString());

        output.println(">" + name);

        for (int i = 0; i<n; i++) {
            output.print(result.chess_map.indexOf(i) + " ");
        }
        output.println();
        output.println("Total Elapsed Time: "+ time);
        output.println();
        output.close();
    }

    public static void main(String[] args) throws IOException {

        // 입력 받은 arguments의 길이가 2가 아니면 바로 종료
        if (args.length != 2) {
            System.out.println("Please input [queen num] [filePath]");
            return;
        }

        int N = Integer.parseInt(args[0]);
        Path filename = Paths.get(args[1]);
        filename = filename.resolve("result" + N + ".txt");

        // 파일이 이미 있을 경우 삭제
        if (filename.toFile().exists()) filename.toFile().delete();

        double start, finish; // 시작시간, 끝나는 시간

        start = System.currentTimeMillis();
        State res;

        // while 문을 돌면서 Hillclimbing을 계속 실행하고, Local optima와 global maximum을 확인
        while (true) {
            State root = new State(N);

            if (root.h_value == 0) {
                res = root;
                break;
            }

            res = HillClibming.go(root);
            if (res.h_value == 0) break; // global maximum인 경우 경우 종료
        }

        finish = System.currentTimeMillis();
        write(N, filename, "Hill Climbing", res,(finish-start) / 1000);
    }
}
