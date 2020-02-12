package DAO;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.text.ParseException;

public interface DaoInterface {
    public static Object getRow(String name) throws ClassNotFoundException, SQLException, ParseException {
        return null;
    }

    public static ObservableList getAllRows() throws ClassNotFoundException, SQLException, ParseException{
        return null;
    };
    public void updateRow();
    public void deleteRow();
    public void add();
}
