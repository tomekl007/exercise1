package pl.parser.nbp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 * 
 * @author Tomasz Lelek
 * xml Parser 
 *
 */
public class ParseXmlHelper {

	
	/**
	 * 
	 * @param path - path where serach xml file
	 * @param currencyToFind - what currency looking for
	 * @return Currency object
	 */
	public static Currency parseXmlFile(String path,String currencyToFind){
		try {

			File course = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(course);
			doc.getDocumentElement().normalize();

		
			NodeList nodes = doc.getElementsByTagName("pozycja");
		

		
			for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
		
			String kod_waluty =getValue("kod_waluty", element);
		
			
			if(kod_waluty.equals(currencyToFind))
			return new Currency(getValue("kod_waluty", element),getValue("kurs_kupna", element)
					,getValue("kurs_sprzedazy", element));
			}
			}
			} catch (Exception ex) {
			ex.printStackTrace();
			}
		
		return null;
			}

			private static String getValue(String tag, Element element) {
				
		
			NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node = (Node) nodes.item(0);
			
				return node.getNodeValue();

			}
}

		



