package jug.lodz.workshops.starter.traits2;

public class JavaDefaultMethods {

    public static void main(String[] args) {
            System.out.println("Java default methods example");
            Example example=new Example();
            System.out.println(example.method());
    }

}

interface One{
    default String method(){
        return "one";
    }
}

interface Two{
    default String method(){
        return "two";
    }
}

class Example implements  One,Two{

    @Override
    public String method() {
        return  One.super.method() + " example";
//        return  Two.super.method() + " example";
    }
}