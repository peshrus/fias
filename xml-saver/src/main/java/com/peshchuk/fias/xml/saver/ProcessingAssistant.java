package com.peshchuk.fias.xml.saver;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
public interface ProcessingAssistant {
	void createConstraints() throws Exception;

	void finish();
}
