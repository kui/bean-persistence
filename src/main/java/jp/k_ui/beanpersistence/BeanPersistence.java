package jp.k_ui.beanpersistence;

public class BeanPersistence<T> {

  private Storage<T> storage;
  private T bean;

  public BeanPersistence(Storage<T> storage) throws BeanPersistenceException {
    this.storage = storage;
    bean = storage.get();
  }

  public T get() {
    return bean;
  }

  public synchronized void set(T t) throws BeanPersistenceException {
    bean = t;
    storage.put(t);
  }

  public void save() throws BeanPersistenceException {
    set(get());
  }
}
