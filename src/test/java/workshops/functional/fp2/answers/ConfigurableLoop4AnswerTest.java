package workshops.functional.fp2.answers;

import org.junit.Test;
import workshops.functional.fp2.exercises.ConfigurableLoop4;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop4AnswerTest {

    @Test
    public void testFieldSummaryFunction() throws Exception {
        List<String> input = Arrays.asList("user1", "user2", "user1", "user2", "user1");

        Map<String, Integer> output = ConfigurableLoop4Answer.fieldsSummary.apply(input);

        assertThat(output).containsEntry("user1",3).containsEntry("user2",2);
    }

    //ADDITIONAL

    @Test
    public void testCreateGenericFieldSummary() throws Exception {
        List<String> input = Arrays.asList("user1", "user2", "user1", "user2", "user1");

        Function<Collection<String>, Map<String, Integer>> function = ConfigurableLoop4Answer.createGenericFieldsSummary();
        Map<String, Integer> output = function.apply(input);

        assertThat(output).containsEntry("user1",3).containsEntry("user2",2);
    }
}
