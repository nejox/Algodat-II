package uebung8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		HashSet<String> dictionary = new HashSet<String>();
//		URL url = new URL("http://andrew.cmu.edu/course/15-121/dictionary.txt");

		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\Jojo\\Workspace\\Algodat-II\\src\\uebung8\\english_text.txt"));) {

			// Dictionary aufbauen
			dictionary = buildDicionary("C:\\Users\\Jojo\\Workspace\\Algodat-II\\src\\uebung8\\dictionary.txt");

			// Rechtschreibfehler entdecken
			String line = "";
			while (br.ready()) {
				line += br.readLine();
			}
			System.out.println(line);
			while (!line.isEmpty()) {
				int minIndex = line.indexOf(" ");
				if (minIndex > line.indexOf(".")) {
					minIndex = line.indexOf(".");
				}
				if (minIndex > line.indexOf(",")) {
					minIndex = line.indexOf(",");
				}
				String word = line.substring(0, minIndex);
				line = line.substring(minIndex+1);
				
				if(word.isEmpty()) {
					continue;
				}
				
				//alles klein machen
				String wordKlein = word.toLowerCase();
				
				// ist korrekt geschrieben?
				if(!dictionary.contains(wordKlein)) {
					System.out.println("WRONG: "+word);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static HashSet<String> buildDicionary(String pfad) {
		HashSet<String> dictionary = new HashSet<String>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\Jojo\\Workspace\\Algodat-II\\src\\uebung8\\dictionary.txt"));) {
			while (br.ready()) {
				String line = br.readLine();
				dictionary.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dictionary;
	}
}
