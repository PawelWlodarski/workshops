package workshops.functional.fp2.exercises;

import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop3 {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;
    //Notice that now we are passing extract function to the main loop
    static List<String> queryForMostUsages(Function<String,String> extractField){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header

            Map<String,Integer> counts=new HashMap<>();
            //and now we have a control on which field will be extracted
            for (String line : lines) {
                String field = extractField.apply(line);
                counts.compute(field, (k,v)-> v==null? 1 : v+1);
            }

            return counts.entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    //lab - split line and extract user field
    //use unit tests : ConfigurableLoop3Test
    static Function<String,String> extractUser=null;

    //additional -- create function which extracts any field
    static Function<Integer,Function<String,String>> extractField= null;  //this is curried function fieldIndex->line->line.extract(fieldIndex)
    static Function<String,String> extractUserCreated=null; //extractField.apply(0);

    //aditional create generic extract function , notice thta now you cna use any type in extractor
    //check ConfigurableLoop3Test for an example how to extract field into custom User class
    static <A> Function<String,A> extractToObject(Integer index,Function<String,A> fieldToObject){
        throw new UnsupportedOperationException("lab not finished");
    }

    public static void main(String[] args){

        queryForMostUsages(extractUser).forEach(logger);
    }
}
