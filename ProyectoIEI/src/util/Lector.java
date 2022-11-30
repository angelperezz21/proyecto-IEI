package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Lector {
    public static String leerFicheroDeTexto(String ruta) throws FileNotFoundException{
        Scanner input = new Scanner(new File(ruta), "utf-8");

        String res = "";

        while(input.hasNext()){
            res += input.nextLine();
        }

        input.close();

        return res;
    }
}
