import java.util.*;
import java.io.*;
public class GameHelper {
    private static final String alphabet = "abcdefg";
    private int gridLength = 7;
    private int gridSize = 49;
    private int[] grid = new int[gridSize];
    private int comCont = 0;

    public String getPlaergasse(String prompt) {
        String inputLine = null;
        System.out.print(prompt + " ");
        try{BufferedReader is = new BufferedReader(
                new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0){return null;}

        }catch(IOException e){
            System.out.println("IOExeption: " + e);
        }
        return inputLine.toLowerCase();
    }

    public ArrayList<String> placeDotCom(int comSize){
        ArrayList<String> alphaCells = new ArrayList();
        String [] alphacoords = new String[comSize]; // Хранит координаты типа f6
        String temp = null;                          // Временная строка для конкотенации
        int [] coords = new int[comSize];            // Координаты текущего сайта
        int attempts = 0;                            // Счётчик текущих попыток
        boolean success = false;                     // Нашли подходящее место положение
        int location = 0;                            // Текущее начальное местоположение

        comCont++;                                   // Энный "сайт" для размещения
        int incr = 1;                                // Устанавливаем горизонтальный инкремент
        if((comCont % 2)== 1){                       // Если ненечётный размещаем горизонтально
            incr = gridLength;                       // Устанавливаем вертикальный инкремент
        }

        while (!success & attempts++ < 200){         //Главный поисковый цикл (32)
            location = (int) (Math.random()*gridSize); // Получаем случайную стартовую точку
            //System.out.print("пробуем" + location);
            int x = 0;                               // Энная позиция в "сайте" , которую нужно разместить
            success = true;                          // Предпологаем успешный исход
            while(success && x < comSize){           // Ищем соседнюю неиспользованную ячейку
                if (grid[location] == 0){            // Если ещё не используется
                    coords[x++] = location;          // Сохраняем местоположение
                    location += incr;                // Пробуем следующую" соседнюю ячейку
                    if(location >= gridSize){        // Вышли за рамки - низ
                        success = false;             // Неудача
                    }
                    if(x > 0 && (location % gridLength == 0)){  // Вышли за рамки - правый край
                        success = false;                        // Неудача
                    }
                }else {                                         // Нашли уже использующееся местоположение
                    //System.out.print("испольуется" + location);
                    success = false;                            // Неудача
                }
            }
        }                                                       // Конец while

        int x = 0;                                              //Переводим местоположение в символьные координаты
        int row = 0;
        int column = 0;
        System.out.println("\n");
        while (x < comSize){
            grid[coords[x]] = 1;                                 // Получаем ячейки на главной сетке как "использованные"
            row = (int) (coords[x] / gridLength);                // Получаем значение строки
            column = coords[x] % gridLength;                     // Получаем числовое значение столбца
            temp = String.valueOf(alphabet.charAt(column));      // Преобразуем его в строковый символ

            alphaCells.add(temp.concat(Integer.toString(row)));
            x++;
            System.out.print(coords +"n="+alphaCells); //System .cot.priatf* c o o r d • + ; ; * " = " * aiphaCelis.gei ( x - i) ) ;
        } // |^ Это вырожение должно говорить где именно находится сайт
        System.out.println("\n");

        return alphaCells;
    }

 }
