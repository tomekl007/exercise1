package pl.parser.nbp;

import java.text.DecimalFormat;
import java.util.List;


/**
 * 
 *
 * @author Tomasz Lelek
 * methods to count avarage course (buy/sell) and
 * standard deviation
 *
 */
public class Statistic {

	public static float avarageBuyCourse(List<Currency> baseCurrencies) {
		
		float sum = 0;
		int count = 0;
		for(Currency currency : baseCurrencies){
			sum += currency.getKurs_kupna();
			count++;
		}
		
	
		return sum/count;
	}
	
	public static float avarageSellCourse(List<Currency> baseCurrencies) {
		
		float sum = 0;
		int count = 0;
		for(Currency currency : baseCurrencies){
			sum += currency.getKurs_sprzedazy();
			count++;
		}
		
		
		return sum/count;
	}
	
	public static double standardDeviation(List<Currency> baseCurrencies){
		
		float variance = 0;
		int count = baseCurrencies.size();
		float avarage = avarageSellCourse(baseCurrencies);
		float temp = 0;
		
		for(Currency currency : baseCurrencies){
		   temp+=Math.pow((currency.getKurs_sprzedazy()-avarage),2);
		}
		
		variance=temp/count;
	    
		return roundFourDecimals(Math.sqrt(variance) );
	}
   	
	static double roundFourDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.####");
	return Double.valueOf(twoDForm.format(d));
}
	
	static double roundFourDecimals(float d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.####");
	return Double.valueOf(twoDForm.format(d));
}



}
