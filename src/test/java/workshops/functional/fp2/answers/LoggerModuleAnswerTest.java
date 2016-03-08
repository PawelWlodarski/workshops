package workshops.functional.fp2.answers;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;



/**
 * Created by pwlodarski on 2016-03-08.
 */
public class LoggerModuleAnswerTest {


    @Test
    public void testLogging() throws Exception {
        TestableLogger loggerWithMemory = new TestableLogger();
        Consumer<String> testableLogger= LoggerModuleAnswer.createLogger.apply(loggerWithMemory);

        testableLogger.accept("message1");
        testableLogger.accept("message2");
        testableLogger.accept("message3");

        assertThat(loggerWithMemory.getLogs()).contains("message1","message2","message3");
    }

    private class TestableLogger implements LoggerModuleAnswer.Logging{

        @Override
        public void log(String message) {
            logs.add(message);
        }

        public Collection<String> getLogs() {
            return Collections.unmodifiableCollection(logs);
        }

        private Collection<String> logs=new LinkedList<>();
    }
}
