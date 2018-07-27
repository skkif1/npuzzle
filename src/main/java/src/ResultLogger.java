package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ResultLogger
{
    static String baseDir = System.getProperty("user.dir");

    static String separator = File.separator;

    static File file = new File(baseDir + separator + "results");

    static BufferedWriter writer;

    static
    {
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {}
    }



    public void logResult(String res)
    {
        try {
            writer.write(res);
            writer.flush();
        } catch (IOException e) {
        }
    }


    public void close()
    {
        try {
            writer.close();
        } catch (IOException e) {}
    }
}
