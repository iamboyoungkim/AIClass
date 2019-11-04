import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    static int N = Main.N; // 배열의 크기
    static int population = Main.population; // 인구 수

    // 퀸을 랜덤으로 배치
    public static ArrayList<Integer> getRandom() {
        ArrayList<Integer> randArr = new ArrayList<>();
        Random rand = new Random();

        while(true) {
            if (randArr.size() == N) break;
            int tmp = rand.nextInt(N);
            if (randArr.indexOf(tmp) == -1) randArr.add(tmp);
        }
        return randArr;
    }

    // fitness (충돌의 횟수) 계산
    // 이전 과제들과 같은 방식
    public static int calcFitness (ArrayList<Integer> arr){
        int cnt = 0; // 퀸이 충돌하는 횟수

        // 같은 행, 대각선에서 충돌하는 횟수 세기
        for (int i = 0; i<N; i++) {
            for (int j = i + 1; j<N; j++) {
                if (arr.get(i) == arr.get(j)) cnt++;
                else if (j-1 == Math.abs(arr.get(i) - arr.get(j))) cnt++;
            }
        }
        return cnt;
    }


    // parent selection : 토너먼트 방식
    // num의 값만큼의 queen 배열을 만들고, 그 중 가장 작은 충돌을 가진 부모 선
    // K번 시행해서 부모를 list에 넣어 return
    // fitness 값은 적을수록 좋음
    public static List parentselection (List generation, int num, int K){
        List sec_parent = new ArrayList();
        Random rand = new Random();

        for (int i = 0; i<K; i++){
            ArrayList<Integer> selectedParents = new ArrayList();
            int fitness = population;
            ArrayList<Integer> tmp;

            // 토너먼트 방식 사용
            for (int j = 0; j< num; j++){
                tmp = (ArrayList)generation.get(rand.nextInt(population));
                int collision = calcFitness(tmp);

                // 충돌이 가장 적은 부모를 모음
                if (fitness > collision){
                    selectedParents.clear();
                    selectedParents.addAll(tmp);
                    fitness = collision;
                }
            }
            sec_parent.add(selectedParents);
        }
        return sec_parent;
    }


    // crossover
    // 기존 population에서 랜덤으로 두 개의 요소를 뽑음
    // 랜덤 포인트 까지를 crossover 함
    public static List crossover(List generation, int num){
        List sec_cross = new ArrayList();
        Random rand = new Random();
        for (int i = 0; i<num/2; i++){
            ArrayList<Integer> first_parent = (ArrayList)generation.get(rand.nextInt(population));
            ArrayList<Integer> sec_parent = (ArrayList)generation.get(rand.nextInt(population));

            // 랜덤을 통해 crossover할 포인트 선택해서 진행
            for (int j = 0; j<rand.nextInt(N); j++){
                int tmp = first_parent.get(j);
                first_parent.remove(j);
                first_parent.add(j,sec_parent.get(j));
                sec_parent.remove(j);
                sec_parent.add(j, tmp);
            }
            // 두 parent 모두 crossover 값에 넣어줌
            sec_cross.add(sec_parent);
            sec_cross.add(first_parent);
        }
        return sec_cross;
    }

    // mutation
    // 선택된 배열마다 랜덤 포인트 지정
    // 포인트까지의 유전자를 삭제 후 다시 퀸 랜덤 배치
    public static List mutation(List generation, int num){
        List sec_mutation = new ArrayList();
        Random rand = new Random();

        for (int i = 0; i<num; i++){
            ArrayList<Integer> tmp = (ArrayList)generation.get(rand.nextInt(population));

            // 랜덤을 통해 mutation할 포인트 선택해서 진행
            // 포인트까지의 원소를 제거
            for (int j = 0; j < rand.nextInt(N); j++){
                tmp.remove(0);
            }

            // 랜덤한 수 삽입
            // 중복된 퀸의 숫자가 들어가지 않게 함 -> 충돌 방지
            while (tmp.size() != N){
                int randInt = rand.nextInt(N);
                if (tmp.indexOf(randInt) == -1) tmp.add(0, randInt);
            }
            sec_mutation.add(tmp);
        }
        return sec_mutation;
    }
}