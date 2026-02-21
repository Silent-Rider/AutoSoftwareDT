import lab1.IntegerSet;

public class Main {
    public static void main(String[] args) {
        IntegerSet integerSet = new IntegerSet();
        integerSet.addAll(1, 2, 3, 4 , 5);
        System.out.println(integerSet);

        System.out.println(integerSet.contains(2));
        System.out.println(integerSet.contains(7));

        integerSet.add(6);
        System.out.println(integerSet);

        integerSet.add(1);
        System.out.println(integerSet);

        integerSet.remove(3);
        System.out.println(integerSet);

        System.out.println(integerSet.remove(9));

        System.out.println(integerSet);
        System.out.println(integerSet.retainAll(1,2,4,5,6,7));
        System.out.println(integerSet);
        System.out.println(integerSet.retainAll(4,5,6,7));
        System.out.println(integerSet);
    }
}