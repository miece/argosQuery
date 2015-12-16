package query;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class Model extends AbstractTableModel {
    private ArrayList lines = null;
    private String [] columns = null;

    public Model(ArrayList lin, String[] col){
        setLinhas(lin);
        setColunas(col);
    }
    public ArrayList getLinhas(){
        return lines;

    }
    public void setLinhas(ArrayList data){
        lines = data;
    }
    public String[] getColunas(){
        return columns;
    }
    public void setColunas(String [] names){
    	columns = names;
    }
    public int getColumnCount(){
        return columns.length;
    }
    public int getRowCount(){
        return lines.size();
    }
    public String getColumnCount (int numCol){
        return columns[numCol];
    }
    

    public Class getColumnClass(int column) {
      return (getValueAt(0, column).getClass());
    }

    public Object getValueAt(int numLin, int numCol){
        Object[] linha = (Object[])getLinhas().get(numLin);
        return linha[numCol];
    }
}