package pl.parser.nbp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String currencys[]={ "USD","EUR","CHF","GBP"};
		String currency = "EUR";
		
		String dateFrom = "2013-01-28";
		String dateTo = "2013-01-31";
		
		String dateFromConverted = Utility.convertDate(dateFrom);
		String dateToConverted = Utility.convertDate(dateTo);
		
		List<String> adresses ;
		

		String path = "listOfTabels.txt";
		try{
		//URL website = new URL("http://www.nbp.pl/kursy/xml/dir.txt");
	  //  ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	   // FileOutputStream fos = new FileOutputStream("information.html");
			Utility.saveUrl(path, "http://www.nbp.pl/kursy/xml/dir.txt");
		}catch(Exception e){
			//ignored 
		}
		
		adresses = Utility.findNamesOfFiles(path,dateFromConverted,dateToConverted);
		
		System.out.println(adresses);
		
		List<Currency> baseCurrencies = new LinkedList<>();
		
		for(String address : adresses){
			try {
				Utility.saveUrl(address, Utility.buildUrl(address) );
				baseCurrencies.add(ParseXmlHelper.parseXmlFile(address,currency) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println(baseCurrencies);
		
		float avarage = Statistic.avarageBuyCourse(baseCurrencies);
		double standardDeviation = Statistic.standardDeviation(baseCurrencies);
		
		System.out.println(standardDeviation);
		
	    
	}
	
	
}
