/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.nebula.widgets.nattable.extension.e4;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings("restriction")
public class NatTableElementAdapter extends WidgetElement {

    List<String> labels = new ArrayList<String>();

    {
        this.labels.add("COLUMN_HEADER");
    }

    public NatTableElementAdapter(NatTable natTable, CSSEngine engine) {
        super(natTable, engine);

        addStaticPseudoInstance("normal");
        addStaticPseudoInstance("select");
        addStaticPseudoInstance("edit");
        addStaticPseudoInstance("hover");
        addStaticPseudoInstance("select-hover");
    }

    // @Override
    // public Node getParentNode() {
    // return this;
    // }

    @Override
    public Node getParentNode() {
        Control control = getControl();
        Composite parent = control.getParent();
        if (parent != null) {
            Element element = getElement(parent);
            return element;
        }
        return null;
    }

    protected Control getControl() {
        return (Control) getNativeWidget();
    }

    @Override
    public NodeList getChildNodes() {
        // only need to return a non-null value
        // strange implementation
        return this;
    }

    @Override
    public int getLength() {
        return this.labels.size();
    }

    @Override
    public Node item(int index) {
        return new NatTableWrapper((NatTable) getNativeWidget(), this.labels.get(index), this.engine, this);
    }
}
