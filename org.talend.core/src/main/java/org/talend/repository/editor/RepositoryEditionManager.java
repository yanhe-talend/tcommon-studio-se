// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.editor;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.general.Project;
import org.talend.core.model.properties.Item;
import org.talend.repository.model.RepositoryConstants;
import org.talend.repository.model.ResourceModelUtils;

/**
 * DOC mhelleboid class global comment. Detailled comment <br/>
 * 
 * $Id: RepositoryEditionManager.java 295 2006-11-02 08:28:03 +0000 (星期四, 02 十一月 2006) smallet $
 * 
 */
public class RepositoryEditionManager {

    private static RepositoryEditionManager instance = new RepositoryEditionManager();

    private Map<IEditorPart, RepositoryEditorInput> editors = new HashMap<IEditorPart, RepositoryEditorInput>();;

    public static RepositoryEditionManager getInstance() {
        return instance;
    }

    public IPath getTemporaryFilePath() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        Long one = System.currentTimeMillis();
        Long two = new Random().nextLong();

        return getTempFolderPath(getRepositoryProject()).append(one.toString() + two);
    }

    /**
     * DOC mhelleboid Comment method "getRepositoryProject".
     * 
     * @return
     */
    private Project getRepositoryProject() {
        RepositoryContext repositoryContext = (RepositoryContext) CorePlugin.getContext().getProperty(
                Context.REPOSITORY_CONTEXT_KEY);
        return repositoryContext.getProject();
    }

    /**
     * DOC mhelleboid Comment method "getTempFolder".
     * 
     * @param project TODO
     * 
     * @return
     */
    private IPath getTempFolderPath(Project project) {
        IProject iProject;
        IFolder iFolder = null;
        try {
            iProject = ResourceModelUtils.getProject(project);
            iFolder = ResourceUtils.getFolder(iProject, RepositoryConstants.TEMP_DIRECTORY, true);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return iFolder.getFullPath();
    }

    public void openEditor(IWorkbenchPage page, IFile file, Item item, boolean forceReadOnly) {
        try {
            IEditorDescriptor editorDesc = IDE.getEditorDescriptor(file);
            RepositoryEditorInput repositoryEditorInput = new RepositoryEditorInput(file, item);
            repositoryEditorInput.setReadOnly(forceReadOnly);
            IEditorPart editorPart = IDE.openEditor(page, repositoryEditorInput, editorDesc.getId());
            editors.put(editorPart, repositoryEditorInput);
        } catch (PartInitException e) {
            e.printStackTrace();
        }
    }
}
