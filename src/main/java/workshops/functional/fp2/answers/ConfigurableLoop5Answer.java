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
public class ConfigurableLoop5Answer {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    static <B> List<B> queryForMostUsages(Function<String,String> extractField, Function<List<String>,List<B>> createSummary){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header
            List<String> fields=lines.stream().map(extractField).collect(Collectors.toList());
            return createSummary.apply(fields);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    static Function<Integer,Function<String,String>> extractField= index->line->line.split(",")[index];
    static Function<String,String> extractUser=extractField.apply(0);

    static Function<List<String>,Map<String,Integer>> fieldsSummary = fields->{
        Map<String,Integer> counts=new HashMap<>();
        for (String field : fields) {
            counts.compute(field, (k,v)-> v==null? 1 : v+1);
        }
        return counts;
    };

    //LAB
    static Function<Map<String,Integer>, List<String>> joinSorted = input ->
        input.entrySet().stream()
                .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.toList());


    static Function<List<String>,List<String>> transformResult= fieldsSummary.andThen(joinSorted);


    //ADDITIONAL
    static <A,B> Function<AbstractContainer<A>,AbstractContainer<B>> lift(Function<A,B> pure){
           return  input -> new AbstractContainer<>(pure.apply(input.t));
    }

    static class AbstractContainer<T>{
        final T t;

        AbstractContainer(T t) {
            this.t = t;
        }
    }



    public static void main(String[] args){
        queryForMostUsages(extractUser,transformResult).forEach(logger);
    }
}
