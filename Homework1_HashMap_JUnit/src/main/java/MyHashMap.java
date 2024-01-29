import java.util.*;

/**
 * Класс - собственная реализация HashMap.
 * @autor Евгения Филипенко
 * @version 1.0
 */
public class MyHashMap<K, V> implements Map<K, V> {

    /** Поле-константа для ёмкости по умолчанию внутреннего массива бакетов */
    private final int DEFAULT_CAPACITY = 16;
    /** Поле для ёмкости по внутреннего массива бакетов */
    private int capacity = DEFAULT_CAPACITY;
    /** Количество элементов, записанное в мапу */
    private int size = 0;
    /** Поле - массив ссылок на элементы мапы {@link Node} */
    private Node<K,V>[] buckets;

    /**
     * Конструктор - создание нового объекта с внутренним массивом с длиной по умолчанию
     * @see MyHashMap#MyHashMap(int)
     */
    public MyHashMap(){
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }
    /**
     * Конструктор - создание нового объекта с внутренним массивом заданной длины
     * @param capacity - длина массива
     */
    public MyHashMap(int capacity){
        buckets = new Node[capacity];
    }


    /**
     * Возвращает значение поля {@link MyHashMap#size}
     * @return количество элементов (пар ключ-значение) в мапе
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет наличие элементов в мапе
     * @return true, если в мапе есть хотя бы один элемент
     */
    @Override
    public boolean isEmpty() {
        for(Node<K, V> bucket : buckets)
            if (bucket!=null)
                return false;
        return true;
    }

    /**
     * Добавляет элемент с уникальным ключом в мапу
     * @return значение из добавленной пары ключ-значение
     */
    @Override
    public V put(K key, V value) {
        Node<K, V> node = new Node(hashFunc(key), key, value);
        int index = node.hash;
        // если элемента с таким ключом ещё нет, то кладём его в бакет в конец списка, не забываем про size
        if ( isContained(key)==-1) {
            if (buckets[index] == null) {
                buckets[index] = node;
            } else {
                Node<K, V> curr = buckets[index];
                while (curr.hasNext())
                    curr = curr.next;
                curr.next = node;
            }
            size++;
            return  node.value;
        }
        // а если уже есть, то переходим на него и просто заменяем в нём value
        else {
            Node<K, V> curr = buckets[index];
            for (int i = 0; i < isContained(key); curr = curr.next, i++);
            curr.value = value;
            return curr.value;
        }
    }

    /**
     * Проверяет, есть ли в мапе элемент с заданным ключом
     * @param key - ключ
     * @return true, если в мапе содержится элемент с таким ключом
     */
    @Override
    public boolean containsKey(Object key) {
        return (isContained(key)!=-1);
    }

    /**
     * Проверяет, содержится ли значение в мапе
     * @param value - значение
     */
    @Override
    public boolean containsValue(Object value) {
        for(int i=0; i<buckets.length;i++){
            if(buckets[i]!=null){
                Node<K, V> curr = buckets[i];
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

    /**
     * Возвращает значение по ключу
     * @param key - ключ
     */
    @Override
    public V get(Object key) {
        if(isContained(key)==-1)
            return null;
        else{
            Node<K, V> curr = buckets[key.hashCode()%capacity];
            for (int i = 0; i < isContained(key); curr = curr.next, i++) {
            };
            return curr.value;
        }
    }

    /**
     * Удаляет из мапы элемент с заданным ключом
     * @param key - ключ, по которому удаляем элемент
     * @return значение из удалённой пары ключ-значение
     */
    @Override
    public V remove(Object key) {
        if(isContained(key)==-1)
            return null;
        else {
            size--;
            Node<K, V> curr = buckets[hashFunc(key)];
            if (isContained(key)==0) {
                buckets[hashFunc(key)] = curr.next;
                return curr.value;
            }
            else{
                for(int i=0;i<(isContained(key)-1);i++)
                    curr = curr.next;
                Node<K, V> res = curr.next;
                curr.next = res.next;
                return res.getValue();
            }


        }
    }

    @Override
    public void putAll(Map< ? extends K, ? extends V> m) {

    }

    /** Удаляет из мапы все элементы  */
    @Override
    public void clear() {
        Arrays.fill(buckets, null);
        size=0;

    }

    /**
     * Метод для доступа к ключам всех элементов мапы
     * @return множество всех ключей мапы
     */
    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        // проще достать ключи из entryset
        for(Entry<K,V> node : entrySet())
            keys.add(node.getKey());

        return keys;
    }

    @Override
    public Collection<V> values() {

        return null;
    }

    /**
     * Метод для доступа к элементам мапы
     * @return множество элементов мапы в виде пар ключ-значение
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K,V>> set = new HashSet<>();
        for(Node<K,V> bucket : buckets){
            // если в бакете вообще что-то есть
            if(bucket!=null){
                set.add(bucket);
                // если это всё = переходим к следующей ячейке(бакету)
                if(!bucket.hasNext())
                    continue;
                else{
                    Node<K, V> curr = bucket;
                    while(curr.hasNext()){
                        set.add(curr);
                        curr = curr.next;
                    }
                    set.add(curr);
                }
            }
        }

        return set;
    }

    /**
     * Хеш-функция, по которой на основании хеш-кода вычисляется индекс бакета,
     * в котором будет размещён элемент
     * @param obj - объект, у которого вычисляется хеш-код (ключ)
     * @return целое число - индекс бакета и поле {@link Node#hash} элемента
     */
    public int hashFunc(Object obj){
        return obj.hashCode() % capacity;
    }

    // служебный метод, проверяет, есть ли в мапе элемент с заданным ключом
    // возвращает -1, если нет
    // возвращает позицию в связанном списке, если есть
    private int isContained(Object key){
        int index = hashFunc(key);
        if ( buckets[index]==null)
            return -1;
        else {
            int position = 0;
            Node<K, V> curr = buckets[index];
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

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder(info());
        for(Entry<K, V> node : entrySet()){
            str.append(String.format("питомец: %s, населённый пункт: %s, номер бакета: %d\n",
                    node.getKey(), node.getValue().toString(), ((Node<K, V>) node).hash));
        }
        return str.toString();
    }

    // Основная информация
    private String info(){
        StringBuilder str = new StringBuilder();
        for(int i=0; i<buckets.length; i++){
            if(buckets[i]!=null)
                str.append("bucket #"+i+": "+buckets[i].key.toString()+"::hashcode "+buckets[i].key.hashCode()+"\n");
        }
        return ("MyHashMap\n" +
                "current capacity: "+capacity+"\n"+
                "current size: "+size+"\n"+
                "buckets: \n"+str+"\n");
    }

    /**
     * Внутренний статический класс-обёртка для объектов внутри бакетов (пар ключ+значение).
     * @autor Евгения Филипенко
     * @version 1.0
     */
     static class Node<K, V> implements Map.Entry<K, V>{     //implements Map.Entry<K, V>
        /** Поле, хранящее результат вычисления хеш-функции */
        private final int hash;
        /** Поле, хранящее ключ элемента */
        private final K key;
        /** Поле, хранящее значение элемента */
        private V value;
        /** Поле, хранящее ссылку на следующий элемент в бакете */
        private Node next;
        //final int hash(Object key);

        /**
         * Конструктор - создание нового объекта
         * @param hash - хеш-функция
         * @param key - ключ
         * @param value - значение
         */
        public Node(int hash, K key, V value){
            this.hash = hash;
            this.key = key;
            int code = key.hashCode();
            int code2 = key.hashCode()%16;
            this.value = value;
            next = null;
        }

        /**
         * Метод проверяет, есть ли следующий элемент в списке,
         * хранящемся в бакете
         * @return true, если в поле {@link Node#next} текущего элемента не null
         */
        public boolean hasNext(){
            return next!=null;
        }

        /**
         * Геттер для поля {@link Node#hash}
         * @return возвращает значение хеш-функции
         */
        public int getHash(){ return hash;}

        /**
         * Геттер для поля {@link Node#key}
         * @return возвращает значение ключа
         */
        public K getKey(){return key;}

        /**
         * Геттер для поля {@link Node#value}
         * @return возвращает значение, хранящееся в элементе
         */
        public V getValue(){
            return value;
        }

        /**
         * Сеттер для поля {@link Node#next}
         * Устанавливает значение поля  {@link Node#next}
         */
        public void setNext(Node next){
            this.next = next;
        }

        /**
         * Сеттер для поля {@link Node#value}
         * Устанавливает значение, хранящееся в элементе
         */
        //@Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        @Override
        public boolean equals(Object obj){
            if(this==obj) return true;
            if(!(obj instanceof Node)) return false;
            return ( (this.key.equals( ((Node) obj).key) ) && (this.value.equals( ((Node) obj).value )) );
        }


    }


}
