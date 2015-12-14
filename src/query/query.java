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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

	public class query extends JFrame {
		 private static final long serialVersionUID = 1L;

		    public static void main(String[] args) {
		        query testJFrame = new query();

		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Game");
		        columns.add("Price");

		        List<String> name = (ArrayList<String>) test()[0];
		        List<String> price = (ArrayList<String>) test()[1];

		        String[] newName = new String[name.size()];
		        newName = name.toArray(newName);
		        String[] newPrice = new String[price.size()];
		        newPrice = price.toArray(newPrice);

		        for (int i = 0; i < 80; i++) {
		            values.add(new String[] {newName[i], newPrice[i]});
		        }

		        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		        JTable table = new JTable(tableModel);
		        table.getColumnModel().getColumn(0).setPreferredWidth(1000);
		        table.getColumnModel().getColumn(0).setPreferredWidth(555);
		        testJFrame.setLayout(new BorderLayout());
		        testJFrame.add(new JScrollPane(table), BorderLayout.CENTER);

		        testJFrame.add(table.getTableHeader(), BorderLayout.NORTH);

		        testJFrame.setVisible(true);
		        testJFrame.setSize(700,800);
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
				        List<String> nList = new ArrayList<String>();
						List<String> pList = new ArrayList<String>();
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