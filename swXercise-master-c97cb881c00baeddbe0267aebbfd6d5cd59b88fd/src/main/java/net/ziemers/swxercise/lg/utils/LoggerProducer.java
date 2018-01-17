package net.ziemers.swxercise.lg.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Dependendy Injection mit dem SLF4J-Logger
 * Quelle: http://www.pavel.cool/jee-tips/Injecting-Logger/
 */
@Named
@Singleton
public class LoggerProducer {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getBean().getBeanClass());
    }

}