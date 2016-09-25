package workshops.functional.general.fpmatters.exercises;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pawel on 25.09.16.
 */
public class FPMattersHighOrderFunctionsExercise {

    @Test
    public void customCurrying() {
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        Function<Integer, Function<Integer, Integer>> curried = curry(add);
        BiFunction<Integer, Integer, Integer> uncurried = uncurry(curried);

        assertThat(add.apply(3, 4)).isEqualTo(7);
        assertThat(curried.apply(3).apply(4)).isEqualTo(7);
        assertThat(uncurried.apply(3, 4)).isEqualTo(7);
    }
    //replace null
    <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> f) {
        return null;
    }
    //replace null
    <A, B, C> BiFunction<A, B, C> uncurry(Function<A, Function<B, C>> f) {
        return null;
    }


    @Test
    public void testCustomFoldR(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        assertThat(foldR((x,y)->x+y,0,numbers)).isEqualTo(15);
        assertThat(foldR((x,y)->x*y,1,numbers)).isEqualTo(120);
    }

    //fp clean code -f,as
    <A, B> B foldR(BiFunction<A, B, B> f, B zero, Collection<A> as) {
        B acc = null; //replace null with proper assignemnt

        for (A a : as) {
            acc = null; //replace null with proper acc computation
        }

        return acc;
    }

    @Test
    public void testFilter(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5,6,7,8);

        assertThat(filterViaFoldR(x->x>3,numbers)).isEqualTo(Arrays.asList(4, 5,6,7,8));
    }

    //STATEMENT vs EXPRESSION
    <A> Collection<A> filterViaFoldR(Function<A,Boolean> f, Collection<A> xs){
        BiFunction<A,Collection<A>,Collection<A>> filterOut=null; //replace null with function body

        return foldR(filterOut,new LinkedList<>(),xs);
    }

    @Test
    public void testMap(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        assertThat(mapViaFoldR(x->x+1,numbers)).isEqualTo(Arrays.asList(2, 3, 4, 5,6));
    }



    <A,B> Collection<B> mapViaFoldR(Function<A,B> f,Collection<A> xs){
        BiFunction<A,Collection<B>,Collection<B>> map=null; //replace null with function body
        return foldR(map,new LinkedList<>(),xs);
    }

}
