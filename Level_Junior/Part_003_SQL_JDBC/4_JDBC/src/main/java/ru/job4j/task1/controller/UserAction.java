package ru.job4j.task1.controller;

/**
 * Interface UserAction.
 *
 * @author Alex Proshak (olexandr_proshak@ukr.net)
 */
public interface UserAction {
    /**
     * Number of an action.
     * @return a number of an action.
     */
    int key();

    /**
     * An unique method for trackerArrayImpl.
     * @param input is an ask interface.
     * @param tracker is an instance of a class TrackerDbImpl.
     */
    void execute(Input input, Tracker tracker);

    /**
     * An information about the current method does.
     * @return an information.
     */
    String info();
}
