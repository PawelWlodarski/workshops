package workshops.functional.general.fpmatters.exercises;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pawel on 21.09.16.
 */
public class FPMattersJavaCompositionExercisesTest {


    @Test
    public void compose(){
        Function<Integer,Integer> f1= i->i+10;
        Function<String,Integer> f2=Integer::parseInt;
        Function<Integer,Double> f3 = i -> i/2.0;

        //Use f1,f2,f3 to receive final result
        Function<String,Double> composed =null;

        assertThat(composed.apply("21")).isEqualTo(15.5);
        assertThat(composed.apply("30")).isEqualTo(20.0);
    }

    @Test
    public void composeCurried(){
        Function<String,Integer> f1=Integer::parseInt;
        BiFunction<Integer,Integer,Integer> f2= (x,y)->x * y;

        //use curry,f1,f2 to build final result
        Function<String,Function<Integer,Integer>> composed= null;

        assertThat(composed.apply("10").apply(3)).isEqualTo(30);
    }

    Function<Integer,Function<Integer,Integer>> curry(BiFunction<Integer,Integer,Integer> f){
        return a->b->f.apply(a,b);
    }


    @Test
    public void sumWithReduce(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        //implement product function with list.stream() framework and stream.reduce
        Function<List<Integer>,Integer> product=null;

        assertThat(sum(integers)).isEqualTo(15);
        assertThat(product.apply(integers)).isEqualTo(120);
    }

    //implement sum method with list.stream() framework and stream.reduce
    Integer sum(List<Integer> input){
        return null;
    }

}
