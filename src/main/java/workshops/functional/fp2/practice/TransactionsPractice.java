package workshops.functional.fp2.practice;

import javaslang.Tuple3;

import java.util.Collection;
import java.util.function.Function;

import static workshops.functional.fp2.practice.FP2IOLibrary.readLines;
import static workshops.functional.fp2.practice.FP2IOLibrary.removeHeader;

/**
 * Created by pawel on 13.03.16.
 */
public class TransactionsPractice {

    public static void main(String[] args){

        Function<Collection<String>, Collection<Tuple3<String, Integer, String>>> toTuples = null;

        Function<Collection<Tuple3<String, Integer, String>>, Collection<Tuple3<String, Integer, String>>> filter001 = null;

        Function<Collection<Tuple3<String, Integer, String>>, Collection<Integer>> mapToTransfers = null;

        Function<Collection<String>, Collection<Integer>> transformation =
                removeHeader.andThen(toTuples).andThen(filter001).andThen(mapToTransfers);


        readLines.apply("fpjava/transactions.csv")
                .map(transformation)
                .map((Collection<Integer> sums)->sums.stream().reduce((i1,i2)->i1+i2))
                .getOrElseThrow(e->new RuntimeException("ERROR DURING COMPUTATION",e))
                .ifPresent(System.out::println);


    }
}
