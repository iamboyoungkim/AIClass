import java.util.Queue;

public class BFS {

    // bfs search를 위한 함수
    public static Node Search(Node root, Queue<Node> fringe) {
        // root 노드를 fringe에 넣음
        fringe.offer(root);

        while (true) {
            // fringe가 비어 있으면
            if (fringe.isEmpty()) return null;
                // 비어 있지 않으면
            else {
                Node cur = fringe.poll();

                // Goal을 만족하는 경우
                if (cur.isGoal()) return cur;

                int next_x = cur.x + 1; // 다음 Node
                int len = cur.chess_map.length;

                // row가 다 찼다면
                if(next_x == len) continue;

                // Node 추가
                for(int i = 1; i<len; i++) {
                    Node nextNode = new Node(next_x, i, len);
                    nextNode.copyMap(cur.chess_map);
                    nextNode.chess_map[next_x][i] = 1;
                    fringe.offer(nextNode); // 다음 node를 fringe(queue)에 넣음
                }
            }
        }
    }

    // bfs search를 시작
    public static Node bfsSearch(Node root, Queue<Node> fringe) {
        int len = root.chess_map.length;

        // 시작점 설정
        for (int i = 1; i<len; i++) {
            Node start = new Node(1, i, len);
            start.chess_map[1][i] = 1;
            Node res = Search(start, fringe);
            if (res != null) return res;
        }

        return null;
    }
}
