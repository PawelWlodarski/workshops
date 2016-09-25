package workshops.functional.general.fpmatters.answers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by pawel on 21.09.16.
 */
public class FPMattersJavaCompositionAnswersTest {


    @Test
    public void compose() {
        Function<Integer, Integer> f1 = i -> i + 10;
        Function<String, Integer> f2 = Integer::parseInt;
        Function<Integer, Double> f3 = i -> i / 2.0;

        Function<String, Double> composed = f2.andThen(f1).andThen(f3);

        assertThat(composed.apply("21")).isEqualTo(15.5);
        assertThat(composed.apply("30")).isEqualTo(20.0);
    }

    @Test
    public void composeCurried() {
        Function<String, Integer> f1 = Integer::parseInt;
        BiFunction<Integer, Integer, Integer> f2 = (x, y) -> x * y;

        Function<String, Function<Integer, Integer>> composed = f1.andThen(curry(f2));

        assertThat(composed.apply("10").apply(3)).isEqualTo(30);
    }

    Function<Integer, Function<Integer, Integer>> curry(BiFunction<Integer, Integer, Integer> f) {
        return a -> b -> f.apply(a, b);
    }


    @Test
    public void sumWithReduce() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        Function<List<Integer>, Integer> product = input -> input.stream().reduce(1, (x, y) -> x * y);

        assertThat(sum(integers)).isEqualTo(15);
        assertThat(product.apply(integers)).isEqualTo(120);
    }


    Integer sum(List<Integer> input) {
        return input.stream().reduce(0, (x, y) -> x + y);
    }

    @Test
    public void reverseWithFoldR() {
        javaslang.collection.List<Integer> slangList = javaslang.collection.List.of(1, 2, 3, 4, 5);

        Function<javaslang.collection.List<Integer>, javaslang.collection.List<Integer>> reverse = l -> l.foldRight(
                javaslang.collection.List.empty(),
                (e, acc) -> acc.append(e)
        );

        assertThat(reverse.apply(slangList)).isEqualTo(javaslang.collection.List.of(5, 4, 3, 2, 1));
    }

}
