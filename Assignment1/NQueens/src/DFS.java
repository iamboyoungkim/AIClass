public class DFS {

    // dfs search를 시작
    public static Node DFSSearch(Node cur) {
        // goal을 만족한 경우 현 상태를 return
        if(cur.isGoal()) return cur;
            // goal을 만족하지 않은 경우
        else {
            int len = cur.chess_map.length;
            int next_x = cur.x + 1;

            // row가 다 찼다면
            if (next_x == len) return null;

            // Node 추가
            for (int i = 1; i<len; i++) {
                Node nextNode = new Node(next_x, i, len);
                nextNode.copyMap(cur.chess_map);
                nextNode.chess_map[next_x][i] = 1;

                // 재귀적으로 search를 계속 해나감
                Node finalNode = DFSSearch(nextNode);
                if (finalNode != null) return finalNode;
                nextNode.chess_map[next_x][i] = 0;
            }
            return null;
        }
    }
}
