package pl.politechnika.ikms.commons.abstracts;

import java.io.Serializable;

/**
 * Extend all dto classes with this - enables serialization of objects
 */
public abstract class AbstractDto implements Serializable {
    private static final long serialVersionUID = 1L;
}
