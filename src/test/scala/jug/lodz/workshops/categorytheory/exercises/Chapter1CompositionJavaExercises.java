package jug.lodz.workshops.categorytheory.exercises;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class Chapter1CompositionJavaExercises {

    @Test
    public void identityTest(){
        assertThat(identity().apply(1)).isEqualTo(1);
        assertThat(identity().apply("aaa")).isEqualTo("aaa");
        assertThat(identity().apply(true)).isEqualTo(true);
    }

    private <A> Function<A,A> identity(){
        return null;
    }


    @Test
    public void compositionTest(){
        Function<User,String> f=u -> u.name;
        Function<String,Integer> g=String::length;

        Function<User,Integer> composed =compose(f,g);

        assertThat(composed.apply(new User("Roman"))).isEqualTo(5);
        assertThat(composed.apply(new User("Agnieszka"))).isEqualTo(9);
    }

    //DON'T use andThen or compose from scala Function type!!!
    private <A,B,C> Function<A,C> compose(Function<A,B> f,Function<B,C> g){
        return null;
    }

    class User{
        public final String name;

        User(String name) {
            this.name = name;
        }
    }
}
