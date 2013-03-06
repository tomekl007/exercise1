package pl.parser.nbp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * @author Tomasz Lelek
 * Helper methods converting date, dowload files form web,
 * saving output and performing cleanup
 * 
 *
 */
public class Utility {
	
	
	private static String baseAddress = "http://www.nbp.pl/kursy/xml/";
	
	private static List<String> adressesBeforeSearch=new LinkedList<>();

	 
	private static List<String> adressesAfterSearch=new LinkedList<>();

	public static String convertDate(String date){
		String[] dates = date.split("-");
		boolean first = true;
		String result="";
		for(String t : dates){
			if(first){
			result+=t.subSequence(2, 4);
			first=false;
			}else
			result+=t;
		}
		return result;
	}
	
	public static void saveUrl(String filename, String urlString) throws MalformedURLException, IOException
    {
    	BufferedInputStream in = null;
    	FileOutputStream fout = null;
    	try
    	{
    		in = new BufferedInputStream(new URL(urlString).openStream());
    		fout = new FileOutputStream(filename);

    		byte data[] = new byte[1024];
    		int count;
    		while ((count = in.read(data, 0, 1024)) != -1)
    		{
    			fout.write(data, 0, count);
    		}
    		//System.out.println(data);
    	}
    	finally
    	{
    		if (in != null)
    			in.close();
    		if (fout != null)
    			fout.close();
    	}
    }
	
	static List<String> findNamesOfFiles(String inputPath, String dateFrom, String dateTo){
		
		findAllAdresses(dateFrom, dateTo);
		
		try{
			 
			  FileInputStream fstream = new FileInputStream(inputPath);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
				
				  
				  for(String s : adressesBeforeSearch) {
				  if( strLine.contains(s) && strLine.contains("c") 	 )
					  adressesAfterSearch.add(strLine);				  
				  }
			  }
			  
			  
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		return adressesAfterSearch;
		}
	
	static int currentYear = 2013;
	private static void findAllAdresses( String dateFrom, String dateTo){
		
		adressesBeforeSearch.add(dateFrom);
			int[] from = customParseDate(dateFrom);
			int[] to = customParseDate(dateTo);
			
		while(!(from[0]==to[0]&&from[1]==to[1]&&from[2]==to[2]) ){
			if(from[2]<=30){
				from[2]++;
			}else if(from[1]<=12){
				from[1]++;
				from[2]=1;
			}else if(from[0]<currentYear){
				from[0]++;
				from[2]=1;
				from[1]=1;
			}
			adressesBeforeSearch.add(dateConvertToString(from));
		}
		
		
		
		
	}
	
    private static int[] customParseDate(String date){
    	String[] dateP = new String[3]; 
    	dateP[0] = date.substring(0, 2);
    	dateP[1] = date.substring(2, 4);
    	dateP[2] = date.substring(4, 6);
    	
    	int[] dateInt = new int[3];
    	dateInt[0] = Integer.parseInt(dateP[0]);
    	dateInt[1] = Integer.parseInt(dateP[1]);
    	dateInt[2] = Integer.parseInt(dateP[2]);
    	return dateInt;
    }
    
    private static String dateConvertToString(int[] dateArray){
    	String result = "";
    	result += String.valueOf(dateArray[0]);
    	if(dateArray[1] < 10){
    		result +="0";
    	result += String.valueOf(dateArray[1]);
    	}else{
    		result += String.valueOf(dateArray[1]);
    	}
    	
    	if(dateArray[2] < 10){
    		result +="0";
    		result += String.valueOf(dateArray[2]);
    	}else{
    	result += String.valueOf(dateArray[2]);
    	}
    	return result;
    	
    }

	public static String buildUrl(String address) {
		
		return baseAddress+address+".xml";
	}

    
	
	public static void saveOutput(String path, String text){
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter(path);
			  BufferedWriter out = new BufferedWriter(fstream);
			  String[] toSave = text.split(" ");
			  for(String s : toSave){
				  out.write(s);
				  out.newLine();
			  }
			  
			  
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		
	}
	
	
	public static void performCleanup(List<String> adresses){
	
    for(String fileName: adresses){
    // A File object to represent the filename
    File f = new File(fileName);

    // Make sure the file or directory exists and isn't write protected
    if (!f.exists())
      throw new IllegalArgumentException(
          "Delete: no such file or directory: " + fileName);

    if (!f.canWrite())
      throw new IllegalArgumentException("Delete: write protected: "
          + fileName);

    // If it is a directory, make sure it is empty
    if (f.isDirectory()) {
      String[] files = f.list();
      if (files.length > 0)
        throw new IllegalArgumentException(
            "Delete: directory not empty: " + fileName);
    }

    // Attempt to delete it
    boolean success = f.delete();

    if (!success)
      throw new IllegalArgumentException("Delete: deletion failed");
    	}
	}
	
	
}
