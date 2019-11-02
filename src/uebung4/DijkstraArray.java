package uebung4;

public class DijkstraArray implements IndexedPriorityQueue {
	private int size;
	private boolean[] list;
	private double[] weights;
	private int costs = 0;

	public DijkstraArray(int size) {
		this.size = size;
		list = new boolean[size];
		weights = new double[size];
	}

	@Override
	public void change(int index, double key) {
		list[index] = true;
		weights[index] = key;
	}

	@Override
	public boolean empty() {
		boolean flag = true;
		for (boolean b : list) {
			if (b) {
				flag = false;
			}
		}

		return flag;
	}

	@Override
	public boolean contains(int index) {
		return list[index];
	}

	@Override
	public int deleteMin() {
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < list.length; i++) {
			if (list[i] && i < min) {
				min = i;
			}
		}
		list[min] = false;
		return min;
	}

	@Override
	public void insert(int index, double key) {
		list[index] = true;
		weights[index] = key;
	}

	@Override
	public void output() {
		// TODO Auto-generated method stub

	}

}
