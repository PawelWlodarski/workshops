package workshops.functional.fp2.exercises;

import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop5 {

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
    static Function<Map<String,Integer>, List<String>> joinSorted = null;

    static Function<List<String>,List<String>> transformResult= fieldsSummary.andThen(joinSorted);


    //ADDITIONAL
    static <A,B> Function<AbstractContainer<A>,AbstractContainer<B>> lift(Function<A,B> pure){
            throw new UnsupportedOperationException("lab not completed");
    }

    class AbstractContainer<T>{
        final T t;

        AbstractContainer(T t) {
            this.t = t;
        }
    }



    public static void main(String[] args){
        queryForMostUsages(extractUser,transformResult).forEach(logger);
    }
}
