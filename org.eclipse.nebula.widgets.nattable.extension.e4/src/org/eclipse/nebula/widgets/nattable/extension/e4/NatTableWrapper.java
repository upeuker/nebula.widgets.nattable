package org.eclipse.nebula.widgets.nattable.extension.e4;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.w3c.dom.Node;

public class NatTableWrapper extends NatTableElementAdapter {

    public final NatTableElementAdapter parent;
    public final String styleLabel;

    public NatTableWrapper(NatTable natTable, String styleLabel, CSSEngine engine, NatTableElementAdapter parent) {
        super(natTable, engine);

        this.styleLabel = styleLabel;
        this.parent = parent;
    }

    public String getStyleLabel() {
        return this.styleLabel;
    }

    @Override
    public Node getParentNode() {
        return this.parent;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public Node item(int index) {
        return null;
    }
}
