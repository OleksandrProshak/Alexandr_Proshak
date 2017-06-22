package ru.job4j.chess.chessEcxeptions;

/**
 * Class OccupiedWayException.
 *
 * @author Alex Proshak (olexandr_proshak@ukr.net)
 * @version $Id$
 * @since 0.1
 */
public class OccupiedWayException extends Exception {

    /**
     * An exception trows if a current way is occupied by a figure.
     * @param message is a message is throw if a current way is occupied by a figure.
     */
    public OccupiedWayException(String message) {
        super("The current way is occupied by figure");
    }
}