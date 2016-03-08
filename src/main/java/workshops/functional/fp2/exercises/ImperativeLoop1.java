package workshops.functional.fp2.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by pwlodarski on 2016-03-08.
 */
public class ImperativeLoop1 {


    static void imperativeLoop(){
        try{
            List<String> lines=Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/transactions.csv").toURI()
                    )
            );

            lines.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        imperativeLoop();
    }

}
