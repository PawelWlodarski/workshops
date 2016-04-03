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
public class ProcessCollection6 {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    //we generalized main loop by replacing List with Collection
    static <B> Collection<B> queryForMostUsages(Function<Collection<String>,Collection<String>> extractFields,
                                          Function<Collection<String>,Collection<B>> createSummary){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header
            //NOTICE that we no longer iterate in the main loop
            Collection<String> fields=extractFields.apply(lines);
            return createSummary.apply(fields);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    //LIBRARY START
    //Extracting functions implemented in lab3
    static Function<Integer,Function<String,String>> extractField= index->line->line.split(",")[index];
    static Function<String,String> extractUser=extractField.apply(0);

    //summary function created in lab4
    static Function<Collection<String>,Map<String,Integer>> fieldsSummary = fields->{
        Map<String,Integer> counts=new HashMap<>();
        for (String field : fields) {
            counts.compute(field, (k,v)-> v==null? 1 : v+1);
        }
        return counts;
    };

    //function which sort entries and produce report lines - defined in lab5
    static Function<Map<String,Integer>, Collection<String>> joinSorted = input ->
            input.entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.toList());


    static Function<Collection<String>,Collection<String>> transformResult= fieldsSummary.andThen(joinSorted);
    //LIBRARY END

    //lab - decorate simple function String->String, into function on lists level List<String> -> List<String>
    // tests - ProcessCollection6Test
    static Function<Collection<String>,Collection<String>> linesToUsers =FunctionalLibraryV1.liftToCollection(extractUser);


    public static void main(String[] args){
        queryForMostUsages(linesToUsers,transformResult).forEach(logger);
    }



}


//LAB
//General Functional Library
//tests ProcessCollection6Test
class FunctionalLibraryV1{
    //LAB - lift simple function to collections level
    public static <A,B> Function<Collection<A>,Collection<B>> liftToCollection(Function<A,B> f){
        throw new UnsupportedOperationException("lab not implemented");
    }

    //ADDITIONAL - lift simple function to options level
    public static <A,B> Function<Optional<A>,Optional<B>> liftToOptional(Function<A,B> f){
        throw new UnsupportedOperationException("lab not implemented");
    }
}
