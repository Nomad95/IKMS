package pl.politechnika.ikms.commons.abstracts;

import java.io.Serializable;

/**
 * Class implementing Serializable and Simple entity - Extend all entities for proper
 * Service working
 */
public abstract class AbstractEntity implements Serializable, SimpleEntity {
    private static final long serialVersionUID = 1L;
}
