package workshops.functional.fp2.exercises;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pwlodarski on 2016-03-09.
 */
public class ProcessCollection8Test {

    @Test
    public void testGroup() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 1, 2, 3, 3, 1, 1, 2, 3, 4,3,3);

        Map<Integer, Integer> result = FunctionalLibraryV3.<Integer>countFunction().apply(numbers);

        assertThat(result)
                .containsEntry(1,4)
                .containsEntry(2,2)
                .containsEntry(3,5)
                .containsEntry(4,1);

    }

    @Test
    public void testSort() throws Exception {
        List<Integer> numbers = Arrays.asList(4,2,3,1);

        Collection<Integer> sorted = FunctionalLibraryV3.sortFunction(Integer::compareTo).apply(numbers);

        assertThat(sorted).containsSequence(1,2,3,4);
    }
}

