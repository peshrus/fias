package com.peshchuk.fias.xml.saver;

import java.util.Collection;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public interface BatchProcessor {
	void save(Collection<?> batch) throws Exception;
	void delete(Collection<?> batch) throws Exception;
}