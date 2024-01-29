import animals.Animal;
import animals.Cat;
import animals.Dog;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {
    private static MyHashMap<Animal, City> createTestData(){
        MyHashMap<Animal, City> pets = new MyHashMap<>();
        //pets.toString();
        pets.put(new Cat(2,25, 4 , "Кошь"), new City("Чусовой"));
        pets.put(new Dog(8,38, 10 , "Бобик"), new City("Пермь"));
        pets.put(new Dog(3,25, 4 , "Шарик"), new City("Кунгур"));
        pets.put(new Dog(2,22, 3.5, "Кента" ), new City("Пермь"));
        pets.put(new Cat(3,25, 4 , "Барсик"), new City("Пермь"));
        pets.put(new Cat(2,22, 3.5, "Пушок" ), new City("Кунгур"));
        pets.put(new Dog(4,28, 4, "Кукуша"), new City("Пермь"));
        pets.put(new Dog(10,45, 9 , "Дэвид"), new City("Добрянка"));
        pets.put(new Cat(3,25, 4.1, "Зося"), new City("Пермь"));
        pets.put(new Cat(2.5,28, 6 , "Люма"), new City("Чусовой"));
        pets.put(new Dog(7,50, 12 , "Арчи"), new City("Пермь"));
        pets.put(new Dog(5.5,38, 8.2 , "Бара"), new City("Пермь"));
        pets.put(new Cat(10,26, 6.4, "Тяпа" ), new City("Чусовой"));
        pets.put(new Cat(5,24, 5 , "Кыш"), new City("Пермь"));
        pets.put(new Cat(0.5,15, 2 , "Цапа"), new City("Красновишерск"));
        pets.put(new Dog(0.5,24, 3, "Жучка"), new City("Пермь"));
        pets.put(new Cat(7,23, 3.9, "Чика"), new City("Кунгур"));
        pets.put(new Cat(1,23, 3.5 , "Муся"), new City("Орда"));
        pets.put(new Dog(4.5,30, 7, "Лора"), new City("Чайковский"));
        pets.put(new Dog(2,60, 18, "Ганс"), new City("Добрянка"));

        return pets;
    }

    @Test
    void isEmpty() { // также тестируем put
        MyHashMap<Animal, City> pets = new MyHashMap<>();
        boolean isEmpty = pets.isEmpty();
        pets = createTestData();
        boolean isNotEmpty = !pets.isEmpty();
        assertTrue(isEmpty && isNotEmpty);
    }

    @Test
    void containsKeyTrue() {
        MyHashMap<Animal, City> pets = createTestData();
        assertTrue(pets.containsKey(new Dog(3,25, 4 , "Шарик")));
    }

    @Test
    void containsKeyFalse() {
        MyHashMap<Animal, City> pets = createTestData();
        assertFalse(pets.containsKey(new Dog(3,25, 4.1 , "Шарик")));
    }

    @Test
    void containsValueTrue() {
        MyHashMap<Animal, City> pets = createTestData();
        assertTrue(pets.containsValue(new City("Пермь")));
    }

    @Test
    void containsValueFalse() {
        MyHashMap<Animal, City> pets = createTestData();
        assertFalse(pets.containsValue(new City("Усьва")));
    }

    @Test
    void get() {
        MyHashMap<Animal, City> pets = createTestData();
        Cat key = new Cat(5,24, 5 , "Кыш");
        City value = new City("Пермь");
        assertEquals(value, pets.get(key));
    }

    @Test
    void remove() {
        MyHashMap<Animal, City> pets = createTestData();
        Cat key = new Cat(5,24, 5 , "Кыш");
        City value = new City("Пермь");
        assertEquals(value, pets.remove(key));
        assertNull( pets.get(key), "Вернул не null");
    }

    @Test
    void clear() {
        MyHashMap<Animal, City> pets = createTestData();
        pets.clear();
        assertTrue(pets.isEmpty());
    }

    @Test
    void keySet() {
        Set<Animal> keyset = new HashSet<>();
        keyset.add(new Cat(2,25, 4 , "Кошь"));
        keyset.add(new Dog(8,38, 10 , "Бобик"));
        keyset.add(new Dog(3,25, 4 , "Шарик"));
        keyset.add(new Dog(2,22, 3.5, "Кента" ));
        keyset.add(new Cat(3,25, 4 , "Барсик"));
        keyset.add(new Cat(2,22, 3.5, "Пушок" ));
        keyset.add(new Dog(4,28, 4, "Кукуша"));
        keyset.add(new Dog(10,45, 9 , "Дэвид"));
        keyset.add(new Cat(3,25, 4.1, "Зося"));
        keyset.add(new Cat(2.5,28, 6 , "Люма"));
        keyset.add(new Dog(7,50, 12 , "Арчи"));
        keyset.add(new Dog(5.5,38, 8.2 , "Бара"));
        keyset.add(new Cat(10,26, 6.4, "Тяпа"));
        keyset.add(new Cat(5,24, 5 , "Кыш"));
        keyset.add(new Cat(0.5,15, 2 , "Цапа"));
        keyset.add(new Dog(0.5,24, 3, "Жучка"));
        keyset.add(new Cat(7,23, 3.9, "Чика"));
        keyset.add(new Cat(1,23, 3.5 , "Муся"));
        keyset.add(new Dog(4.5,30, 7, "Лора"));
        keyset.add(new Dog(2,60, 18, "Ганс"));
        MyHashMap<Animal, City> pets = createTestData();
        assertEquals(keyset, pets.keySet());
    }

    @Test
    void values() {
    }

    @Test
    void entrySet() {
        Set<MyHashMap.Node<Animal, City>> testEntryset= new HashSet<>();
        testEntryset.add(new MyHashMap.Node(-5, new Cat(2,25, 4 , "Кошь"), new City("Чусовой")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(8,38, 10 , "Бобик"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(3,25, 4 , "Шарик"), new City("Кунгур")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(2,22, 3.5, "Кента" ), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(3,25, 4 , "Барсик"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(2,22, 3.5, "Пушок" ), new City("Кунгур")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(4,28, 4, "Кукуша"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(10,45, 9 , "Дэвид"), new City("Добрянка")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(3,25, 4.1, "Зося"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(2.5,28, 6 , "Люма"), new City("Чусовой")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(7,50, 12 , "Арчи"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(5.5,38, 8.2 , "Бара"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(10,26, 6.4, "Тяпа" ), new City("Чусовой")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(5,24, 5 , "Кыш"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(0.5,15, 2 , "Цапа"), new City("Красновишерск")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(0.5,24, 3, "Жучка"), new City("Пермь")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(7,23, 3.9, "Чика"), new City("Кунгур")));
        testEntryset.add(new MyHashMap.Node(-5, new Cat(1,23, 3.5 , "Муся"), new City("Орда")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(4.5,30, 7, "Лора"), new City("Чайковский")));
        testEntryset.add(new MyHashMap.Node(-5, new Dog(2,60, 18, "Ганс"), new City("Добрянка")));

        MyHashMap<Animal, City> pets = createTestData();
        boolean result = true;
        for(Map.Entry<Animal, City> entry : pets.entrySet()){
            if( !(pets.get( entry.getKey() ).equals( entry.getValue() )) )
                result = false;
        }
        assertTrue( result);
    }

}