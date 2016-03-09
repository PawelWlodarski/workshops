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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop3Test {

    @Test
    public void testExtractUser() throws Exception {
        String line="user1,tv,3000,01-02-2016";

        String result = ConfigurableLoop3.extractUser.apply(line);

        assertThat(result).isEqualTo("user1");
    }

    @Test
    public void testExtractField() throws Exception {
        String line="user1,tv,3000,01-02-2016";

        String result = ConfigurableLoop3.extractField.apply(1).apply(line);

        assertThat(result).isEqualTo("tv");
    }
}
