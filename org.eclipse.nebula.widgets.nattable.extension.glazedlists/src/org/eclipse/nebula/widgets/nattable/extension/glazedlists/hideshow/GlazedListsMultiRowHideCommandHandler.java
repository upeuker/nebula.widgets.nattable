/*******************************************************************************
 * Copyright (c) 2013 Dirk Fauth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dirk Fauth <dirk.fauth@gmail.com> - initial API and implementation
 *******************************************************************************/ 
package org.eclipse.nebula.widgets.nattable.extension.glazedlists.hideshow;

import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.hideshow.command.MultiRowHideCommand;

/**
 * Command handler for handling {@link MultiRowHideCommand}s in a NatTable that uses GlazedLists.
 * 
 * @author Dirk Fauth
 *
 */
public class GlazedListsMultiRowHideCommandHandler<T> extends AbstractLayerCommandHandler<MultiRowHideCommand>{

	/**
	 * The {@link GlazedListsRowHideShowLayer} where this command handler should operate on.
	 */
	private final GlazedListsRowHideShowLayer<T> rowHideShowLayer;

	/**
	 * 
	 * @param rowHideShowLayer The {@link GlazedListsRowHideShowLayer} where this command handler should operate on.
	 */
	public GlazedListsMultiRowHideCommandHandler(GlazedListsRowHideShowLayer<T> rowHideShowLayer) {
		this.rowHideShowLayer = rowHideShowLayer;
	}
	
	@Override
	public Class<MultiRowHideCommand> getCommandClass() {
		return MultiRowHideCommand.class;
	}

	@Override
	protected boolean doCommand(MultiRowHideCommand command) {
		rowHideShowLayer.hideRowPositions(command.getRowPositions());
		return true;
	}
	
}
