package ru.arkhipov.MyFirstTestAppSpringBoot.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class HelloController {

    ArrayList<String> myList = null;
    HashMap<Integer, String> myMap = null;
    int count = 0;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name + "!";
    }

    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam String s) {
        if (myList == null) {
            myList = new ArrayList<String>();
        }
        myList.add(s);
        return "Добавил в список: " + s + ". Теперь в списке " + myList.size() + " элементов";
    }

    @GetMapping("/show-array")
    public String showArrayList() {
        if (myList == null) {
            return "Список еще пустой, сначала добавьте элементы";
        }
        if (myList.isEmpty()) {
            return "Список создан, но в нем ничего нет";
        }
        String result = "Вот что в списке:\n";
        for (int i = 0; i < myList.size(); i++) {
            result += "[" + i + "] = " + myList.get(i) + "\n";
        }
        return result;
    }

    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam String s) {
        if (myMap == null) {
            myMap = new HashMap<Integer, String>();
            count = 1;
        }
        myMap.put(count, s);
        String answer = "Добавил в мапу: ключ=" + count + ", значение=" + s;
        count++;
        return answer;
    }

    @GetMapping("/show-map")
    public String showHashMap() {
        if (myMap == null) {
            return "Мапа еще пустая, сначала добавьте элементы";
        }
        if (myMap.isEmpty()) {
            return "Мапа создана, но в ней ничего нет";
        }
        String result = "Вот что в мапе:\n";
        for (Integer key : myMap.keySet()) {
            result += "Ключ: " + key + " -> Значение: " + myMap.get(key) + "\n";
        }
        return result;
    }

    @GetMapping("/show-all-length")
    public String showAllLength() {
        int listSize = 0;
        int mapSize = 0;
        if (myList != null) {
            listSize = myList.size();
        }
        if (myMap != null) {
            mapSize = myMap.size();
        }
        return "Размер ArrayList: " + listSize + "\n" +
                "Размер HashMap: " + mapSize;
    }
}