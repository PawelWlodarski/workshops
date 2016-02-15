package workshops.functional.fp1.theory;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1Theory {

    public static void main(String[] args){
        //example 1
        MutableNumber a=new MutableNumber(2);
        MutableNumber b=new MutableNumber(3);

//        print(addMutable(a,b));
//        print("a="+a);
//        print("b="+b);

        //example 2
        int a2=2;
        int b2=3;
//        print(addEncapsulated(a2,b2));
//        print("a2="+a2);
//        print("b2="+b2);


        //example 3
//        print(div(4,2));
//        print(div(4,0));

        //example 4
        addWithConsole(3,4);
    }


    public static MutableNumber addMutable(MutableNumber a, MutableNumber b) {
        while (b.getValue() > 0) {
            a.increment();
            b.decrement();
        }
        return a;
    }

    public static int addEncapsulated(int a, int b) {
        while (b > 0) {
            a++;
            b--;
        }
        return a;
    }

    public static int div(int a, int b) {
        return a / b;
    }

    public static int addWithConsole(int a, int b) {
        while (b > 0) {
            a++;
            b--;
        }
        System.out.println(a);
        return a;
    }


    static <A> void print(A line){
        System.out.println(line);
    }
}

class MutableNumber{
    private int value=0;

    public MutableNumber(int value) {
        this.value = value;
    }

    public void increment(){
        value=value+1;
    }

    public void decrement(){
        value=value-1;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MutableNumber("+value+")";
    }
}