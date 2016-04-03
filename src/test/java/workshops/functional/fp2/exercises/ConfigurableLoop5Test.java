package workshops.functional.fp2.exercises;

import org.junit.Test;
import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop5Test {

    @Test
    public void testJoinSorted() throws Exception {
            Map<String,Integer> input = new HashMap(){{
                put("user1",3);
                put("user2",5);
                put("user3",1);
                put("user4",2);
            }};

        List<String> output = ConfigurableLoop5.joinSorted.apply(input);

        //LAB - write assertion for output
    }

    //ADDITIONAL - write tests for lift
}
