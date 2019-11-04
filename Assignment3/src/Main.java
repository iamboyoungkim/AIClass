import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * 인공지능 Assignment3
 * N-Queens 문제를 Genetic Algorithm의 방법으로 풀이
 */

public class Main {
    static int N; // 배열의 갯수
    static int population = 100; // 인구 수
    static double elapsed_time = 0; // 지난 시간

    public static void main(String args[]){

        // 입력 받은 arguments의 길이가 2가 아니면 바로 종료
        if (args.length != 2) {
            System.out.println("Please input [queen num] [filePath]");
            return;
        }

        // args[0] -> 배열의 갯수
        N = Integer.parseInt(args[0]);
        // args[1] -> output file
        File output = new File(args[1] , "result" + N + ".txt");

        // outputstream 초기화
        PrintWriter outputStream = null;

        // 파일 출력 예외 처리 구문
        try {
            outputStream = new PrintWriter(new FileOutputStream(output));
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 시작 시간 측정하기
        elapsed_time = System.currentTimeMillis();

        // parent selection, crossover, mutation 각각의 rate와 값들
        // ~_val의 경우 population을 곱한 후 int 형으로 변환시킴
        double parent_sel_rate = 0.4;
        double crossover_rate = 0.3;
        double mute_rate = 0.3;

        int parent_sel_val = (int)(population * parent_sel_rate);
        int crossover_val = (int)(population * crossover_rate);
        int mutation_val = (int)(population * mute_rate);

        // generation 시작
        List generation = new ArrayList();

        // goal state
        ArrayList<Integer> goal_state = new ArrayList();

        for (int i = 0; i<population; i++){
            ArrayList<Integer> child_generation = GeneticAlgorithm.getRandom();
            generation.add(child_generation);
        }

        // 가능할 때만 genetic algorithm 작동하도록
        if (N > 4){
            // goal이 empty 상태일 경우 while 문 반복
            while (goal_state.isEmpty()){
                List new_generation = new ArrayList();

                // parent selection, crossover, mutation 각각에 대해 동작
                new_generation.addAll(GeneticAlgorithm.parentselection(generation, population / 5, parent_sel_val));
                new_generation.addAll(GeneticAlgorithm.crossover(generation, crossover_val));
                new_generation.addAll(GeneticAlgorithm.mutation(generation, mutation_val));

                generation.clear();
                generation.addAll(new_generation);

                new_generation.clear();

                // fitness 체크 -> 0이면 collision 발생 하지 않음 -> goal state
                for (int i = 0; i<population; i++){
                    if (GeneticAlgorithm.calcFitness((ArrayList)generation.get(i)) == 0) goal_state = (ArrayList)generation.get(i);
                }
            }
        }

        // elapsed time 설정
        elapsed_time = (System.currentTimeMillis() - elapsed_time) / 1000 ;

        // 출력 시작
        outputStream.println(">Genetic Algorithm");

        // 해답을 찾을 수 없을 때
        if (N < 4) {
            outputStream.println("No Solution");
            elapsed_time = 0.000;
        }
        // 해답이 존재할 때
        else {
            // goal state에 있는 퀸 위치 출력
            for(int i = 0; i<N; i++) {
                outputStream.print(goal_state.get(i) +  " ");
            }
            outputStream.println();
        }
        // 결과 출력
        outputStream.println("Total Elapsed Time: " + elapsed_time);
        // outputstream 종료
        outputStream.close();


    }

}
