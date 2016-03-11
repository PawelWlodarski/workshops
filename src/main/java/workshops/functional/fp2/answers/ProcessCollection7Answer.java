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
public class ProcessCollection7Answer {

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

    //functions
    static Function<Integer,Function<String,String>> extractField= index->line->line.split(",")[index];
    static Function<String,String> extractUser=extractField.apply(0);

    static Function<Collection<String>,Map<String,Integer>> fieldsSummary = fields->{
        Map<String,Integer> counts=new HashMap<>();
        for (String field : fields) {
            counts.compute(field, (k,v)-> v==null? 1 : v+1);
        }
        return counts;
    };

    static Function<Map<String,Integer>, Collection<String>> joinSorted = input ->
            input.entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.toList());


    static Function<Collection<String>,Collection<String>> generateUsersReport = fieldsSummary.andThen(joinSorted);

    static Function<Collection<String>,Collection<String>> convertLinesToUsers =FunctionalLibraryV2Answer.liftToCollection(extractUser);

    //LAB
    static Function<Collection<String>,Collection<String>> removeHeader= lines->lines.stream().skip(1).collect(Collectors.toList());

    static Function<Collection<String>,Collection<String>> computeSummary =
            removeHeader.andThen(convertLinesToUsers).andThen(generateUsersReport);

    public static void main(String[] args){
        transformFile(computeSummary).forEach(logger);
    }
}

class FunctionalLibraryV2Answer{
    public static <A,B> Function<Collection<A>,Collection<B>> liftToCollection(Function<A,B> f){
        return input -> input.stream().map(f).collect(Collectors.toList());
    }

    public static <A,B> Function<Optional<A>,Optional<B>> liftToOptional(Function<A,B> f){
        return input-> input.map(f);
    }
}

