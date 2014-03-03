package jp.k_ui.beanpersistence.file.json;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.k_ui.beanpersistence.BeanPersistence;
import lombok.Data;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.Files;

public class JsonFileStorageTest {

  @Test
  public void print() throws Exception {
    File file = File.createTempFile("beanp", ".json");
    BeanPersistence<Person> bp = new JsonFileBeanPersistence<>(file, Person.class);
    Files.copy(file, System.out);

    bp.set(new Person());
    Files.copy(file, System.out);

    String name = "foo smith";
    Date birthDay = new Date(System.currentTimeMillis() - 12 * 365 * 24 * 60 * 60 * 1000);
    bp.get().setName(name);
    bp.get().setBirthDay(birthDay);
    bp.save();
    Files.copy(file, System.out);

    BeanPersistence<Person> bp2 = new JsonFileBeanPersistence<>(file, Person.class);
    assertEquals(bp.get(), bp2.get());

    //

    file = File.createTempFile("beanp", ".json");
    TypeReference<List<Person>> typeRef = new TypeReference<List<Person>>() {
    };
    BeanPersistence<List<Person>> bp3 = new JsonFileBeanPersistence<>(file, typeRef);
    Files.copy(file, System.out);

    Person p = new Person();
    p.setName("bar smith");
    p.setBirthDay(new Date(System.currentTimeMillis() - 15 * 365 * 24 * 60 * 60 * 1000));
    bp3.set(Collections.singletonList(p));
    Files.copy(file, System.out);

    BeanPersistence<List<Person>> bp4 = new JsonFileBeanPersistence<>(file, typeRef);
    assertEquals(bp3.get(), bp4.get());
  }

  @Data
  static class Person {
    String name;
    Date birthDay;
  }
}
