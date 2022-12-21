package wrappers;

import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import util.Lector;

public class Wrapper_IB {
    private String xml;

    public Wrapper_IB(String path){
        try {
            this.xml = Lector.leerFicheroDeTexto(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getJSON(){
        JSONObject obj = (JSONObject)XML.toJSONObject(this.xml);
        JSONObject response = (JSONObject)obj.get("response");
        JSONObject row1 = (JSONObject)response.get("row");
        JSONArray row2 = (JSONArray)row1.get("row");
        return row2.toString();
    }
}