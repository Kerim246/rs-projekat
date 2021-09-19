package ba.unsa.etf.rs.project;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ProgressNumber {
    private DoubleProperty broj;

    public final double getBroj(){
        if(broj != null){
            return broj.get();
        }
        return 0;
    }

    public final DoubleProperty brojProperty() {
        if(broj == null){
            broj = new SimpleDoubleProperty(0);
        }
        return broj;
    }

    public final void setBroj(double broj) {
        this.brojProperty().set(broj);
    }
}

