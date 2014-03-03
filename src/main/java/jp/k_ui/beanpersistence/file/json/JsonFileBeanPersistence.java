package jp.k_ui.beanpersistence.file.json;

import java.io.File;

import jp.k_ui.beanpersistence.BeanPersistence;
import jp.k_ui.beanpersistence.BeanPersistenceException;

import com.fasterxml.jackson.core.type.TypeReference;

public class JsonFileBeanPersistence<T> extends BeanPersistence<T> {

  public JsonFileBeanPersistence(File jsonFile, Class<T> clazz)
      throws BeanPersistenceException {
    super(new JsonFileStorage<>(jsonFile, clazz));
  }

  public JsonFileBeanPersistence(File jsonFile, TypeReference<T> typeRef)
      throws BeanPersistenceException {
    super(new JsonFileStorage<>(jsonFile, typeRef));
  }
}
