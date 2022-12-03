package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Lector {
    public static String leerFicheroDeTexto(String ruta) throws FileNotFoundException{
        // Scanner input = new Scanner(new File(ruta), "utf-8");

        String res = "";
        // while(input.hasNextLine()){
        //     res += input.nextLine();
        // }
        File f = new File(ruta);
        FileInputStream input = new FileInputStream(f);
        byte[] a = new byte[(int) f.length()];
        try {
            input.read(a);
            res = new String(a, "utf-8");
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        return res;
    }
}
