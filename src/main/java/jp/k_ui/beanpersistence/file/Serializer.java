package jp.k_ui.beanpersistence.file;

import java.io.InputStream;

import jp.k_ui.beanpersistence.BeanPersistenceException;

public interface Serializer<T> {

  byte[] serialize(T t) throws BeanPersistenceException;

  T deserialize(InputStream in) throws BeanPersistenceException;

}
