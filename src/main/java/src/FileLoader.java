package src;

import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import src.algo.IHeuristicFunction;
import src.input.InputManagerException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileLoader
{

    static String file = "/Users/omotyliu/files";

    Set<Pair<String, IHeuristicFunction>> getFiles(String file)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            return reader.lines().filter(StringUtils::isNotEmpty).map(this::getPair).collect(Collectors.toSet());
        }
        catch (Exception ex)
        {
            throw new InputManagerException("bad file " + file);
        }
    }


    private Pair<String, IHeuristicFunction> getPair(String line)
    {
        line = StringUtils.trim(line);
        return new Pair<>(StringUtils.substringBefore(line, " "), Main.getFunction(StringUtils.substringAfter(line , " ")));
    }


    public static void main(String[] args)
    {
        new FileLoader().getFiles(file);
    }
}
