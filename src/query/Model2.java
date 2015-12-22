package query;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

public class Model2 {

	public static class TableModel extends DefaultTableModel{
	    // data in first column
	    private ArrayList<String> names = new ArrayList<String>();
	    // data in second column - can by any object
	    private ArrayList<Object> prices = new ArrayList<Object>();

	    public TableModel(String name){
	    	setNames(name);
	    }

	    public void setNames(String data){
	        lines = data;
	    }
	    
	    public void setValueAt(Object value, int row, int col){
	        prices.set(row, value);
	        fireTableCellUpdated(row, col);
	    }

	    public Object getValueAt(int row, int col){
	        if (col==0)
	            return names.get(row) + " ["+prices.get(row).getClass().getSimpleName()+"]";
	        else return prices.get(row); 
	    }

	    public int getRowCount(){
	        if (prices==null) return 0;
	        else return prices.size();
	    }

	    public int getColumnCount(){
	        return 2;
	    }

	    public boolean isCellEditable(int row, int col){
	        return col==1; // only column 2 is editable
	    }

	    public Class<?> getColumnClass(int col){
	        switch(col){
	        case 0:
	            return String.class;
	        default:
	            return Object.class;
	        }
	    }

	}
}