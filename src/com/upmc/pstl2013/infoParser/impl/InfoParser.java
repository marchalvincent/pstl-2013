package com.upmc.pstl2013.infoParser.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.umlParser.IUMLParser;

/**
 * Cette classe permet de passer des paramètres au {@link IUMLParser}.
 *
 */
public class InfoParser implements IInfoParser {

	private List<IFile> files;

	public InfoParser() {
		super();
		files = new ArrayList<IFile>();
	}

	@Override
	public List<IFile> getSelectedUMLFiles() {
		return files;
	}

	@Override
	public void addFile(IFile file) {
		files.add(file);
	}

	@Override
	public void reset() {
		// files.clear();
	}
}
