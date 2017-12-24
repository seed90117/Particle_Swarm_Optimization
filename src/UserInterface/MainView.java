package UserInterface;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import IO.DrawPanel;
import IO.LoadFile;
import IO.SaveFile;
import Program.MainMethod;
import Values.BestSolution;
import Values.Data;
import Values.DataSet;
import Values.Parameter;

public class MainView extends JFrame {
	
	/**
	 * M10456012
	 * Kevin Yen
	 * kelly10056040@gmail.com
	 */
	private static final long serialVersionUID = 1L;
	private boolean isLoad = false;
	private String dataSetName = "";
	private BestSolution bestSolution = null;
	private DrawPanel drawPanel = new DrawPanel();
	private DataSet dataSet = DataSet.getInstance();
	
	//*****宣告介面*****//
	Container cp = this.getContentPane();
	
	//*****宣告元件*****//
	JLabel generationLabel = new JLabel("Generation:");
	JLabel particleAmountLabel = new JLabel("Particle:");
	JLabel inertiaStartLabel = new JLabel("Inertia Start:");
	JLabel inertiaEndLabel = new JLabel("Inertia End:");
	JLabel cramOneLabel = new JLabel("Cram 1:");
	JLabel cramTwoLabel = new JLabel("Cram 2:");
	JLabel dataSetLabel = new JLabel("DateSet: ");
	JLabel runTimeLabel = new JLabel("Running Time: ");
	JLabel distanceLabel = new JLabel("Best Distance: ");
	JLabel fitnessLabel = new JLabel("Best Fitness: ");
	JLabel avgRunTimeLabel = new JLabel("Avg Time: ");
	JLabel avgDistanceLabel = new JLabel("Avg Distance: ");
	
	JTextField generationTextField = new JTextField("50");
	JTextField particleAmountTextField = new JTextField("10");
	JTextField inertiaStartTextField = new JTextField("0.9");
	JTextField inertiaEndTextField = new JTextField("0.4");
	JTextField cramOneTextField = new JTextField("2");
	JTextField cramTwoTextField = new JTextField("2");
	JTextField computerRunTextField = new JTextField("30");
	
	JButton loadFileButton = new JButton("Load File");
	JButton startButton = new JButton("Starts");
	JButton drawButton = new JButton("Draw");
	
	JCheckBox isMacCheckBox = new JCheckBox("Using Mac");
	JCheckBox isInteger = new JCheckBox("Output Integer");
	JCheckBox isComputerRunCheckBox = new JCheckBox("Computer Run");
	JTextArea output = new JTextArea();
	JPanel show = new JPanel();
	JFileChooser open = new JFileChooser();
	JScrollPane sp = new JScrollPane(output,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	MainView()
	{
		//*****設定介面*****//
		this.setSize(1000, 800);
		this.setLayout(null);
		this.setTitle("Particle Swarm Optimization");
		
		//*****設定元件位置*****//
		generationLabel.setBounds(700, 30, 100, 30);
		generationTextField.setBounds(800, 30, 100, 30);
		particleAmountLabel.setBounds(700, 70, 100, 30);
		particleAmountTextField.setBounds(800, 70, 100, 30);
		inertiaStartLabel.setBounds(700, 110, 100, 30);
		inertiaStartTextField.setBounds(800, 110, 100, 30);
		inertiaEndLabel.setBounds(700, 150, 150, 30);
		inertiaEndTextField.setBounds(800, 150, 100, 30);
		cramOneLabel.setBounds(700, 190, 150, 30);
		cramOneTextField.setBounds(800, 190, 100, 30);
		cramTwoLabel.setBounds(700, 230, 150, 30);
		cramTwoTextField.setBounds(800, 230, 100, 30);
		
		isMacCheckBox.setBounds(700, 310, 120, 30);
		isInteger.setBounds(820, 310, 150, 30);
		loadFileButton.setBounds(700, 350, 200, 30);
		startButton.setBounds(700, 390, 200, 30);
		drawButton.setBounds(700, 430, 200, 30);
		isComputerRunCheckBox.setBounds(700, 470, 150, 30);
		computerRunTextField.setBounds(850, 470, 40, 30);
		dataSetLabel.setBounds(700, 500, 250, 30);
		runTimeLabel.setBounds(700, 530, 250, 30);
		distanceLabel.setBounds(700, 560, 250, 30);
		fitnessLabel.setBounds(700, 590, 250, 30);
		avgRunTimeLabel.setBounds(700, 620, 250, 30);
		avgDistanceLabel.setBounds(700, 650, 250, 30);
		
		show.setBounds(20, 20, 650, 500);
		output.setBounds(20, 530, 650, 200);
		sp.setBounds(20, 530, 650, 200);
		
		//*****設定JPanel邊框*****//
		show.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		output.setEditable(false);//JTextArea不可編輯
		output.setLineWrap(true);//JTextArea自動換行
		
		cp.add(generationLabel);
		cp.add(generationTextField);
		cp.add(particleAmountLabel);
		cp.add(particleAmountTextField);
		cp.add(inertiaStartLabel);
		cp.add(inertiaStartTextField);
		cp.add(inertiaEndLabel);
		cp.add(inertiaEndTextField);
		cp.add(cramOneLabel);
		cp.add(cramOneTextField);
		cp.add(cramTwoLabel);
		cp.add(cramTwoTextField);
		cp.add(startButton);
		cp.add(loadFileButton);
		cp.add(show);
		cp.add(sp);
		cp.add(runTimeLabel);
		cp.add(distanceLabel);
		cp.add(fitnessLabel);
		cp.add(isMacCheckBox);
		cp.add(isComputerRunCheckBox);
		cp.add(computerRunTextField);
		cp.add(avgRunTimeLabel);
		cp.add(avgDistanceLabel);
		cp.add(isInteger);
		cp.add(drawButton);
		cp.add(dataSetLabel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		loadFileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LoadFile loadFile = new LoadFile();
				String tmpStr = loadFile.loadfile(open, show, isMacCheckBox.isSelected());
				if (!tmpStr.equals("")) {
					dataSetName = tmpStr;
				}
				dataSetLabel.setText("DateSet: " + dataSetName);
				if (!dataSetLabel.getText().equals("DateSet: "))
					isLoad = true;
			}});
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isLoad) {
					LoadFile loadFile = new LoadFile();
					SaveFile saveFile = new SaveFile();
					initialOutPut();
					if (dataSet.getIsAuto()) {
						for (int set=0; set<dataSet.getCount(); set++) {
							loadFile.autoLoadFile(show, isMacCheckBox.isSelected(), set);
							computerRunAlgorithm(isInteger.isSelected(),set);
							saveFile.saveFile(dataSet.getDataSet(set), bestSolution, isMacCheckBox.isSelected());
						}
					} else {
						if (isComputerRunCheckBox.isSelected()) {
							computerRunAlgorithm(isInteger.isSelected(), 0);
						} else {
							runAlgorithm(isInteger.isSelected());
						}
					}
				}
			}});
		drawButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showResult(bestSolution, output);
			}});
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainView();
	}

	private void runAlgorithm(boolean isInteger) {
		// 記錄開始時間
		long startTime = System.currentTimeMillis();
		// 設定參數
		setParameter();
		// 執行PSO
		MainMethod main = new MainMethod();
		this.bestSolution = main.mainProgram(isInteger);
		// 記錄結束時間
		long endTime = System.currentTimeMillis();
		runTimeLabel.setText("Running Time: " + getRunTime(startTime, endTime) + " s");
		showResult(this.bestSolution, output);
	}
	
	private void computerRunAlgorithm(boolean isInteger, int count) {
		int runTime = Integer.parseInt(computerRunTextField.getText());
		long startTime = 0;
		long endTime = 0;
		double totalDistance = 0;
		double totalTime = 0;
		ArrayList<Integer> bestPath = null;
		double bestDistance = -1;
		double bestTime = 0;
		for (int i=0; i<runTime; i++) {
			// 記錄開始時間
			startTime = System.currentTimeMillis();
			// 設定參數
			setParameter();
			// 執行PSO
			MainMethod main = new MainMethod();
			this.bestSolution = main.mainProgram(isInteger);
			// 記錄結束時間
			endTime = System.currentTimeMillis();
			// 記錄最佳
			if (i==0) {
				bestPath = this.bestSolution.solution;
				bestDistance = this.bestSolution.distance;
				bestTime = getRunTime(startTime, endTime);
			} else {
				if (bestDistance > this.bestSolution.distance) {
					bestPath = this.bestSolution.solution;
					bestDistance = this.bestSolution.distance;
					bestTime = getRunTime(startTime, endTime);
				}
			}
			totalDistance += bestSolution.distance;
			totalTime += getRunTime(startTime, endTime);
		}
		this.bestSolution = new BestSolution();
		this.bestSolution.solution = bestPath;
		this.bestSolution.distance = bestDistance;
		this.bestSolution.fitness = 1/bestDistance;
		this.bestSolution.time = bestTime;
		this.bestSolution.avgDistance = totalDistance/runTime;
		this.bestSolution.avgTime = totalTime/runTime;
		showResult(this.bestSolution, this.output);
		this.runTimeLabel.setText("Running Time: " + bestTime + " s");
		this.avgRunTimeLabel.setText("Avg Time: " + totalTime/runTime + " s");
		this.avgDistanceLabel.setText("Avg Distance: " + totalDistance/runTime);
	}
	
	private void setParameter() {
		Parameter parameter = Parameter.getInstance();
		if (this.dataSet.getIsAuto()) {
			Data data = Data.getInstance();
			int generation = 50;
			int particle = 10;
			if (data.total > 500) {
				generation = 500;
				particle = 100;
			} else {
				generation = data.total + (50 - data.total%50);
				particle = data.total;
			}
			parameter.setGeneration(generation);
			parameter.setParticleAmount(particle);
			parameter.setInertiaStart(0.9);
			parameter.setInertiaEnd(0.4);
			parameter.setCramOne(2);
			parameter.setCramTwo(2);
		} else {
			parameter.setGeneration(Integer.parseInt(generationTextField.getText()));
			parameter.setParticleAmount(Integer.parseInt(particleAmountTextField.getText()));
			parameter.setInertiaStart(Double.parseDouble(inertiaStartTextField.getText()));
			parameter.setInertiaEnd(Double.parseDouble(inertiaEndTextField.getText()));
			parameter.setCramOne(Double.parseDouble(cramOneTextField.getText()));
			parameter.setCramTwo(Double.parseDouble(cramTwoTextField.getText()));
		}
	}
	
	private void initialOutPut() {
		runTimeLabel.setText("Run Time: ");
		distanceLabel.setText("Best Distance: ");
		fitnessLabel.setText("Best Fitness: ");
		avgRunTimeLabel.setText("Avg Time: ");
		avgDistanceLabel.setText("Avg Distance: ");
	}

	private void showResult(BestSolution bestSolution, JTextArea output) {
		// 清空畫布
		this.drawPanel.drawpanel(show);
		int size = bestSolution.solution.size()-1;
		String out = this.dataSetLabel.getText() + "\nBest Path: \n";
		for (int i=0; i<size; i++) {
			this.drawPanel.drawline(bestSolution.solution.get(i), bestSolution.solution.get(i+1), show);
			if (i==size-1) {
				out += bestSolution.solution.get(i) + " - " + bestSolution.solution.get(i+1) + "\n";
			} else {
				out += bestSolution.solution.get(i) + " - ";
			}
		}
		out += "Best Distance: " + bestSolution.distance;
		this.distanceLabel.setText("Best Distance: " + bestSolution.distance);
		this.fitnessLabel.setText("Best Fitness: " + bestSolution.fitness);
		output.setText(out);
	}
	
	private double getRunTime(long start, long end) {
		double startTime = start;
		double endTime = end;
		return (endTime - startTime)/1000;
	}
}
