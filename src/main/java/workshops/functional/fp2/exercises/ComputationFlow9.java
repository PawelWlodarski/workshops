package workshops.functional.fp2.exercises;

import javaslang.control.Match;
import javaslang.control.Try;
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
public class ComputationFlow9 {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    //LAB
    static Function<String,List<String>> readLines = null;

    //ADDITIONAL
    static Function<String,Optional<List<String>>> safeReadLines= null;


    static Function<String,Try<List<String>>> exceptionallySafeReadLines= null;


    //functions - map records
    static Function<Collection<String>,Collection<String>> removeHeader= lines->lines.stream().skip(1).collect(Collectors.toList());

    static Function<Integer,Function<String,String>> extractField= index->line->line.split(",")[index];
    static Function<String,String> extractUser=extractField.apply(0);

    static Function<Collection<String>,Collection<String>> mapLinesToUsers = FunctionalLibraryV4.liftToCollection(extractUser);

    // functions - reduce to final report
    static Function<Collection<String>,Map<String,Integer>> numberOfOccurences = FunctionalLibraryV4.countFunction();

    static Function<Collection<Map.Entry<String,Integer>>,Collection<Map.Entry<String,Integer>>> naturalSort
            = FunctionalLibraryV4.sortFunction(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    static Function<Collection<Map.Entry<String,Integer>>,Collection<String>> toStringRecords =
            FunctionalLibraryV4.liftToCollection(entry->entry.getKey() + ":" + entry.getValue());

    static Function<Collection<String>,Collection<String>> reduceToUsersReport =
            numberOfOccurences.andThen(m->m.entrySet()).andThen(naturalSort).andThen(toStringRecords);


    //functions final program
    static Function<Collection<String>,Collection<String>> computeSummary =
            removeHeader.andThen(mapLinesToUsers).andThen(reduceToUsersReport);

    static Function<String,Collection<String>> theProgram=readLines.andThen(computeSummary);

    static Function<String,Optional<Collection<String>>> safeProgram=path -> safeReadLines.apply(path).map(computeSummary);

    static Function<String,Try<Collection<String>>> exceptionallySafeProgram=
            path -> exceptionallySafeReadLines.apply(path).map(computeSummary);

    public static void main(String[] args){
        theProgram.apply("fpjava/purchases.csv").forEach(logger);
        safeProgram.apply("fpjava/purchases.csv").orElse(Arrays.asList("THERE WAS UNKNOWN ERROR")).forEach(logger);
        exceptionallySafeProgram.apply("fpjava/purchases.csv")
                .getOrElseThrow(e->new RuntimeException("ERROR DURING COMPUTATION",e))
                .forEach(logger);

    }
}

class FunctionalLibraryV4{
    public static <A,B> Function<Collection<A>,Collection<B>> liftToCollection(Function<A,B> f){
        return input -> input.stream().map(f).collect(Collectors.toList());
    }

    public static <A,B> Function<Optional<A>,Optional<B>> liftToOptional(Function<A,B> f){
        return input-> input.map(f);
    }

    public static <A> Function<Collection<A>,Map<A,Integer>> countFunction(){
        return input->{
            Map<A,Integer> counts=new HashMap<>();
            for (A field : input) {
                counts.compute(field, (k,v)-> v==null? 1 : v+1);
            }
            return counts;
        };
    }

    public static <A> Function<Collection<A>,Collection<A>> sortFunction(Comparator<A> comparator){
        return input->input.stream().sorted(comparator).collect(Collectors.toList());
    }
}