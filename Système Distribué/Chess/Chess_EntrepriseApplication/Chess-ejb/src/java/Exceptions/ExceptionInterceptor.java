/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


public class ExceptionInterceptor {
    public ExceptionInterceptor(){

    }

    @AroundInvoke
    public Object invokeMethod(InvocationContext context) throws Exception{
        try{
            return context.proceed();
        }catch(Exception ex){
            String message = GestionExceptions.traiterException(ex.getMessage());
            System.err.println("MESSAGE: " + ex.getMessage());
            System.err.println("CAUSED BY: " + context.getMethod().toString());
            throw new Exception(message);
        }
    }
}
