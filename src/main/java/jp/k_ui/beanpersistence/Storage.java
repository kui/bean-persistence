package jp.k_ui.beanpersistence;

public interface Storage<T> {

  T get() throws BeanPersistenceException;

  void put(T t) throws BeanPersistenceException;
}
