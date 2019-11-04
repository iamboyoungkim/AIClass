public class DFID {
    // dfid search를 시작

    public static Node DFIDSearch(Node cur, int depth, int limit) {
        // goal을 만족한 경우 현 상태를 return
        if (cur.isGoal()) return cur;

        else if (depth == limit) return null;

        else {
            int len = cur.chess_map.length;
            int next_x = cur.x + 1;

            // Node 추가
            for(int i = 1; i<len; i++) {
                Node next = new Node(next_x, i, len);
                next.copyMap(cur.chess_map);
                next.chess_map[next_x][i] = 1;

                // 재귀적으로 search를 계속 해나감
                Node finalNode = DFIDSearch(next, depth + 1, limit);
                if(finalNode != null) return finalNode;
                next.chess_map[next_x][i] = 0;
            }
            return null;
        }
    }
}
