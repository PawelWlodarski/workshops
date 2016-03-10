package workshops.functional.fp2.answers;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import workshops.functional.fp2.answers.ConfigurableLoop5Answer.AbstractContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop5AnswerTest {

    @Test
    public void testJoinSorted() throws Exception {
            Map<String,Integer> input = new HashMap(){{
                put("user1",3);
                put("user2",5);
                put("user3",1);
                put("user4",2);
            }};

        List<String> output = ConfigurableLoop5Answer.joinSorted.apply(input);

        assertThat(output).containsExactly("user2:5", "user1:3", "user4:2","user3:1");
    }

    @Test
    public void testLift() throws Exception {
        //given
        Function<String,Integer> addOneToInput=s->Integer.parseInt(s)+1;
        AbstractContainer<String> input=new AbstractContainer<>("55");

        Function<AbstractContainer<String>, AbstractContainer<Integer>> lifted = ConfigurableLoop5Answer.lift(addOneToInput);

        //when
        AbstractContainer<Integer> result = lifted.apply(input);

        //then
        assertThat(result.t).isEqualTo(56);
    }
}
