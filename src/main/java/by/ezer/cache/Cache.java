package by.ezer.cache;

public interface Cache {

    public Object get(Long key);

    public void put(Long key, Object object);

    public void delete(Long key);

    public boolean containsKey(Long key);
}
