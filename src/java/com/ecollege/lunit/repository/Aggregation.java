package com.ecollege.lunit.repository;

/**
 * Created by IntelliJ IDEA.
 * User: chrisch
 * Date: Nov 16, 2009
 * Time: 11:26:08 AM
 */
public interface Aggregation {
    int getNumTesters();
    int getNumErrorEvents();
    int getNumFailureEvents();
    int getNumTestStopEvents();
}
