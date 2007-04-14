/*
 * Copyright 2006  Prakash (techieguy@gmail.com)
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
package in.cypal.studio.gwt.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.wizards.NewElementWizard;
import org.eclipse.jface.resource.ImageDescriptor;


public class NewGwtModuleWizard extends NewElementWizard {

	private NewGwtModuleWizardPage wizardPage;
	
	public NewGwtModuleWizard() {
		setDefaultPageImageDescriptor(ImageDescriptor.createFromFile(this.getClass(), "/icons/new_module.gif")); //$NON-NLS-1$
		setDialogSettings(JavaPlugin.getDefault().getDialogSettings());
		setWindowTitle(""); //$NON-NLS-1$
	}


	public void addPages() {

		wizardPage = new NewGwtModuleWizardPage();
		wizardPage.init(getSelection());
		addPage(wizardPage);
	}

	protected boolean canRunForked() {
		return !wizardPage.isEnclosingTypeSelected();
	}


	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {
		wizardPage.createType(monitor);
	}

	public boolean performFinish() {
		warnAboutTypeCommentDeprecation();
		boolean res = super.performFinish();
		if (res) {
			IResource resource = wizardPage.getModifiedResource();
			if (resource != null) {
				selectAndReveal(resource);
				openResource((IFile) resource);
			}
		}
		return res;
	}

	
	public IJavaElement getCreatedElement() {
		return wizardPage.getCreatedType();
	}

}
