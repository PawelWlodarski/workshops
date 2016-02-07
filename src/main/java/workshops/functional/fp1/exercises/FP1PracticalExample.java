package workshops.functional.fp1.exercises;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1PracticalExample {


    static private Map<Integer,Purchase> database=initDatabase();

    public static Optional<Purchase> findPurchase(Integer id){
        throw new RuntimeException("not implemented");
    }

    static Function<Purchase,Collection<BigDecimal>> domainFunction = null;

    static Function<Collection<BigDecimal>, BigDecimal> genericMathFunction=null;

    public static void main(String[] args){
         Function<Purchase,BigDecimal> pureDomainFunction=domainFunction.andThen(genericMathFunction);

         findPurchase(1).map(pureDomainFunction).ifPresent(result-> System.out.println("sum for purchase 1 : "+result));

        BigDecimal priceOfPurchase3 = findPurchase(3).map(pureDomainFunction).orElseThrow(() -> new RuntimeException("there is no purchase with id 3"));
        System.out.println("somehow we have found price of purchase 3 : "+priceOfPurchase3);
    }


    public static Map<Integer,Purchase> initDatabase(){
        Map<Integer,Purchase> database=new HashMap<>();
        Product tv=new Product("TV",new BigDecimal("3000"));
        Product keyboard=new Product("Keyboard",new BigDecimal("120"));
        Product mouse=new Product("Mouse",new BigDecimal("70"));
        Product headphones=new Product("Head Phones",new BigDecimal("200"));

        Purchase p1=new Purchase(1, Arrays.asList(tv,headphones));
        Purchase p2=new Purchase(2, Arrays.asList(headphones,keyboard,mouse));

        database.put(1,p1);
        database.put(2,p2);
        return database;
    }
}


class Product{
    final String name;
    final BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}

class Purchase{
    final Integer id;
    private final List<Product> purchasedProducts;

    public Purchase(Integer id, List<Product> purchasedProducts) {
        this.id = id;
        this.purchasedProducts = purchasedProducts;
    }

    public List<Product> getPurchasedProducts() {
        return Collections.unmodifiableList(purchasedProducts) ;
    }
}
