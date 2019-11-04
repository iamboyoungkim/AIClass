public class HillClibming {

    // return a state that is a local maximum
    public static State go(State cur) {
        // 랜덤으로 생성한 state에서부터 휴리스틱 값이 적은 방향으로 이동해 goal state에 도달.
        State goalState = cur.solveProblem();

        if (goalState == null) return cur; // 이웃이 더이상 없으면 현재 state 리턴
        if (goalState.h_value == 0) return goalState; // 휴리스틱 값이 0이면 -> goal State에 도달 goal 리턴

        if (goalState.h_value <= cur.h_value) return go(goalState); // 더나은 상태가 있으면 그쪽으로 옮겨서 search (재귀적)
        else return cur;
    }
}
