package workshops.functional.fp2.practice;

import javaslang.Tuple;
import javaslang.Tuple3;
import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import static workshops.functional.fp2.practice.FP2FunctionalLibrary.filterFunction;
import static workshops.functional.fp2.practice.FP2FunctionalLibrary.liftToCollection;
import static workshops.functional.fp2.practice.FP2IOLibrary.readLines;
import static workshops.functional.fp2.practice.FP2IOLibrary.removeHeader;

/**
 * Created by pawel on 13.03.16.
 */
public class TransactionsPracticeAnswer {

    public static void main(String[] args){

        Function<Collection<String>, Collection<Tuple3<String, Integer, String>>> toTuples =
                liftToCollection((String line) -> {
                   String[] fields = line.split(",");
                   return Tuple.of(fields[0], Integer.parseInt(fields[1]), fields[2]);
                });

        Function<Collection<Tuple3<String, Integer, String>>, Collection<Tuple3<String, Integer, String>>> filter001 =
                filterFunction((Tuple3<String, Integer, String> t)
                    -> "001".equals(t._1) || "001".equals(t._3)
                );

        Function<Collection<Tuple3<String, Integer, String>>, Collection<Integer>> mapToTransfers =
                liftToCollection((Tuple3<String, Integer, String> t) -> "001".equals(t._1) ? -t._2 : t._2);

        Function<Collection<String>, Collection<Integer>> transformation =
                removeHeader.andThen(toTuples).andThen(filter001).andThen(mapToTransfers);


        readLines.apply("fpjava/transactions.csv")
                .map(transformation)
                .map((Collection<Integer> sums)->sums.stream().reduce((i1,i2)->i1+i2))
                .getOrElseThrow(e->new RuntimeException("ERROR DURING COMPUTATION",e))
                .ifPresent(System.out::println);


    }
}
