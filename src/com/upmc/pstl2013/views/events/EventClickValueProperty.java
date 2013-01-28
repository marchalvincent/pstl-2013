package com.upmc.pstl2013.views.events;

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

public class EventClickValueProperty extends SelectionAdapter {

	private final int EDITABLECOLUMN = 1;
	private TableEditor editor;
	private Table tabValueProperties;

	public EventClickValueProperty(TableEditor editor, Table tabValueProperties) {

		this.editor = editor;
		this.tabValueProperties = tabValueProperties;
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
		Text newEditor = new Text(tabValueProperties, SWT.NONE);
		newEditor.setText(item.getText(EDITABLECOLUMN));
		newEditor.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				Text text = (Text) editor.getEditor();
				editor.getItem().setText(EDITABLECOLUMN, text.getText());
			}
		});
		newEditor.selectAll();
		newEditor.setFocus();
		editor.setEditor(newEditor, item, EDITABLECOLUMN);
	}
}
