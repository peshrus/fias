package com.peshchuk.fias.xml.saver;

import java.util.Collection;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public interface BatchSaver {
	void save(Collection<?> batch);
}