package jp.k_ui.beanpersistence.file.json;

import java.io.File;

import jp.k_ui.beanpersistence.BeanPersistenceException;
import jp.k_ui.beanpersistence.file.FileStorage;

import com.fasterxml.jackson.core.type.TypeReference;

public class JsonFileStorage<T> extends FileStorage<T> {

  public JsonFileStorage(File jsonFile, Class<T> clazz)
      throws BeanPersistenceException {
    super(jsonFile, new JacksonSerializer<>(clazz));
  }

  public JsonFileStorage(File jsonFile, TypeReference<T> typeRef)
      throws BeanPersistenceException {
    super(jsonFile, new JacksonSerializer<>(typeRef));
  }
}
