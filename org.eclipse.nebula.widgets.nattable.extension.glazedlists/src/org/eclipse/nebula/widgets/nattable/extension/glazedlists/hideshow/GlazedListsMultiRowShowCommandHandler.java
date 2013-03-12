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
import org.eclipse.nebula.widgets.nattable.hideshow.command.MultiRowShowCommand;

/**
 * Command handler for handling {@link MultiRowShowCommand}s in a NatTable that uses GlazedLists.
 * 
 * @author Dirk Fauth
 *
 */
public class GlazedListsMultiRowShowCommandHandler<T> extends AbstractLayerCommandHandler<MultiRowShowCommand> {

	/**
	 * The {@link GlazedListsRowHideShowLayer} where this command handler should operate on.
	 */
	private final GlazedListsRowHideShowLayer<T> rowHideShowLayer;
	
	/**
	 * 
	 * @param rowHideShowLayer The {@link GlazedListsRowHideShowLayer} where this command handler should operate on.
	 */
	public GlazedListsMultiRowShowCommandHandler(GlazedListsRowHideShowLayer<T> rowHideShowLayer) {
		this.rowHideShowLayer = rowHideShowLayer;
	}
	
	@Override
	public Class<MultiRowShowCommand> getCommandClass() {
		return MultiRowShowCommand.class;
	}

	@Override
	protected boolean doCommand(MultiRowShowCommand command) {
		rowHideShowLayer.showRowIndexes(command.getRowIndexes());
		return true;
	}

}
