package com.technohest.core.event;

/**
 * An event listener used to notify listener classes.
 * @author David Ström
 */
public interface REventListener {
    /**
     * Notify observers of a change.
     */
    void fireEvent();
}
