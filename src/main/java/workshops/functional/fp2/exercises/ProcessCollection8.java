package workshops.functional.fp2.exercises;

import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ProcessCollection8 {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    static <B> Collection<B> transformFile(Function<Collection<String>,Collection<B>> computation){
        try{
            List<String> lines= Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            return computation.apply(lines);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    //DOMAIN LIBRARY
    //DATA PREPARATION PHASE - removing header
    static Function<Collection<String>,Collection<String>> removeHeader= lines->lines.stream().skip(1).collect(Collectors.toList());

    //MAP PHASE
    //Extracting functions
    static Function<Integer,Function<String,String>> extractField= index->line->line.split(",")[index];
    static Function<String,String> extractUser=extractField.apply(0);

    // mapping lines to users
    static Function<Collection<String>,Collection<String>> mapLinesToUsers =FunctionalLibraryV1.liftToCollection(extractUser);

    //REDUCE PHASE
    // general count function - to be implemented
    static Function<Collection<String>,Map<String,Integer>> numberOfOccurences = FunctionalLibraryV3.countFunction();

    // general sort fucntion - to be implemented
    static Function<Collection<Map.Entry<String,Integer>>,Collection<Map.Entry<String,Integer>>> naturalSort
            = FunctionalLibraryV3.sortFunction(Map.Entry.comparingByValue());

    // building report rows
    static Function<Collection<Map.Entry<String,Integer>>,Collection<String>> toStringRecords =
            FunctionalLibraryV3.liftToCollection(entry->entry.getKey() + ":" + entry.getValue());

    //COMPOSED REDUCE PHASE
    static Function<Collection<String>,Collection<String>> reduceToUsersReport =
            numberOfOccurences.andThen(m->m.entrySet()).andThen(naturalSort).andThen(toStringRecords);


    //FINAL PROGRAM
    static Function<Collection<String>,Collection<String>> computeSummary =
            removeHeader.andThen(mapLinesToUsers).andThen(reduceToUsersReport);

    public static void main(String[] args){
        transformFile(computeSummary).forEach(logger);
    }
}

//LAB - use test : ProcessCollection8Test
//GENERAL LIBRARY
class FunctionalLibraryV3{
    public static <A,B> Function<Collection<A>,Collection<B>> liftToCollection(Function<A,B> f){
        return input -> input.stream().map(f).collect(Collectors.toList());
    }

    public static <A,B> Function<Optional<A>,Optional<B>> liftToOptional(Function<A,B> f){
        return input-> input.map(f);
    }

    //LAB - copy and refactor field summary from previous exercise
    public static <A> Function<Collection<A>,Map<A,Integer>> countFunction(){
        throw new UnsupportedOperationException("lab not implemented");
    }

    //ADDITIONAL - copy and refactor part of joinsort function from previous exercise
    public static <A> Function<Collection<A>,Collection<A>> sortFunction(Comparator<A> comparator){
        throw new UnsupportedOperationException("lab not implemented");
    }
}
