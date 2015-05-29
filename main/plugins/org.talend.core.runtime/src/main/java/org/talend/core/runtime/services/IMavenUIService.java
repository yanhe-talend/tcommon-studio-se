// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.runtime.services;

import java.io.InputStream;

import org.eclipse.jface.preference.IPreferenceNode;
import org.talend.core.IService;

/**
 * DOC ggu class global comment. Detailled comment
 */
public interface IMavenUIService extends IService {

    /**
     * Try to add custom maven scripts node in project setting tree.
     */
    void addCustomMavenSettingChildren(IPreferenceNode parent);

    /**
     * won't provide the bundle name, because the key is unique, so will try to find value for each bundle setting.
     */
    public String getProjectSettingValue(String key);

    public InputStream getProjectSettingStream(String key);
}
