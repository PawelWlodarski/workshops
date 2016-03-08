package workshops.functional.fp2.exercises;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * Created by pwlodarski on 2016-03-08.
 */
public class LoggerModuleTest {


    @Test
    public void testLogging() throws Exception {
        Consumer<String> testableLogger= null;

        testableLogger.accept("message1");
        testableLogger.accept("message2");
        testableLogger.accept("message3");

        /*
        import static org.assertj.core.api.Assertions.*;
         */
    }

    private class TestableLogger implements LoggerModule.Logging{

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
