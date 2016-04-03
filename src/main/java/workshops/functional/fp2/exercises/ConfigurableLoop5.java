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

    //Now Our main loop is more abstract
    //also we accept more general summary function
    static <B> List<B> queryForMostUsages(Function<String,String> extractField, Function<List<String>,List<B>> createSummary){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header
            List<String> fields=lines.stream().map(extractField).collect(Collectors.toList());
            return createSummary.apply(fields);  //create summary is now seen just as a one function
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
    static Function<List<String>,Map<String,Integer>> fieldsSummary = fields->{
        Map<String,Integer> counts=new HashMap<>();
        for (String field : fields) {
            counts.compute(field, (k,v)-> v==null? 1 : v+1);
        }
        return counts;
    };
    //LIBRARY END

    //LAB - copy and refactor code from lab4
    //also as a part of exercise complete tests in ConfigurableLoop5Test
    static Function<Map<String,Integer>, List<String>> joinSorted = null;

    //notice how we composed both functions into a function List<String> -> List<String>
    //the main loop is not aware how many smaller functions are used to create final summary function
    //and this gives us a lot of freedom in composition as long as types are correct
    static Function<List<String>,List<String>> transformResult= fieldsSummary.andThen(joinSorted);


    //ADDITIONAL- decorate simple function so it can be used on Lists level
    //also write test in ConfigurableLoop5Test
    static <A,B> Function<AbstractContainer<A>,AbstractContainer<B>> lift(Function<A,B> pure){
            throw new UnsupportedOperationException("lab not completed");
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
