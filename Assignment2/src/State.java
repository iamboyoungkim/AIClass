import java.lang.reflect.Array;
import java.util.*;

public class State {
    ArrayList<Integer> chess_map = new ArrayList<>();
    int h_value; // 휴리스틱 값

    // 생성자
    public State() {

    }

    // 생성자
    // Main에서 받은 n값을 받아서 State를 만들고 진행해 나감
    public State (int n) {
        for (int i = 0; i<n; i++) {
            chess_map.add(i);
        }
        Collections.shuffle(chess_map, new Random()); // State에 관한 리스트를 랜덩으로 섞어 줌
        this.getHeuristicWithCollision(); // heuristic 값을 얻음
    }

    private int MakeRandomInteger(int max) {
        return (int) ((Math.random() * max));
    }

    private static int[] shuffle(int[] arr){
        for(int x = 0; x<arr.length;x++){
            int i = (int)(Math.random() * arr.length);
            int j = (int)(Math.random() * arr.length);

            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        return arr;
    }


    // heuristic 값을 얻기 위한 메소드 (objective function)
    // 이 값은 작을수록 더 나은 상태이다 (값이 크면 충돌이 많다는 의미이기 떄문)
    public void getHeuristicWithCollision() {
        int tmp = 0;
        int size = this.chess_map.size();

        for (int i = 0; i<size - 1; i++) {
            for (int j = i+1; j<size; j++) {
                // 같은 열에 있거나 대각선 상에 있으면 충돌 처리
                int diff = Math.abs(this.chess_map.indexOf(j) - this.chess_map.indexOf(i));
                if (j - i == diff) tmp++;
                if (j == i) tmp++;
            }
        }
        this.h_value = tmp;
    }

    // 현재 state의 이웃 state 중에서 현재보다 나은 state를 찾음
    // 그 중 하나를 랜덤으로 추가
    public State solveProblem() {
        // init state 생성
        State nowState = new State();

        // neighbor state 설정
        // State class의 두 변수를 만들어줌
        ArrayList<State> state_arr = new ArrayList<>();
        int cur_h_value = this.h_value;

        int size = chess_map.size();

        for (int i = 0; i<size-1; i++) {
            for (int j = i + 1; j<size; j++) {
                State neighbor_state = new State();
                for (int k = 0; k<size; k++) {
                    // State neighbor = new State();
                    if (i == k) neighbor_state.chess_map.add(this.chess_map.indexOf(j));
                    else if (j == k) neighbor_state.chess_map.add(this.chess_map.indexOf(i));
                    else neighbor_state.chess_map.add(this.chess_map.indexOf(k));
                }

                neighbor_state.getHeuristicWithCollision(); // neighbor의 휴리스틱 값 설정
                if (neighbor_state.h_value < cur_h_value) state_arr.add(neighbor_state); // 현재 상태보다 이웃의 휴리스틱 값이 작은 경우 (더 나은 상태) 벡터에 더해줌
            }
        }

        // 값 리턴
        if (state_arr.size() == 0) return null; // state가 없으면 null 리턴
        else return state_arr.get(MakeRandomInteger(state_arr.size())); // 현재보다 나은 state를 찾았으니, 랜덤으로 추가하고 해당 state 리턴

    }
}
