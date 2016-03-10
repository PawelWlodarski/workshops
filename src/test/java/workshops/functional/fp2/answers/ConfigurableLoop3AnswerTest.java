package workshops.functional.fp2.answers;

import org.junit.Test;
import workshops.functional.fp2.exercises.ConfigurableLoop3;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ConfigurableLoop3AnswerTest {

    @Test
    public void testExtractUser() throws Exception {
        String line="user1,tv,3000,01-02-2016";

        String result = ConfigurableLoop3Answer.extractUser.apply(line);

        assertThat(result).isEqualTo("user1");
    }

    @Test
    public void testExtractField() throws Exception {
        String line="user1,tv,3000,01-02-2016";

        String result = ConfigurableLoop3Answer.extractField.apply(1).apply(line);

        assertThat(result).isEqualTo("tv");
    }

    @Test
    public void extractObjectTest() throws Exception {
        String line="user1,tv,3000,01-02-2016";

        User result = ConfigurableLoop3Answer.extractToObject(0, User::new).apply(line);
//        ConfigurableLoop3.extractToObject(0, login -> new User(login) );

        assertThat(result.login).isEqualTo("user1");
    }

    class User{
        final String login;

        User(String login) {
            this.login = login;
        }
    }
}
