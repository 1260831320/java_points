import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class PersonalRank {
    private void getRank() throws IOException {
        List<Integer> rank = new ArrayList<>();

        List<Integer> id = new ArrayList<>();
        List<Integer> point = new ArrayList<>();
        File file = new File("D:\\Rank.txt");
        FileReader fileReader = new FileReader(file);
        char[] cha = new char[1024];
        int len = fileReader.read(cha);
        String str = new String(cha, 0, len);
        String[] res = str.split(" ", str.length());
        fileReader.close();
        for (String re : res) {
            if (re.length() < 3 & !re.equals("")) {
                point.add(Integer.parseInt(re));
            } else if (re.length() >= 3 & !re.equals("")) {
                id.add(Integer.parseInt(re));
            }
        }
        for (Integer integer : id) {
            System.out.println(integer);
        }
        for (Integer integer : point) {
            System.out.println(integer);
        }


        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < id.size(); i++) {
            map.put(id.get(i), point.get(i));
        }
        Set<Integer> set = map.keySet();
        Iterator<Integer> iterator = set.iterator();
        System.out.println("Key: ");
        while(iterator.hasNext()){
            System.out.println(iterator.next()+" ");
        }
        Collection<Integer> collection = map.values();
        iterator = collection.iterator();
        System.out.println("Value"+" ");
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }

    public static void main(String[] args) throws IOException {
        PersonalRank personalRank = new PersonalRank();
        personalRank.getRank();
    }
}
    
