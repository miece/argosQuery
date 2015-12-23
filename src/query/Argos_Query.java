package query;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jaunt.NotFound;

public class Argos_Query extends JFrame {
    public Argos_Query() {
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JTable table = new JTable(new ModelData());
        add(new JScrollPane(table));
        setVisible(true);
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                //int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0) {
                   // System.out.println("Id: " + row );
                    System.out.println("Id: " + table.getModel().getValueAt(row, 3) );
                    try {
						table.setValueAt(checkInStock(table.getModel().getValueAt(row, 3).toString()), row, 2);
					} catch (NotFound | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                }
            }
        });
    }
    
    
    public static boolean checkInStock(String id) throws NotFound, IOException{
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

    public static void main(String[] args) {
        new Argos_Query();
    }
    
    
    
    
    public static Object[] getItemInfo(){
		
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
		     
		        java.util.Iterator<WebElement> i = itemElement.iterator();
		        java.util.Iterator<WebElement> j = priceElement.iterator();
		        java.util.Iterator<WebElement> k = idElement.iterator();
		        List<String> nList = new ArrayList<String>();
				List<String> pList = new ArrayList<String>();

				
		        while(i.hasNext()) {
		            WebElement name = i.next();
		            WebElement price = j.next();
		            WebElement id= k.next();
		            
		            nList.add(name.getText().replace(" PS4 Game.", "").replace("PS4", "").replace("-", "").replace(".",""));
		            pList.add(price.getText());
		        }

		        driver.quit();
		        return new Object[]{nList, pList};
	}
    

	public static List<String> getId(){
	
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

class ModelData extends AbstractTableModel {


    List<String> name = (ArrayList<String>) Argos_Query.getItemInfo()[0];
    List<String> price = (ArrayList<String>) Argos_Query.getItemInfo()[1];
    List<String> id = (ArrayList<String>) Argos_Query.getId();
    
	
    List<Data> data = new ArrayList<Data>();
    String colNames[] = { "Name", "Type", "Available" };
    Class<?> colClasses[] = { String.class, String.class, Boolean.class, String.class };
    
    

    ModelData() {
        //data.add(new Data("name 1", "type 1", new Date()));
        //data.add(new Data("name 2", "type 2", new Date()));
        //data.add(new Data("name 3", "type 3", new Date()));
    	
        for (int i = 0; i < 80; i++) {
        	data.add(new Data(name.get(i) ,price.get(i), false , id.get(i)));
        }
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return data.get(rowIndex).getName();
        }
        if (columnIndex == 1) {
            return data.get(rowIndex).getPrice();
        }
        if (columnIndex == 2) {
            return data.get(rowIndex).getBool();
        }
        if (columnIndex == 3) {
            return data.get(rowIndex).getID();
        }
        return null;
    }

    public String getColumnName(int columnIndex) {
        return colNames[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return colClasses[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            data.get(rowIndex).setName((String) aValue);
        }
        if (columnIndex == 1) {
            data.get(rowIndex).setPrice((String) aValue);
        }
        if (columnIndex == 2) {
            data.get(rowIndex).setBool((Boolean) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}

class Data {
    String name;
    String price;
    Boolean boo;
    String id;

    public Data(String name, String price, Boolean boo, String id) {
        super();
        this.name = name;
        this.price = price;
        this.boo = boo;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getBool() {
        return boo;
    }

    public void setBool(Boolean boo) {
        this.boo = boo;
    }
    
    public String getID() {
        return id;
    }
}