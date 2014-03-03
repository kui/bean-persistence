package jp.k_ui.beanpersistence.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import jp.k_ui.beanpersistence.BeanPersistenceException;
import jp.k_ui.beanpersistence.Storage;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class FileStorage<T> implements Storage<T> {

  private FileIO file;
  private Serializer<T> serializer;
  private HashFunction hashFunction;

  private byte[] hash = null;

  public FileStorage(File file, Serializer<T> serializer) {
    this(file, serializer, Hashing.murmur3_128());
  }

  public FileStorage(File file, Serializer<T> serializer, HashFunction hashFunction) {
    this(new FileIO(file), serializer, hashFunction);
  }

  public FileStorage(FileIO file, Serializer<T> serializer, HashFunction hashFunction) {
    this.file = file;
    this.serializer = serializer;
    this.hashFunction = hashFunction;
  }

  @Override
  public T get() throws BeanPersistenceException {
    if (file.notExists() || file.isEmpty())
      return null;

    try (InputStream is = file.getInputStream()) {
      return serializer.deserialize(is);
    } catch (IOException e) {
      throw new BeanPersistenceException(e);
    }
  }

  @Override
  public void put(T t) throws BeanPersistenceException {
    byte[] bytes = serializer.serialize(t);

    byte[] newHash = hashBytes(bytes);
    if (Arrays.equals(newHash, hash))
      return;

    hash = newHash;

    try (OutputStream out = file.getOutputStream()) {
      out.write(bytes);
    } catch (IOException e) {
      throw new BeanPersistenceException(e);
    }
  }

  private byte[] hashBytes(byte[] b) {
    HashCode code = hashFunction.hashBytes(b);
    return code.asBytes();
  }
}
