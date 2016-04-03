package workshops.functional.fp2.exercises;

import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by pwlodarski on 2016-03-08.
 */
public class ImperativeLoop1 {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    static List<String> usersWhoPurchasedMostProducts(){
        try{
            List<String> lines=Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header

            Map<String,Integer> counts=new HashMap<>();

            //LAB
            for (String line : lines) {
                // split line on ','
                // take user field
                // fill counts map with [user -> number of occurences] - you may try Map.compute function
                //map.compute takes (key, BiFunction[key,value])
            }

            return counts.entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public static void main(String[] args){
        usersWhoPurchasedMostProducts().forEach(logger);
    }

}
