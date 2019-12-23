/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.strep.validator;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author María Novo
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object firstObj = getFieldValue(value, firstFieldName);
            final Object secondObj = getFieldValue(value, secondFieldName);
            System.out.println("validator: " + firstObj + " - " + secondObj);
            //valid = (firstObj==null && secondObj==null ) ||(firstObj.equals("") && secondObj.equals("")) || firstObj.equals(secondObj);

           valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore){}

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
    
    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field passwordField = clazz.getDeclaredField(fieldName);
        passwordField.setAccessible(true);
        return passwordField.get(object);
    }    
}