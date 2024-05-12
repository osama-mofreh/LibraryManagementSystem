package com.LMS.LibraryManagementSystem.configs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Define pointcut expressions for methods you want to intercept

    // Pointcut for all methods in BookService
    @Pointcut("execution(* com.LMS.LibraryManagementSystem.services.BookService.*(..))")
    public void bookServiceMethods() {}

    // Pointcut for all methods in PatronService
    @Pointcut("execution(* com.LMS.LibraryManagementSystem.services.PatronService.*(..))")
    public void patronServiceMethods() {}


    // Pointcut for all methods in BorrowingService
    @Pointcut("execution(* com.LMS.LibraryManagementSystem.services.BorrowingService.*(..))")
    public void borrowingServiceMethods() {}

    // Advice to log method entry
    @Before("bookServiceMethods() || patronServiceMethods() || borrowingServiceMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        // Get method signature
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String signature = className + "." + methodName;

        // Get method arguments
        Object[] args = joinPoint.getArgs();

        // Get method parameters
        Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
        Method method = Arrays.stream(methods)
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElse(null);

        StringBuilder params = new StringBuilder();
        if (method != null) {
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < args.length; i++) {
                params.append(paramTypes[i].getSimpleName()).append(" ").append(args[i]);
                if (i < args.length - 1) {
                    params.append(", ");
                }
            }
        }

        // Log method entry
        logger.info("Method entry: {}({}) | Timestamp: {} | Class: {} | Line: {}",
                methodName, params.toString(), new Date(), className, getLineNumber(joinPoint));
    }

    // Helper method to get the line number where the method is invoked
    private int getLineNumber(JoinPoint joinPoint) {
        try {
            return ((org.aspectj.lang.reflect.SourceLocation) joinPoint.getSourceLocation()).getLine();
        } catch (UnsupportedOperationException e) {
            // Source location information is not available
            // Alternatively, you can use stack trace to get the line number
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (element.getClassName().equals(joinPoint.getTarget().getClass().getName())
                        && element.getMethodName().equals(joinPoint.getSignature().getName())) {
                    return element.getLineNumber();
                }
            }
            // Fallback to -1 if line number cannot be determined
            return -1;
        }
    }

    // Advice to log exceptions
    @AfterThrowing(pointcut = "bookServiceMethods() || patronServiceMethods() || borrowingServiceMethods()", throwing = "ex")
    public void logException(Exception ex) {
        logger.error("Exception occurred: " + ex.getMessage());
    }
}

