package com.codeit.blog.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneDecimalPlaceValidator implements
    ConstraintValidator<OneDecimalPlace, Double> {

  @Override
  public boolean isValid(Double value, ConstraintValidatorContext context) {
    if ( value == null ) return true;
    String str = String.valueOf(value);
    int index = str.indexOf('.');
    if( index == -1 ) return true;  //정수 허용
    return str.length() - index - 1 <= 1; // 수소점 한자리 까지 허용
  }
}
