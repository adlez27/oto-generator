package otogenerator;

import java.io.*;
import java.util.*;

public class OtoGenerator {
	private HashMap<String,String[]> indexDict;
	private HashMap<String,String> replaceDict;
	private ArrayList<String[]> oto;
	private String suffix, cons, cutoff, preutt, overlap;
	private int offset, offInc;
	
	/* A GUI would have all the text fields, options, etc.
	 * When a "generate oto" button is pressed in the GUI
	 * it instantiates an OtoGenerator object and passes 
	 * the csv files and the oto parameters.
	 * 
	 * CSV Files
	 * indexFile should be ./csv/index.csv
	 * replaceFile should be ./csv/replace.csv
	 * 
	 * OTO Parameters
	 * String: Suffix (send a blank string if no suffix, NOT NULL)
	 * int: Initial offset (for the first alias of a line in the reclist)
	 * int: Offset increment per each alias after
	 * String: Consonant
	 * String: Cutoff
	 * String: Preutterance
	 * String: Overlap
	 */
	public OtoGenerator(File indexFile, File replaceFile,
						String suf, int of, int ofi, String co, String cu, String p, String ov){
		indexDict = new HashMap<String,String[]>();
		try (BufferedReader br = new BufferedReader(new FileReader(indexFile))) {
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] lineArr = line.split(",");
				String fn = lineArr[0].substring(0,lineArr[0].length()-4);
				String[] phonemes = lineArr[1].split("_");
                indexDict.put(fn,phonemes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		replaceDict = new HashMap<String,String>();
		try (BufferedReader br = new BufferedReader(new FileReader(replaceFile))){
			String line;
			while ((line = br.readLine()) != null) {
				String[] lineArr = line.split(",");
				replaceDict.put(lineArr[0],lineArr[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		oto = new ArrayList<String[]>();
		suffix = suf;
		offset = of;
		offInc = ofi;
		cons = co;
		cutoff = cu;
		preutt = p;
		overlap = ov;
	}
	
	/* When the GUI button is pressed, it should also call this method
	 * through the OtoGenerator object, and pass these options.
	 * 
	 * If startEnd is true, starting/ending aliases (like [- k]) are included
	 * If replace is true, aliases are replaced according to replace.csv
	 * If dup is -1, delete duplicate aliases.
	 * If dup is 1, number duplicate aliases.
	 * If dup is 0, do nothing with duplicates.
	 */
	public void generateOto(boolean startEnd, boolean replace, int dup){
		generateAliases(startEnd);
		
		if (replace)
			replaceAliases();
		
		if (dup == -1)
			deleteDuplicates();
		else if (dup == 1)
			numberDuplicates();
		
		exportOto();
	}
	
	/* Goes through all keys in the index
	 * For each key, it goes through each pair of phonemes
	 * If startEnd is true, it'll make aliases for the first/last phoneme
	 * The phoneme pairs are made into a string array that represents a line of oto
	 * The line of oto is added to the "oto" arraylist
	 */
	private void generateAliases(boolean startEnd){
		Object[] reclist = indexDict.keySet().toArray();
		for (Object line : reclist){
			int currentOffset = offset;
			String[] phonemes = indexDict.get(line);
			if (startEnd){
				String[] otoLine = {line.toString(),"alias","offset",cons,cutoff,preutt,overlap};
				String alias = "- " + phonemes[0];
				otoLine[1] = alias;
				otoLine[2] = Integer.toString(currentOffset);
				oto.add(otoLine);
				currentOffset += offInc;
			}
			for (int i = 0; i < indexDict.get(line).length-1; i++) {
				String[] otoLine = {line.toString(),"alias","offset",cons,cutoff,preutt,overlap};
				String alias = phonemes[i] + " " + phonemes[i+1];
				otoLine[1] = alias;
				otoLine[2] = Integer.toString(currentOffset);
				oto.add(otoLine);
				currentOffset += offInc;
			}
			if (startEnd){
				String[] otoLine = {line.toString(),"alias","offset",cons,cutoff,preutt,overlap};
				String alias = phonemes[phonemes.length-1] + " -";
				otoLine[1] = alias;
				otoLine[2] = Integer.toString(currentOffset);
				oto.add(otoLine);
			}
		}
	}
	
	/* Goes through the oto arraylist
	 * If the alias is a key in the replace dictionary, change it to the value
	 */
	private void replaceAliases(){
		for (String[] line : oto){
			if (replaceDict.containsKey(line[1]))
				line[1] = replaceDict.get(line[1]);
		}
	}
	
	/* Goes through the oto arraylist
	 * Makes a temporary hashmap that counts occurrences of every alias
	 * If the alias of a line isn't in the hashmap, add it with a count of 0
	 * If the alias IS in the hashmap, increment hashmap value and remove line from oto
	 */
	private void deleteDuplicates(){
		HashMap<String,Integer> aliasCount = new HashMap<String,Integer>();
		int counter = 0;
		while (counter < oto.size()){
			String[] line = oto.get(counter);
			if (aliasCount.containsKey(line[1])){
				aliasCount.replace(line[1],aliasCount.get(line[1])+1);
				oto.remove(counter);
			} else {
				aliasCount.put(line[1],0);
				counter++;
			}
		}
	}
	
	/* Goes through the oto arraylist
	 * Makes a temporary hashmap that counts occurences of every alias
	 * If the alias of a line isn't in the hashmap, add it with a count of 0
	 * If the alias IS in the hashmap, increment hashmap value and 
	 * append the number to the alias
	 */
	private void numberDuplicates(){
		HashMap<String,Integer> aliasCount = new HashMap<String,Integer>();
		int counter = 0;
		while (counter < oto.size()){
			String[] line = oto.get(counter);
			if (aliasCount.containsKey(line[1])){
				aliasCount.replace(line[1],aliasCount.get(line[1])+1);
				line[1] += aliasCount.get(line[1]);
			} else {
				aliasCount.put(line[1],0);
				counter++;
			}
		}
	}
	
	/* Goes through the oto arraylist
	 * Adds the suffix to all aliases
	 * Prints the whole oto to a file in the result folder
	 */
	private void exportOto(){
		try {
            PrintWriter writer = new PrintWriter(new File("./result/oto.ini"));
            
            for (String[] line : oto) {
            	writer.println(line[0] + ".wav=" + line[1] + suffix + "," + line[2] + "," + line[3] + "," + line[4] + "," + line[5] + "," + line[6]);
            }
            writer.close();
         } catch (Exception ex) {
        	 ex.printStackTrace();
         }
	}
	
	// Uncomment this main if you wanna test it out
	
	/* public static void main(String[]args){
		File indexFile = new File("./csv/index.csv");
		File replaceFile = new File("./csv/replace.csv");
		OtoGenerator otogen = new OtoGenerator(indexFile, replaceFile,
											   "_G4",100,600,"100","-500","60","30");
		otogen.generateOto(true,true, 1);
	}*/
}
