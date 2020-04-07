package phone_book.domain;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Если это не персистент объект до думаю нет смысла ему лежать в domain
 * Можно было сделать и справочников с возможностью редактирования
 */
public class Street {


    private static List<String> streets;

    static{
        try {
            // Можно путь сделать относительным
            BufferedReader bf = new BufferedReader(new FileReader(Paths.get("src/main/resources/str.txt").toFile()));
            String[] str = bf.readLine().split(", ");
            streets = Arrays.asList(str);
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String>  getAllStreet(){
        return streets;
    }

    public static int getIdByStreet(String street){
       return streets.indexOf(street);
    }
}
