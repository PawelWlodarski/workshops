package workshops.functional.fp2.answers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop4Answer {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    static List<String> queryForMostUsages(Function<String,String> extractField, Function<List<String>,Map<String,Integer>> createSummary){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header
            List<String> fields=lines.stream().map(extractField).collect(Collectors.toList());
            Map<String,Integer> counts=createSummary.apply(fields);
            return counts.entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    static Function<Integer,Function<String,String>> extractField= index->line->line.split(",")[index];
    static Function<String,String> extractUser=extractField.apply(0);

    //Lab
    static Function<List<String>,Map<String,Integer>> fieldsSummary = fields->{
        Map<String,Integer> counts=new HashMap<>();
        for (String field : fields) {
            counts.compute(field, (k,v)-> v==null? 1 : v+1);
        }
        return counts;
    };

    //ADDITIONAL
    static <A> Function<Collection<A>,Map<A,Integer>> createGenericFieldsSummary(){
        return elements -> {
            Map<A,Integer> counts=new HashMap<>();
            for (A field : elements) {
                counts.compute(field, (k,v)-> v==null? 1 : v+1);
            }
            return counts;
        };

    }

    public static void main(String[] args){
        queryForMostUsages(extractUser,fieldsSummary).forEach(logger);
    }
}
