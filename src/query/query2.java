package query;

	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

	public class query2 extends JFrame {
		 private static final long serialVersionUID = 1L;

		    public static void main(String[] args) throws NotFound, IOException {
		        query testJFrame = new query();

		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        ArrayList da = new ArrayList();
		        String [] Colunas = new String[] {"Game", "Price", "Available"};


		        columns.add("Game");
		        columns.add("Price");
		        columns.add("Available");

		        List<String> name = (ArrayList<String>) test()[0];
		        List<String> price = (ArrayList<String>) test()[1];
		        List<String> id = (ArrayList<String>) test()[2];

		        String[] newName = new String[name.size()];
		        newName = name.toArray(newName);
		        String[] newPrice = new String[price.size()];
		        newPrice = price.toArray(newPrice);
		        String[] newID = new String[id.size()];
		        newID = id.toArray(newID);

		        
		        for (int i = 0; i < 80; i++) {
		            da.add(new Object[] {newName[i], newPrice[i], parseJSON(newID[i])});
		            //System.out.println(newName[i] + newPrice[i] + parseJSON(newID[i]));
		        }



		        
		        //TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		        Model model = new Model(da, Colunas);
		        JTable table = new JTable(model);
		        table.getColumnModel().getColumn(0).setPreferredWidth(350);
		        //table.getColumnModel().getColumn(1).setPreferredWidth(900);
		        testJFrame.setLayout(new BorderLayout());
		        testJFrame.add(new JScrollPane(table), BorderLayout.CENTER);

		        testJFrame.add(table.getTableHeader(), BorderLayout.NORTH);

		        testJFrame.setVisible(true);
		        testJFrame.setSize(350,800);
		        testJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		        
		        
		        
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
				        List<WebElement> idElement = driver.findElements(By.xpath("//../*[@class='partnum']"));
				     
				        //Formatter formatter = new Formatter();

				        java.util.Iterator<WebElement> i = itemElement.iterator();
				        java.util.Iterator<WebElement> j = priceElement.iterator();
				        java.util.Iterator<WebElement> k = idElement.iterator();
				        List<String> nList = new ArrayList<String>();
						List<String> pList = new ArrayList<String>();
						List<String> idList = new ArrayList<String>();
						
				        while(i.hasNext()) {
				            WebElement name = i.next();
				            WebElement price = j.next();
				            WebElement id= k.next();
				            //formatter = new Formatter();
				            
				            //out.append(String.format("%-70s= %s" , name.getText().replace(" PS4 Game.", ""), price.getText()) + "\n");
				            nList.add(name.getText().replace(" PS4 Game.", "").replace("PS4", "").replace("-", "").replace(".",""));
				            pList.add(price.getText());
				            idList.add(id.getText().replace("/", ""));
				        }

				        driver.quit();
				        return new Object[]{nList, pList,idList};
				
			
			}
		    
		    
			public static boolean parseJSON(String id) throws NotFound, IOException{
				  String sURL = "http://legacy.checkargos.com/Android.php?function=stock&productId="+id+"&storeId=201"; //just a string
				  String zipcode = "";
				  boolean inStock = false;
				    // Connect to the URL using java's native library
				    URL url = new URL(sURL);
				    HttpURLConnection request = (HttpURLConnection) url.openConnection();
				    request.connect();

				    // Convert to a JSON object to print data
				    JsonParser jp = new JsonParser(); //from gson
				    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
				    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
				    zipcode = rootobj.get("stock").getAsString(); //just grab the zipcode
				    
				    //System.out.println(zipcode);
				    
				    if(zipcode.equals("In stock")){
				    	inStock = true;
				    }
				    else if(zipcode.equals("Item is out of stock")){
				    	inStock = false;
				    }
				    else{
				    	inStock = false;
				    }
				    
				    return inStock;
			}
			
			
			
			private static Object[] getItemInfo(String id){
				
				String sURL = "http://glasnost.itcarlow.ie/~softeng4/C00118202/check/Android.php?function=info&storeId=201&productId="+id;
				String name = "", price = "", stock = "";
				boolean inStock;
				
				try{
				URL url = new URL(sURL);
				HttpURLConnection req = (HttpURLConnection) url.openConnection();
				req.connect();
				
				
				JsonParser jp = new JsonParser();
				JsonElement root = jp.parse(new InputStreamReader((InputStream) req.getContent()));
				JsonObject rootobj = root.getAsJsonObject();
				name = rootobj.get("name").getAsString();
				price = rootobj.get("price").getAsString();
				stock = rootobj.get("stock").getAsString();
				
			    if(stock.equals("In stock")){
			    	inStock = true;
			    }
			    else if(stock.equals("Item is out of stock")){
			    	inStock = false;
			    }
			    else{
			    	inStock = false;
			    }
				
				}
				catch(Exception e){
					
				}
				
				 return new Object[]{name, price,stock};
			}
			
			
				public static List getId(){
				
				// turn logging off
						java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
						System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

				        WebDriver driver = new HtmlUnitDriver();
				        
						// visit the webpage
				        driver.get("http://www.argos.ie/webapp/wcs/stores/servlet/Browse?pp=80&s=Price%3A+Low+-+High&storeId=10152&catalogId=14551&langId=111&c_1=1|category_root|Video%2Bgames|14419738&c_2=2|14419738|PS4|37328275&c_3=3|cat_37328275|PS4%2Bgames|37328632&authToken=");

				        List<WebElement> idElement = driver.findElements(By.xpath("//../*[@class='partnum']"));
				     
				        //Formatter formatter = new Formatter();

				        java.util.Iterator<WebElement> k = idElement.iterator();
						List<String> idList = new ArrayList<String>();
						
				        while(k.hasNext()) {

				            WebElement id= k.next();
				            idList.add(id.getText().replace("/", ""));
				        }

				        driver.quit();
				        return idList;
				
			
			}
			
			


		}