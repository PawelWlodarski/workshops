package workshops.functional.fp2.practice;

import javaslang.control.Try;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pawel on 13.03.16.
 */
public class FP2IOLibrary {

    static Function<String,Try<List<String>>> readLines= filepath ->
            Try.of(()-> Files.readAllLines(Paths.get(ClassLoader.getSystemResource(filepath).toURI())));


    static Function<Collection<String>,Collection<String>> removeHeader= lines->lines.stream().skip(1).collect(Collectors.toList());
}
