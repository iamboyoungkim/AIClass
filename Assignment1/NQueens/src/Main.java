import java.util.*;
import java.lang.*;
import java.io.*;
import java.nio.file.*;
/*
 *
 * 인공지능 Assignment1
 * 이 프로그램은 N-Queens 문제를 dfs, bfs, dfid 등의 방법으로 풀이하는 프로그램 입니다.
 * dfs, bfs, dfid 세 방식 모두 트리의 맨 앞쪽부터 서치하기 때문에 결과 값은 동일하게 나옵니다.
 * 시간의 단위는 second입니다.
 */

public class Main{

    public static void Write(int n, Path filename, String name, Node result, double time) throws IOException {
        PrintWriter output;

        if (filename.toFile().exists()) output = new PrintWriter(new FileWriter(filename.toString(), true));
        else output = new PrintWriter(filename.toString());

        // 답이 있는 경우
        if (result != null) {
            output.println(">" + name);
            output.print("Location : ");
            for (int j = 1; j<=n; j++) {
                for (int i = 1; i<=n; i++) {
                    if (result.chess_map[i][j] == 1) {
                        String data = i - 1 + " ";
                        output.print(data);
                        break;
                    }
                }
            }
        }
        // 답이 없는 경우
        else {
            output.println(">" + name);
            output.println("No Solution");
            output.println("Time : 0.0");
            output.println();
            output.close();
            return;
        }
        output.println();
        output.println("Time : " + time);
        output.println();
        output.close();
    }

    public static void main(String[] args) throws IOException {

        // 입력 받은 arguments의 길이가 2가 아니면 바로 종료
        if (args.length != 2) {
            System.out.println("Please input [queen num] [filePath]");
            return;
        }

        int n = Integer.parseInt(args[0]);
        Path filename = Paths.get(args[1]);
        filename = filename.resolve("result" + n + ".txt");

        // 파일이 이미 있을 경우 삭제
        if (filename.toFile().exists()) filename.toFile().delete();

        Stack<Node> st = new Stack<>(); // dfs와 dfid는 stack 사용
        Queue<Node> q = new LinkedList<>(); // bfs는 queue 사용
        double start, finish; // 시작시간, 끝나는 시간

        Node root = new Node(0, 0, n+1); // root 생성

        // Search 함수들은 Algorithm class 참조
        start = System.currentTimeMillis();
        Node dfsTree = DFS.DFSSearch(root);
        finish = System.currentTimeMillis();
        Write(n, filename, "DFS", dfsTree, (finish - start) / 1000);
        st.clear();

        start = System.currentTimeMillis();
        Node bfsTree = BFS.bfsSearch(root, q);
        finish = System.currentTimeMillis();
        Write(n, filename, "BFS", bfsTree, (finish - start) / 1000);
        q.clear();

        start = System.currentTimeMillis();
        Node dfidTree = DFID.DFIDSearch(root, 0, n);
        finish = System.currentTimeMillis();
        Write(n, filename, "DFID", dfidTree, (finish - start) / 1000);
    }
}

