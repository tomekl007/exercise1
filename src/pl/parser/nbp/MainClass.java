package pl.parser.nbp;


import java.io.IOException;


import java.util.LinkedList;
import java.util.List;

public class MainClass {

	/**
	 * @param args
	 * use :
	 * args[0]=currency
	 * args[1]=dateFrom
	 * args[2]=dateTo
	 * args[3]=outputFilePath
	 */
	public static void main(String[] args) {
		
		//String currencys[]={ "USD","EUR","CHF","GBP"};
		String currency = args[0];
		
		String dateFrom = args[1];
		String dateTo = args[2];
		String saveToPath = args[3];
		
		//custom converting
		String dateFromConverted = Utility.convertDate(dateFrom);
		String dateToConverted = Utility.convertDate(dateTo);
		
		List<String> adresses ;
		

		String path = "listOfTabels.txt";
		try{
		
			Utility.saveUrl(path, "http://www.nbp.pl/kursy/xml/dir.txt");
		}catch(Exception e){
			//ignored 
			
		}
		
		adresses = Utility.findNamesOfFiles(path,dateFromConverted,dateToConverted);
		
		
		
		List<Currency> baseCurrencies = new LinkedList<>();
		
		for(String address : adresses){
			try {
				Utility.saveUrl(address, Utility.buildUrl(address) );
				baseCurrencies.add(ParseXmlHelper.parseXmlFile(address,currency) );
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		
		float avarage = Statistic.avarageBuyCourse(baseCurrencies);
		double standardDeviation = Statistic.standardDeviation(baseCurrencies);
		
		
	
		Utility.saveOutput(saveToPath, avarage + " "+ standardDeviation);
	    
		
		Utility.performCleanup(adresses);
	}
	
	
	
	
}
