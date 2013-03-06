package pl.parser.nbp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Utility {
	
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
    		System.out.println(data);
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
				  System.out.println(strLine);
				  
				  for(String s : adressesBeforeSearch) {
				  if( strLine.contains(s) 	 )
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
			if(from[2]<=31){
				from[2]++;
			}else if(from[1]<=12){
				from[1]++;
			}else if(from[0]<=currentYear){
				from[0]++;
			}
			adressesBeforeSearch.add(dateConvertToString(from));
		}
		
		
			System.out.println("->>>>>>>>>");
			System.out.println(adressesBeforeSearch);
		
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

    
}
