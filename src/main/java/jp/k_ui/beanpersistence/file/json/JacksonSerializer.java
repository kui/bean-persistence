package jp.k_ui.beanpersistence.file.json;

import java.io.IOException;
import java.io.InputStream;

import jp.k_ui.beanpersistence.BeanPersistenceException;
import jp.k_ui.beanpersistence.file.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JacksonSerializer<T> implements Serializer<T> {

  private ObjectMapper objectMapper = new ObjectMapper();
  private TypeFactory typeFactory = TypeFactory.defaultInstance();
  private JavaType javaType;

  public JacksonSerializer(Class<T> clazz) {
    this();
    javaType = typeFactory.constructType(clazz);
  }

  public JacksonSerializer(TypeReference<T> typeReference) {
    this();
    javaType = typeFactory.constructType(typeReference);
  }

  private JacksonSerializer() {
    objectMapper.setTypeFactory(typeFactory);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  @Override
  public T deserialize(InputStream in) throws BeanPersistenceException {
    try {
      return objectMapper.readValue(in, javaType);
    } catch (IOException e) {
      throw new BeanPersistenceException(e);
    }
  }

  @Override
  public byte[] serialize(T t) throws BeanPersistenceException {
    try {
      return objectMapper.writeValueAsBytes(t);
    } catch (JsonProcessingException e) {
      throw new BeanPersistenceException(e);
    }
  }
}
