//import stepSort.SortOn2;
//import stepSort.SortOnlogn;
import stepSort.SortApplication;
import stepSort.SortOn;
import util.InputTestGenerator;

public class Main {
    public static void main(String[] args) {
        InputTestGenerator itg= new InputTestGenerator();

//        SortOn2 sort= new SortOn2();
//        SortOnlogn sort= new SortOnlogn();
//        SortOn sort= new SortOn();

        SortApplication sort= new SortApplication();
//        itg.caseGenerator(); //generator
        sort.Problem2108();
    }
}
