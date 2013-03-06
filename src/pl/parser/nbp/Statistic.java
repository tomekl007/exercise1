package pl.parser.nbp;

import java.util.List;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class Statistic {

	public static float avarageBuyCourse(List<Currency> baseCurrencies) {
		
		float sum = 0;
		int count = 0;
		for(Currency currency : baseCurrencies){
			sum += currency.getKurs_kupna();
			count++;
		}
		
		System.out.println(sum + "   - //   " + count     );
		return sum/count;
	}
	
	public static float avarageSellCourse(List<Currency> baseCurrencies) {
		
		float sum = 0;
		int count = 0;
		for(Currency currency : baseCurrencies){
			sum += currency.getKurs_sprzedazy();
			count++;
		}
		
		System.out.println(sum + "   - //   " + count     );
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
	    
		return Math.sqrt(variance);
	}
   	


}
