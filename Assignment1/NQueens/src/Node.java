// Node는 기본적으로 x좌표와 y좌표를 가지고 있음
// 그 외에도 chess_map이라는 이차원 배열을 통해 현재까지 놓인 queen의 정보를 저장해줌

public class Node {
    int x;
    int y;
    int[][] chess_map;

    // 생성자
    // x, y는 queen의 위치
    // map은 지금까지의 정보
    public Node(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.chess_map = new int[size][size];
    }

    // chess_map 배열을 copy하는 함수
    public void copyMap(int[][] origin_map) {
        int len = this.chess_map.length;
        for (int i = 1; i<len; i++) {
            for (int j = 1; j<len; j++) {
                this.chess_map[i][j] = origin_map[i][j];
            }
        }
    }

    // 현재 상태가 goal인지 체크하는 함수
    // 즉, Node가 최종적으로 만족하는지 체크
    public boolean isGoal() {
        int len = this.chess_map.length;
        int[] start = new int[len];
        boolean[] isVisit = new boolean[len];

        // initialization
        for (int i = 0; i<len; i++) {
            isVisit[i] = false; // false로 초기화
            start[i] = 0; // 0으로 초기화
        }

        // 각 row마다 queen을 확인
        for (int i = 1; i<len; i++) {
            for (int j = 1; j<len; j++) {
                // queen을 발견했을 때
                if (this.chess_map[i][j] == 1) {
                    if (isVisit[j]) return false; // 이미 queen이 놓여 있으면 false를 리턴함
                    else {
                        isVisit[j] = true;
                        start[i] = j; // start에는 queen이 놓인 col을 저장함
                        break;
                    }
                }
            }
        }

        // 각 row에서 queen의 위치에 문제 있는지 확인
        for(int i = 1; i<len; i++) {
            // 시작 좌표
            int start_x = i;
            int start_y = start[i];
            if (start_y == 0) return false; // queen이 없으면 return false

            // 대각선은 위 방향, 아래 방향 두가지 있으므로 둘 다 확인해 줘야 함
            // 아래 방향 대각선
            int dg = 1;
            while (true) {
                boolean chk = false;
                if (start_x - dg > 0 && start_y - dg > 0) {
                    chk = true;
                    if (this.chess_map[start_x - dg][start_y - dg] == 1) return false;
                }
                if (start_x + dg <len && start_y + dg < len) {
                    chk = true;
                    if (this.chess_map[start_x + dg][start_y + dg] == 1) return false;
                }
                if (!chk) break;
                dg++;
            }

            // 위 방향 대각선
            for(int j = 1; j<len; j++) {
                if ( start_x == j) continue;
                if ( (start_x + start_y -j) <= 0) break;
                if ( (start_x + start_y) - j< len && this.chess_map[j][ (start_x + start_y) - j] == 1) return false;
            }
        }
        return true;
    }
}