package jp.k_ui.beanpersistence.file;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.io.OutputStream;
import java.util.Date;

import lombok.Data;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

import com.google.common.hash.Hashing;

public class FileStorageTest {

  @Test
  public void testPutWithSameArgs() throws Exception {

    IMocksControl mockControl = EasyMock.createControl();
    FileIO file = mockControl.createMock(FileIO.class);
    @SuppressWarnings("unchecked")
    Serializer<Person> serializer = mockControl.createMock(Serializer.class);
    Person person = mockControl.createMock(Person.class);

    FileStorage<Person> storage = new FileStorage<>(file, serializer, Hashing.murmur3_128());

    byte[] serializedBytes = new byte[] { 2 };
    expect(serializer.serialize(person))
        .andReturn(serializedBytes)
        .times(2);

    OutputStream out = mockControl.createMock(OutputStream.class);
    expect(file.getOutputStream())
        .andReturn(out)
        .times(1);

    out.write(serializedBytes);
    expectLastCall().times(1);

    out.close();
    expectLastCall().times(1);

    mockControl.replay();

    storage.put(person);
    storage.put(person);

    mockControl.verify();
  }

  @Test
  public void testPutWithDifferentArgs() throws Exception {

    IMocksControl mockControl = EasyMock.createControl();
    FileIO file = mockControl.createMock(FileIO.class);
    @SuppressWarnings("unchecked")
    Serializer<Person> serializer = mockControl.createMock(Serializer.class);
    Person person1 = mockControl.createMock(Person.class);
    Person person2 = mockControl.createMock(Person.class);

    FileStorage<Person> storage = new FileStorage<>(file, serializer, Hashing.murmur3_128());

    byte[] serializedBytes1 = new byte[] { 2 };
    expect(serializer.serialize(person1))
        .andReturn(serializedBytes1)
        .times(1);

    byte[] serializedBytes2 = new byte[] { 3 };
    expect(serializer.serialize(person2))
        .andReturn(serializedBytes2)
        .times(1);

    OutputStream out = mockControl.createMock(OutputStream.class);
    expect(file.getOutputStream())
        .andReturn(out)
        .times(2);

    out.write(serializedBytes1);
    expectLastCall().times(1);

    out.write(serializedBytes2);
    expectLastCall().times(1);

    out.close();
    expectLastCall().times(2);

    mockControl.replay();

    storage.put(person1);
    storage.put(person2);

    mockControl.verify();
  }

  @Data
  public static class Person {
    private PersonName name;
    private Date birthDay;
  }

  @Data
  public static class PersonName {
    private String first;
    private String middle;
    private String last;
  }
}

