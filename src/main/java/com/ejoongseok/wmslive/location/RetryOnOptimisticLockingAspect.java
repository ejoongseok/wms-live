package com.ejoongseok.wmslive.location;

import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.StaleObjectStateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

/**
 * 재고 변경같은 정합성 유지가 필요한 작업에서 OptimisticLockException이 발생하면 재시도하는 Aspect
 * spring retry를 사용해도 됨. https://www.baeldung.com/spring-retry
 */
@Slf4j
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component
public class RetryOnOptimisticLockingAspect {

    @Around("@annotation(RetryOnOptimisticLockingFailure)")
    public Object doConcurrentOperation(final ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        final RetryOnOptimisticLockingFailure annotation = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(RetryOnOptimisticLockingFailure.class);
        final int maxRetries = annotation.maxRetries();
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            } catch (final OptimisticLockException | StaleObjectStateException |
                           ObjectOptimisticLockingFailureException oe) {
                log.error("RetryOnOptimisticLockingFailure: retrying operation {} due to optimistic locking error, attempt {}", pjp.getSignature().getName(), numAttempts);
                if (numAttempts > maxRetries) {
                    log.error("RetryOnOptimisticLockingFailure: max retries exceeded");
                    throw oe;
                }
                Thread.sleep(100); // 재시도 전 지연
            }
        } while (numAttempts <= maxRetries);

        // 이 부분은 실행되지 않는다.
        return null;
    }
}
