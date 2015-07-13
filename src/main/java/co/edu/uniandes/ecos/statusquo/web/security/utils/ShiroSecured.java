/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.security.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author Dev
 */
@Inherited
@InterceptorBinding
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ShiroSecured {
    
}
