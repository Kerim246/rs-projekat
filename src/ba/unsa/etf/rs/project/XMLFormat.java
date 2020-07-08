package ba.unsa.etf.rs.project;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.beans.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class XMLFormat implements Serializable {
    private ArrayList<Material> materials = new ArrayList<>();

    public XMLFormat() {
        load();
    }

    public static ArrayList<Material> load() {
        ArrayList<Material> materials = new ArrayList<>();
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream("materials.xml"));

            materials = (ArrayList<Material>) xmlDecoder.readObject();
            xmlDecoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return materials;
    }

    public static void save(ObservableList<Material> mat){
        XMLEncoder XMLdecoder = null;
        ArrayList<Material> materials = new ArrayList<>();
        for(Material m : mat){
            materials.add(m);
        }
        try {
            XMLdecoder = new XMLEncoder(new FileOutputStream("materials.xml"));
            XMLdecoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });

            XMLdecoder.writeObject(materials);
            XMLdecoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
