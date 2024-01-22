import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> implements Map {
    int size = 0;
    final int DEFAULT_CAPACITY = 16;
    int capacity = DEFAULT_CAPACITY;
    Node[] buckets; // массив ссылок на Node

    // конструкторы
    public MyHashMap(){
        buckets = new Node[DEFAULT_CAPACITY];
    }
    public MyHashMap(int capacity){
        buckets = new Node[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    // добавляет элемент с уникальным ключом в мапу
    @Override
    public V put(Object key, Object value) {
        Node node = new Node(key, value);
        int index = node.hash % capacity;
        // если элемента с таким ключом ещё нет, то кладём его в бакет в конец списка, не забываем про size
        if ( isContained(key)==-1) {
            if (buckets[index] == null) {
                buckets[index] = node;
            } else {
                Node curr = buckets[index];
                while (curr.hasNext())
                    curr = curr.next;
                curr.next = node;
            }
            size++;
            return (V) node.value;
        }
        // а если уже есть, то переходим на него и просто заменяем в нём value
        else {
            Node curr = buckets[index];
            for (int i = 0; i < isContained(key); curr = curr.next, i++) {
            };
            curr.value = value;
            return (V) curr.value;
        }
    }

    // проверяет, есть ли в мапе элемент с заданным ключом
    @Override
    public boolean containsKey(Object key) {
        return (isContained(key)==-1);
    }

    // служебный метод, проверяет, есть ли в мапе элемент с заданным ключом
    // возвращает -1, если нет
    // возвращает позицию в связанном списке, если есть
    private int isContained(Object key){
        int index = key.hashCode() % capacity;
        if ( buckets[index]==null)
            return -1;
        else {
            int position = 0;
            Node curr = buckets[index];
            while (curr.hasNext()) {
                if (curr.key.equals(key))
                    return position;
                else {
                    position++;
                    curr = curr.next;
                }
            }
            return (curr.key.equals(key)) ? position : -1;
        }
    }

    // проверяем, содержится ли value в мапе
    @Override
    public boolean containsValue(Object value) {
        for(int i=0; i<buckets.length;i++){
            if(buckets[i]!=null){
                Node curr = buckets[i];
                while(curr.hasNext()){
                    if(curr.value.equals(value))
                        return true;
                    curr = curr.next;
                }
                if(curr.value.equals(value))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        if(isContained(key)==-1)
            return null;
        else{
            Node curr = buckets[key.hashCode()%capacity];
            for (int i = 0; i < isContained(key); curr = curr.next, i++) {
            };
            return (V) curr.value;
        }


    }



    @Override
    public Object remove(Object key) {
        size--;
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    // объекты внутри бакетов
    class Node<K, V>{
        private K key;
        private V value;
        private int hash;
        private Node next;
        public Node(K key, V value){
            hash = key.hashCode();
            this.key = key;
            this.value = value;
            next = null;
        }
        public void setNext(Node next){
            this.next = next;
        }

        public boolean hasNext(){
            return next!=null;
        }

        public V getValue(){
            return value;
        }
    }


}
