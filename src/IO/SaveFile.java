package IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Values.BestSolution;

public class SaveFile {

	private BufferedWriter bw;
	private static String WINDOWS = ".\\";
	private static String MAC = ".//";
	private static String FILENAME = "Particle Swarm Optimiztion - TSP test data";
	private static String FILETYPE = ".txt";
	
	public void saveFile(String dataSetName,BestSolution bestSolution, boolean isMac) {
		String savePath = "";
		if (isMac)
			savePath = SaveFile.MAC;
		else
			savePath = SaveFile.WINDOWS;
		savePath += SaveFile.FILENAME + SaveFile.FILETYPE;
		
		try {
			bw = new BufferedWriter(new FileWriter(savePath, true));
			
			bw.write("DataSet:" + ";" + dataSetName);
			bw.newLine();
			
			bw.write("Running Time:" + ";" + bestSolution.time);
			bw.newLine();
			
			bw.write("Best Distance:" + ";" + bestSolution.distance);
			bw.newLine();
			
			bw.write("Best Fitness:" + ";" + bestSolution.fitness);
			bw.newLine();
			
			bw.write("Avg Time:" + ";" + bestSolution.avgTime);
			bw.newLine();
			
			bw.write("Avg Distance:" + ";" + bestSolution.avgDistance);
			bw.newLine();
			
			bw.write("Best Path:" + ";" + getArrayListToString(bestSolution.solution));
			bw.newLine();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getArrayListToString(ArrayList<Integer> arrayList) {
		String output = "";
		for (int i=0; i<arrayList.size(); i++) {
			output += arrayList.get(i);
			if (i != arrayList.size()-1)
				output += ",";
		}
		return output;
	}
}
