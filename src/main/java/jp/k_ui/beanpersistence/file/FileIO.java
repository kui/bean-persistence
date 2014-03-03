package jp.k_ui.beanpersistence.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileIO {

  private File file;

  public FileIO(File file) {
    this.file = file;
  }

  public InputStream getInputStream() throws FileNotFoundException {
    return new FileInputStream(file);
  }

  public OutputStream getOutputStream() throws FileNotFoundException {
    return new FileOutputStream(file);
  }

  public boolean notExists() {
    return !file.exists();
  }

  public boolean isEmpty() {
    return file.length() == 0;
  }

}
