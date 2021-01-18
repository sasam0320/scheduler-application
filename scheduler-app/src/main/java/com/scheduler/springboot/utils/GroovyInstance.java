package com.scheduler.springboot.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyInstance {

    private static GroovyShell groovyShell;

    private GroovyInstance(){}

    public static GroovyShell getShell(){

        Binding binding = new Binding();
        groovyShell = new GroovyShell(binding);

        return groovyShell;
    }
}
