package com.technohest.core.event;

/**
 * An event listener used to notify listener classes.
 * @author David Str�m
 */
public interface REventListener {
    /**
     * Notify observers of a change.
     */
    void fireEvent();
}
