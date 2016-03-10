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
public class ConfigurableLoop4 {

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


    //LAB
    static Function<List<String>,Map<String,Integer>> fieldsSummary = null;

    //ADDITIONAL
    static <A> Function<Collection<A>,Map<A,Integer>> createGenericFieldsSummary(){
        throw new UnsupportedOperationException("lab not implemented");
    }

    public static void main(String[] args){
        queryForMostUsages(extractUser,fieldsSummary).forEach(logger);
    }
}
