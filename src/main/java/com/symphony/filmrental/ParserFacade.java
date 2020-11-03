package com.symphony.filmrental;

import java.io.*;
import java.util.Optional;

//FIXME comment doesn`t explain class purpose
/**
 * This class is thread safe.
 */
//FIXME all file operations should have close blocks, or wrapped in try-with-resources
public class ParserFacade {

  private static ParserFacade instance;

  public static ParserFacade getInstance() {
    if (instance == null) {
      //FIXME add synhronized(ParserFacade.class) here
      instance = new ParserFacade();
    }
    return instance;
  }

  private File file;

  public synchronized void setFile(File f) {
    file = f;
  }

  //FIXME invalid usage of Optional - should use Optional.ofNullable
  public synchronized Optional<File> getFile() {
    if (file != null) {
      return Optional.of(file);
    } else {
      return null;
    }
  }

  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    //FIXME use proper wrapping {...} & StringBuilder/Buffer
    while ((data = i.read()) > 0) output += (char) data;
    return output;
  }

  //FIXME use InputReaded with Charset set up
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) if (data < 0x80) {
      output += (char) data;
    }
    return output;
  }

  //FIXME use BufferedWriter/PrintWriter instead of cycle
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
