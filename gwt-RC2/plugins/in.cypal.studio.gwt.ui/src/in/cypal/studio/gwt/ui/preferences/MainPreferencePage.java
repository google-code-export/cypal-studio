/*
 * Copyright 2006 Cypal Solutions (tools@cypal.in)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package in.cypal.studio.gwt.ui.preferences;

import in.cypal.studio.gwt.core.common.Constants;
import in.cypal.studio.gwt.ui.Activator;
import in.cypal.studio.gwt.ui.common.Util;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Prakash G.R.
 *
 */
public class MainPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	static final IPropertyChangeListener changeListener = new IPropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent event) {
			if(event.getProperty().equals(Constants.GWT_HOME_PREFERENCE)){
				IPath newGwtHome = new Path((String)event.getNewValue());
				try {
					JavaCore.setClasspathVariable(Constants.GWT_HOME_CPE, newGwtHome, new NullProgressMonitor());
					ResourcesPlugin.getWorkspace().getPathVariableManager().setValue(Constants.GWT_HOME_PATH, newGwtHome);
				} catch (Exception e) {
					Activator.logException(e);
				}
			}
		}
	};
		
	
	public MainPreferencePage() {
		super(GRID);
		setDescription("Options for Cypal Studio for GWT");
		setPreferenceStore(Util.getPreferenceStore());
		
	}

	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor(Constants.GWT_HOME_PREFERENCE, "GWT Home:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Constants.UPDATE_ASYNC_PREFERENCE, "Manually manage Async files", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Constants.COMPILE_AT_FULLBUILD_PREFERENCE, "Invoke GWT Compiler on Clean &Build", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Constants.COMPILE_AT_PUBLISH_PREFERENCE, "Invoke GWT Compiler when publishing to an &external server", getFieldEditorParent())); 
	}

	public void init(IWorkbench workbench) {
		getPreferenceStore().addPropertyChangeListener(changeListener);
	}
	
	public void dispose() {
		super.dispose();
		getPreferenceStore().removePropertyChangeListener(changeListener);
		
	}
	
	
}
