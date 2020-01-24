package Klausurvorbereitung.ub4;

public class ArrayHeap implements IndexedPriorityQueue {

	private double[] liste;

	public ArrayHeap(int n) {
		liste = new double[n];
		for (int i = 0; i < liste.length; i++) {
			liste[i] = -1.0d;
		}
	}

	@Override
	public void change(int index, double key) {
		liste[index] = key;
	}

	@Override
	public boolean empty() {

		for (double d : liste) {
			if (d > -1.0d) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean contains(int index) {
		if (liste[index] > -1.0d) {
			return true;
		}
		return false;
	}

	@Override
	public int deleteMin() {
		double min = Double.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < liste.length; i++) {
			if (liste[i] < min && liste[i] > -1.0d) {
				min = liste[i];
				index = i;
			}
		}
		liste[index] = -1.0d;
		return index;
	}

	@Override
	public void insert(int index, double key) {
		if (contains(index)) {
			return;
		}
		liste[index] = key;
	}

	@Override
	public void output() {
		// TODO Auto-generated method stub

	}

}
