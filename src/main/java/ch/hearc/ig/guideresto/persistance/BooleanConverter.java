package ch.hearc.ig.guideresto.persistance;

import javax.persistence.AttributeConverter;

public class BooleanConverter implements AttributeConverter<Boolean, String> {

  @Override
  public String convertToDatabaseColumn(Boolean b) {
    return Boolean.TRUE.equals(b) ? "O" : "N";
  }

  @Override
  public Boolean convertToEntityAttribute(String s) {
    return "O".equals(s);
  }
}
