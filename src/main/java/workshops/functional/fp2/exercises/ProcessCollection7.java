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
public class ProcessCollection7 {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    //now we have a single function which transform input state into output result
    //also header removal is now part of computation
    static <B> Collection<B> transformFile(Function<Collection<String>,Collection<B>> computation){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            //LAB - implement execution of computation function here
            throw new UnsupportedOperationException("lab not finished");
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    //DOMAIN LIBRARY START
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


    static Function<Collection<String>,Collection<String>> generateUsersReport = fieldsSummary.andThen(joinSorted);

    //function which converts collection of lines into collection of users lab6
    static Function<Collection<String>,Collection<String>> convertLinesToUsers =FunctionalLibraryV2.liftToCollection(extractUser);
    //DOMAIN LIBRARY END

    //LAB - you need to remove first line - first element in input collection.
    static Function<Collection<String>,Collection<String>> removeHeader= null;

    //this is final computation composed from three independent functions
    static Function<Collection<String>,Collection<String>> computeSummary =
            removeHeader.andThen(convertLinesToUsers).andThen(generateUsersReport);

    public static void main(String[] args){
        transformFile(computeSummary).forEach(logger);
    }
}
//GENERAL FUNXCTIONAL LIBRARY
class FunctionalLibraryV2{
    public static <A,B> Function<Collection<A>,Collection<B>> liftToCollection(Function<A,B> f){
        return input -> input.stream().map(f).collect(Collectors.toList());
    }

    public static <A,B> Function<Optional<A>,Optional<B>> liftToOptional(Function<A,B> f){
        return input-> input.map(f);
    }
}

