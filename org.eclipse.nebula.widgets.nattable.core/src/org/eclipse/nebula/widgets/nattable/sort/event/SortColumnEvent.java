/*******************************************************************************
 * Copyright (c) 2012 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.sort.event;

import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.ColumnVisualChangeEvent;

public class SortColumnEvent extends ColumnVisualChangeEvent {

	public SortColumnEvent(ILayer layer, int columnPosition) {
		super(layer, new Range(columnPosition));
	}
	
	protected SortColumnEvent(SortColumnEvent event) {
		super(event);
	}
	
	@Override
	public SortColumnEvent cloneEvent() {
		return new SortColumnEvent(this);
	}

}
