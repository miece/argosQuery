package query;

	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

	public class query extends JFrame {
		// Instance attributes used in this example
		private	JPanel		topPanel;
		private	JList		listbox;

		// Constructor of main frame
		public query()
		{
			// Set the frame characteristics
			setTitle( "Simple ListBox Application" );
			setSize( 500, 800 );
			setBackground( Color.gray );

			// Create a panel to hold all other components
			topPanel = new JPanel();
			topPanel.setLayout( new BorderLayout() );
			JScrollPane scrollPane = new JScrollPane( topPanel );
			getContentPane().add( scrollPane );
			
			ArrayList<String> ar = (ArrayList<String>) test()[0];
			// Create a new listbox control
			listbox = new JList( ar.toArray() );
			topPanel.add( listbox, BorderLayout.CENTER );
		}

		// Main entry point for this example
		public static void main( String args[] )
		{
			// Create an instance of the test application
			query mainFrame	= new query();
			mainFrame.setVisible( true );
			
		}

		
		
		
		
		
		public static Object[] test(){
			
			// turn logging off
					java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
					System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

			        WebDriver driver = new HtmlUnitDriver();
			        
			        StringBuffer out = new StringBuffer();
			        
					// visit the webpage
			        driver.get("http://www.argos.ie/webapp/wcs/stores/servlet/Browse?pp=80&s=Price%3A+Low+-+High&storeId=10152&catalogId=14551&langId=111&c_1=1|category_root|Video%2Bgames|14419738&c_2=2|14419738|PS4|37328275&c_3=3|cat_37328275|PS4%2Bgames|37328632&authToken=");

			        // Find the text input element by its name
			        List<WebElement> itemElement = driver.findElements(By.xpath("//../a[starts-with(@id,'href_')]"));
			        List<WebElement> priceElement = driver.findElements(By.xpath("//../li[starts-with(@class,'price')]"));
			        
			        
			        //Formatter formatter = new Formatter();

			        java.util.Iterator<WebElement> i = itemElement.iterator();
			        java.util.Iterator<WebElement> j = priceElement.iterator();
			        ArrayList<String> nList = new ArrayList<>();
					ArrayList<String> pList = new ArrayList<>();
					ArrayList<String> mainList = new ArrayList<>();
					
			        while(i.hasNext()) {
			            WebElement name = i.next();
			            WebElement price = j.next();

			            //formatter = new Formatter();
			            
			            //out.append(String.format("%-70s= %s" , name.getText().replace(" PS4 Game.", ""), price.getText()) + "\n");
			            nList.add(name.getText().replace(" PS4 Game.", "").replace("PS4", "").replace("-", "").replace(".",""));
			            pList.add(price.getText());
			        }
	
			        driver.quit();
			        return new Object[]{nList, pList};
			
		
		}
		
	}