package com.datareport.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.util.CollectionUtils;

import com.datareport.err.ParamException;

public class VailUtils {
	
	
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	
	public static <T> void validate( T o,Class<?> clss) {
		
		Set<ConstraintViolation<T>> validate = factory.getValidator().validate(o,clss);
		StringBuffer msg = new StringBuffer();
		if (!CollectionUtils.isEmpty (validate)) {
			validate.forEach (tConstraintViolation -> {
				msg.append(tConstraintViolation.getMessage()).append(",");
			});
			throw new ParamException(msg.toString());
		}
	}
	
}
