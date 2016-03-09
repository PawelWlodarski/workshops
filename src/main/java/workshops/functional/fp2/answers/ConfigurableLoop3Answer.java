package workshops.functional.fp2.answers;

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
public class ConfigurableLoop3Answer {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    static List<String> queryForMostUsages(Function<String,String> extractField){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header

            Map<String,Integer> counts=new HashMap<>();

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

    //lab
    static Function<String,String> extractUser=l->l.split(",")[0];

    //additional
    static Function<Integer,Function<String,String>> extractField= index->l->l.split(",")[index];
    static Function<String,String> extractUserCreated=extractField.apply(0);

    public static void main(String[] args){

        queryForMostUsages(extractUser).forEach(logger);
    }
}
