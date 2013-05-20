package com.upmc.pstl2013.views.events;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.SwtView;

public class EventClickValueAttributes extends SelectionAdapter {

	private final int EDITABLECOLUMN = 1;
	private TableEditor editor;
	private Table table;
	private Logger log = Logger.getLogger(EventClickValueAttributes.class);
	private boolean isPropertyUse;

	/**
	 * Évènement permettant de cocher les attributs d'une property.
	 * @param {@link SwtView}
	 */
	public EventClickValueAttributes(Table table, TableEditor editor, boolean isPropertyUse) {

		this.editor = editor;
		this.table = table;
		this.isPropertyUse = isPropertyUse;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		// Clean up any previous editor control
		Control oldEditor = editor.getEditor();
		if (oldEditor != null) oldEditor.dispose();
		// Identify the selected row
		TableItem item = (TableItem) e.item;
		if (item == null) return;
		// The control that will be the editor must be a child of the
		// Table
		Text newEditor = new Text(table, SWT.NONE);
		newEditor.setText(item.getText(EDITABLECOLUMN));
		newEditor.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				Text text = (Text) editor.getEditor();
				editor.getItem().setText(EDITABLECOLUMN, text.getText());
				if (isPropertyUse){
					try {
						IProperties propertie = PropertiesFactory.getInstance().getProperty((String)(editor.getItem().getData()));
						propertie.put(editor.getItem().getText(),  text.getText());

					} catch (PropertiesException e1) {
						log.error(e1.getMessage());
					}
				}

			}
		});
		newEditor.selectAll();
		newEditor.setFocus();
		editor.setEditor(newEditor, item, EDITABLECOLUMN);
	}
}
