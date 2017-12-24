package Program;

import java.util.ArrayList;
import java.util.Random;

import Values.Parameter;

public class Update {
	
	private Parameter parameter = Parameter.getInstance();
	
	// 新粒子
	public ArrayList<Integer> updateParticle(ArrayList<Integer> current, ArrayList<String> change) {
		ArrayList<Integer> newParticle = current;
		for (int i=0; i<change.size(); i++) {
			String[] tmp = change.get(i).split(",");
			if (!tmp[0].equals(tmp[1])) {
				// 原本位置
				int position1 = current.indexOf(Integer.parseInt(tmp[0]));
				int value1 = current.get(position1);
				// 替換位置
				int position2 = current.indexOf(Integer.parseInt(tmp[1]));
				int value2 = current.get(position2);
				// 位置互換
				newParticle.set(position1, value2);
				newParticle.set(position2, value1);
				if (position1 == 0) {
					newParticle.set(newParticle.size()-1, value2);
				}
				if (position2 == 0) {
					newParticle.set(newParticle.size()-1, value1);
				}
			}
		}
		return newParticle;
	}
	
	// 粒子狀態更新
	public ArrayList<String> particleStatusUpdate(double inertia, ArrayList<String> currentV, ArrayList<String> partBestP, ArrayList<String> globalBestP) {
		ArrayList<String> newStatus = new ArrayList<>();
		double cOne = parameter.getCramOne();
		double cTwo = parameter.getCramTwo();
		double rOne = new Random().nextDouble();
		double rTwo = new Random().nextDouble();
		int current = (int)(inertia);
		int part = (int)(cOne*rOne);
		int global = (int)(cTwo*rTwo);
		newStatus.addAll(getFinalStatus(current, currentV));
		newStatus.addAll(getFinalStatus(part, partBestP));
		newStatus.addAll(getFinalStatus(global, globalBestP));
		return newStatus;
	}
	
	// 慣性權重更新
	public double inertiaUpdate(int generation) {
		double wStart = parameter.getInertiaStart();
		double wEnd = parameter.getInertiaEnd();
		double tMax = parameter.getGeneration();
		return wStart-((wStart-wEnd)/tMax*generation);
	}
	
	// 取得最終更新
	public ArrayList<String> getFinalStatus(int number, ArrayList<String> status) {
		ArrayList<String> newStatus = new ArrayList<>();
		Random random = new Random();
		if (status.size() < number){
			number = status.size();
		}
		for (int i=0; i<number; i++) {
			int index = random.nextInt(status.size());
			newStatus.add(status.get(index));
			status.remove(index);
		}
		return newStatus;
	}
	
	// 粒子當前速度
	public ArrayList<String> getCurrentP(ArrayList<Integer> current) {
		ArrayList<String> currentP = new ArrayList<>();
		int size = current.size();
		for (int i=0; i<size; i++) {
			currentP.add(current.get(i) + "," + current.get(i));
		}
		return currentP;
	}
	
	// 粒子與自身最佳解的差異
	public ArrayList<String> getPartBestP(ArrayList<Integer> current, ArrayList<Integer> partBest) {
		ArrayList<String> partBestP = new ArrayList<>();
		int size = current.size();
		for (int i=0; i<size; i++) {
			if (partBest.get(i) != current.get(i)) {
				partBestP.add(current.get(i) + "," + partBest.get(i));
			}
		}
		return partBestP;
	}
	
	 // 粒子與全域最佳解的差異
	public ArrayList<String> getGlobalBestP(ArrayList<Integer> current, ArrayList<Integer> globalBest) {
		ArrayList<String> globalBestP = new ArrayList<>();
		int size = current.size();
		for (int i=0; i<size; i++) {
			if (globalBest.get(i) != current.get(i)) {
				globalBestP.add(current.get(i) + "," + globalBest.get(i));
			}
		}
		return globalBestP;
	}
}
