import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Tree_node {
	Tree_node parent;
	LinkedList<Tree_node> children = new LinkedList<Tree_node>();
	int puzzle[][];
	int x;
	int y;
	String move;

	public Tree_node(int puzzle[][], int x, int y, String move, Tree_node parent) {
		this.puzzle = puzzle;
		this.x = x;
		this.y = y;
		this.move = move;
		this.parent = parent;
	}
}

public class NPuzzle {

	public static void main(String[] args) {
		// Scanner sc = new Scanner(System.in);
		// Integer n = sc.nextInt();
		// int puzzle[][] = new int[n][n];
		int puzzle[][] = new int[][] { { 4, 1, 3 }, { 2, 0, 6 }, { 7, 5, 8 } }; // n = 3

		BFS(puzzle, 1, 1);
	}

	private static void BFS(int[][] puzzle, int x, int y) {
		Queue<Tree_node> queue = new LinkedList<Tree_node>();

		Tree_node root = new Tree_node(puzzle, x, y, "", null);
		queue.add(root);
		LinkedList<Tree_node> children = new LinkedList<Tree_node>();
		Tree_node curr = null;

		do {
			curr = queue.poll();

			if (solution(curr)) {

				LinkedList<String> steps = new LinkedList<String>();
				while (curr != null) {
					if (curr != null)
						if (!curr.move.toString().equals(""))
							steps.add(curr.move);
					curr = curr.parent;
				}

				for (int i = steps.size() - 1; i >= 0; i--)
					System.out.println(steps.get(i));

				System.out.println("Total steps for the solution are: " + steps.size());
				return;
			}
			// create children of head tree node

			// createRightchild
			if (curr.y + 1 < puzzle.length) {
				createChild(curr.puzzle, queue, children, curr, "right", curr.x, curr.y + 1);
			}
			// createDownChild
			if (curr.x + 1 < puzzle.length) {
				createChild(curr.puzzle, queue, children, curr, "down", curr.x + 1, curr.y);
			}
			// createLeftchild
			if (curr.y - 1 >= 0) {
				createChild(curr.puzzle, queue, children, curr, "left", curr.x, curr.y - 1);
			}
			// createUpchild
			if (curr.x - 1 >= 0) {
				createChild(curr.puzzle, queue, children, curr, "up", curr.x - 1, curr.y);
			}

			for (Tree_node tree_node : children)
				curr.children.add(tree_node);

			children.clear();
		} while (!queue.isEmpty());

	}

	private static void createChild(int[][] puzzle, Queue<Tree_node> queue, LinkedList<Tree_node> children,
			Tree_node curr, String move, int new_x, int new_y) {
		int[][] temp_puzzle = new int[puzzle.length][puzzle.length];
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle.length; j++)
				temp_puzzle[i][j] = puzzle[i][j];
		}
		temp_puzzle[curr.x][curr.y] = puzzle[new_x][new_y];
		temp_puzzle[new_x][new_y] = 0;

		Tree_node child = new Tree_node(temp_puzzle, new_x, new_y, move, curr);
		// check for equals
		if (!equalsObj(child)) {
			queue.add(child);
			children.add(child);
		}
	}

	private static boolean equalsObj(Tree_node child) {

		for (int i = 0; i < child.puzzle.length; i++) {
			for (int j = 0; j < child.puzzle.length; j++) {
				if (child.puzzle[i][j] != child.parent.puzzle[i][j])
					return false;
			}
		}

		return true;
	}

	private static boolean solution(Tree_node curr) {

		int solution[][] = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

		for (int i = 0; i < solution.length; i++)
			for (int j = 0; j < solution.length; j++) {
				if (solution[i][j] != curr.puzzle[i][j])
					return false;
			}

		return true;
	}

}
