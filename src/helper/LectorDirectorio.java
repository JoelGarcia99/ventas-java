package helper;

import java.io.File;
import java.util.ArrayList;

public class LectorDirectorio {
    public static ArrayList<String> leeArhivosEnCarpeta(File carpeta) {

        ArrayList<String> archivo = new ArrayList<String>();

		for (File file : carpeta.listFiles()) {
            System.out.println(file.getAbsolutePath());

            String [] splitted = file.getName().split("\\.");
            if(splitted.length == 0) continue;


            if(splitted[splitted.length-1].toUpperCase().equals("UTM")) {
                archivo.add(file.getPath());
            }
		}

        return archivo;
	}
}
