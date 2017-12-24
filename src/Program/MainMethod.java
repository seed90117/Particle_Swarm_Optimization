package Program;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Values.BestSolution;
import Values.Data;
import Values.Parameter;

public class MainMethod {
	
	private boolean isInteger = false;
	
	private Data data = Data.getInstance();
	private Parameter parameter = Parameter.getInstance();
	private Update update = null;
	private Optimization optimization = null;
	
	private ArrayList<Integer>[] particles = null;
	private double[] distance = null;
	private BestSolution globalBestSolution = null;
	private BestSolution[] partBestSolution = null;
	
	// 演算法主程式
	public BestSolution mainProgram(boolean isInteger) {
		setDistanceType(isInteger);
		this.update = new Update();
		this.optimization = new Optimization();
		this.globalBestSolution = new BestSolution();
		int generation = parameter.getGeneration();
		for(int t=0; t<generation; t++) {
			// 初始化粒子
			initialParticles();
			// 更新粒子
			updateParticles(t);
			// 更新自身最佳與全域最佳
			updateBest();
			// 二元優化法
			optimizationPath();
		}
		return this.globalBestSolution;
	}
	// 初始粒子
	@SuppressWarnings("unchecked")
	private void initialParticles() {
		this.particles = new ArrayList[this.parameter.getParticleAmount()];
		this.distance = new double[this.parameter.getParticleAmount()];
		this.partBestSolution = new BestSolution[this.parameter.getParticleAmount()];
		Random random = new Random();
		int size = this.data.total;
		for (int i=0; i<this.parameter.getParticleAmount(); i++) {
			ArrayList<Integer> list = getPointList();
			this.partBestSolution[i] = new BestSolution();
			this.particles[i] = new ArrayList<>();
			for (int j=0; j<size; j++) {
				int position = random.nextInt(list.size());
				this.particles[i].add(list.get(position));
				list.remove(position);
				if (j > 0) {
					this.distance[i] += calculationDistance(this.particles[i].get(j), this.particles[i].get(j-1));
				}
			}
			this.particles[i].add(this.particles[i].get(0));
			this.distance[i] += calculationDistance(this.particles[i].get(size-1), this.particles[i].get(size));
			// 記錄粒子自身最佳解
			this.partBestSolution[i].solution = this.particles[i];
			this.partBestSolution[i].distance = this.distance[i];
			// 記錄粒子群體最佳解
			if (this.globalBestSolution.distance == -1 || this.globalBestSolution.distance > distance[i]) {
				this.globalBestSolution.solution = this.particles[i];
				this.globalBestSolution.distance = this.distance[i];
				this.globalBestSolution.fitness = 1/this.distance[i];
			}
		}
	}

	// 更新粒子
	private void updateParticles(int generation) {
		int size = parameter.getParticleAmount();
		double w = this.update.inertiaUpdate(generation);
		for (int p=0; p<size; p++) {
			ArrayList<String> current = this.update.getCurrentP(this.particles[p]);
			ArrayList<String> partBest = this.update.getPartBestP(this.particles[p], this.partBestSolution[p].solution);
			ArrayList<String> globalBest = this.update.getGlobalBestP(this.particles[p], this.globalBestSolution.solution);
			ArrayList<String> change = this.update.particleStatusUpdate(w, current, partBest, globalBest);
			this.particles[p] = this.update.updateParticle(this.particles[p], change);
			this.distance[p] = calculationParticlesDistance(this.particles[p]);
		}
	}
	
	// 更新自身最佳與全域最佳
	private void updateBest() {
		int size = this.parameter.getParticleAmount();
		for (int p=0; p<size; p++) {
			if (this.distance[p] < this.partBestSolution[p].distance) {
				this.partBestSolution[p].distance = this.distance[p];
				this.partBestSolution[p].solution = this.particles[p];
				this.partBestSolution[p].fitness = 1/this.distance[p];
			}
			if (this.distance[p] < this.globalBestSolution.distance) {
				this.globalBestSolution.distance = this.distance[p];
				this.globalBestSolution.solution = this.particles[p];
				this.globalBestSolution.fitness = 1/this.distance[p];
			}
		}
	}
	
	// 二元優化路徑
	private void optimizationPath() {
		for (int p=0; p<particles.length; p++) {
			this.particles[p] = this.optimization.twoOptimization(this.particles[p]);
			double dis = 0;
			for (int city=0; city<this.particles[p].size()-1; city++) {
				dis += calculationDistance(this.particles[p].get(city), this.particles[p].get(city+1));
			}
			this.distance[p] = dis;
			if (this.globalBestSolution.distance == -1 || this.globalBestSolution.distance > this.distance[p]) {
				this.globalBestSolution.distance = this.distance[p];
				this.globalBestSolution.solution = this.particles[p];
				this.globalBestSolution.fitness = 1/this.distance[p];
			}
			if (this.partBestSolution[p].distance > this.distance[p]) {
				this.partBestSolution[p].distance = this.distance[p];
				this.partBestSolution[p].solution = this.particles[p];
				this.partBestSolution[p].fitness = 1/this.distance[p];
			}
		}
	}
	
 	// 計算粒子距離
	private double calculationParticlesDistance(ArrayList<Integer> particle) {
 		int size = particle.size()-1;
 		double dis = 0;
 		for (int p=0; p<size; p++) {
 			dis += calculationDistance(particle.get(p), particle.get(p+1));
 		}
 		return dis;
 	}
	// 計算距離
	private double calculationDistance(int pointA, int pointB) {
		if (isInteger)
			return calculationDistanceInteger(pointA, pointB);
		else
			return calculationDistanceDouble(pointA, pointB);
	}
	// 計算距離(整數)
	private double calculationDistanceInteger(int pointA, int pointB) {
		return (double)Math.round(Point.distance(
				this.data.x[pointA], this.data.y[pointA], 
				this.data.x[pointB], this.data.y[pointB]));
	}
	// 計算距離(小數)
	private double calculationDistanceDouble(int pointA, int pointB) {
		return Point.distance(
				this.data.x[pointA], this.data.y[pointA], 
				this.data.x[pointB], this.data.y[pointB]);
	}
	
	// 取得所有點的ArrayList
	private ArrayList<Integer> getPointList() {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i=0; i<this.data.total; i++) {
			list.add(i);
		}
		return list;
	}

	// 設定距離型態
	private void setDistanceType(boolean isInteger) {
		this.isInteger = isInteger;
	}
}
