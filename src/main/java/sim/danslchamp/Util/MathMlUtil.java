package sim.danslchamp.Util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class MathMlUtil{
    public static String loadTxt(String fileName) throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(new File(MathMlUtil.class.getResource(fileName).toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String str = "";
        int i = 0;

        while ((i = fileReader.read()) != -1){
            str += (char)i;
        }
        return str;
    }
}
