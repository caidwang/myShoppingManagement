package com.hustcaid.myshoppingmanagement.entity;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/4/29   
 *
 ******************************************************************************/

/**
 * 为Saleman信息表单配置验证器, 验证姓名,密码不能为空
 */
public class SaleManValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Saleman.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Saleman sm = (Saleman) o;
        String name = sm.getSname();
        String passwd = sm.getSpassword();
        if (name == null || "".equalsIgnoreCase(name)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sname", "error.sname.blank", "姓名不能为空");
        }
        if (passwd == null || "".equalsIgnoreCase(passwd)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "spassword", "error.password.blank", "密码不能为空");
        }
    }
}
