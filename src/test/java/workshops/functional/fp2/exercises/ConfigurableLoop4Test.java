package workshops.functional.fp2.exercises;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop4Test {

    @Test
    public void testFieldSummaryFunction() throws Exception {
        List<String> input = Arrays.asList("user1", "user2", "user1", "user2", "user1");

        Map<String, Integer> output = ConfigurableLoop4.fieldsSummary.apply(input);

        assertThat(output).containsEntry("user1",3).containsEntry("user2",2);
    }
}
