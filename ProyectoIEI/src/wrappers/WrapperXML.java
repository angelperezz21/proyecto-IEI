package wrappers;

import java.io.FileNotFoundException;

import util.Lector;

public class WrapperXML {
    private String xml;

    public WrapperXML(String path){
        try {
            this.xml = Lector.leerFicheroDeTexto(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    
}