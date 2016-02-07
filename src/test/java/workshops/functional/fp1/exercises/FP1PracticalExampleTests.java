package workshops.functional.fp1.exercises;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1PracticalExampleTests {

    @Test
    public void shouldExtractAllPricesFromPurchase(){
        Purchase sut=preparePurchase();

        Collection<BigDecimal> result = FP1PracticalExample.domainFunction.apply(sut);

        assertThat(result,
                hasItems(new BigDecimal("3000"),new BigDecimal("120"),new BigDecimal("70"),new BigDecimal("200"))
        );
    }


    @Test
    public void shouldSumCollectionOfBigDecimals(){
        Collection<BigDecimal> bigDecimals=Arrays.asList(new BigDecimal("3000"),new BigDecimal("120"),new BigDecimal("70"),new BigDecimal("200"));

        BigDecimal result = FP1PracticalExample.genericMathFunction.apply(bigDecimals);

        assertThat(result,is(new BigDecimal("3390")));
    }

    private Purchase preparePurchase(){
            Product tv=new Product("TV",new BigDecimal("3000"));
            Product keyboard=new Product("Keyboard",new BigDecimal("120"));
            Product mouse=new Product("Mouse",new BigDecimal("70"));
            Product headphones=new Product("Head Phones",new BigDecimal("200"));

            return new Purchase(1, Arrays.asList(tv,headphones,mouse,keyboard));

        }
}
