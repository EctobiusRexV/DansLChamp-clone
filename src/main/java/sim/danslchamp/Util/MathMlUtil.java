package sim.danslchamp.Util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class MathMlUtil{
    public static String loadTxt(String fileName) throws IOException {
        InputStream fileReader  = MathMlUtil.class.getResourceAsStream(fileName);

        String str = "";
        int i = 0;

        while ((i = fileReader.read()) != -1){
            str += (char)i;
        }
        return str;
    }
}
