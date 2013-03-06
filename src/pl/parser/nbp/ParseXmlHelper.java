package pl.parser.nbp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//Read more: http://javarevisited.blogspot.com/2011/12/parse-xml-file-in-java-example-tutorial.html#ixzz2Mmekw2N3


public class ParseXmlHelper {

	// static NodeList nodes;
	
	public static Currency parseXmlFile(String path,String currencyToFind){
		try {

			File course = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(course);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("pozycja");
			System.out.println("==========================");

			System.out.println(nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			System.out.println("kod_waluty: " + getValue("kod_waluty", element));
			String kod_waluty =getValue("kod_waluty", element);
			//System.out.println("kurs_kupna: " + getValue("kurs_kupna", element));
			//System.out.println("kurs_sprzedazy: " + getValue("kurs_sprzedazy", element));
			
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
				
				//for(int i = 0; i < nodes.getLength(); i++){
			NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node = (Node) nodes.item(0);
			
				return node.getNodeValue();

			}
}

			//Read more: http://javarevisited.blogspot.com/2011/12/parse-xml-file-in-java-example-tutorial.html#ixzz2MmesoT3N



