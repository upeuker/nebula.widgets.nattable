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

import java.util.List;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler2;
import org.eclipse.e4.ui.css.core.dom.properties.converters.ICSSValueConverter;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.CellStyleProxy;
import org.eclipse.nebula.widgets.nattable.style.ConfigAttribute;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class NatTableCSSHandler extends AbstractCSSPropertySWTHandler implements ICSSPropertyHandler2 {

    /**
     * CSS property for the NatTable background color. This has effect on the
     * area that does not show cells.
     */
    public static final String BACKGROUND_COLOR = "background-color";

    /**
     * CSS property for {@link CellStyleAttributes#BACKGROUND_COLOR}.
     */
    public static final String CELL_BACKGROUND_COLOR = "cell-background-color";

    /**
     * CSS property for {@link CellStyleAttributes#FOREGROUND_COLOR}.
     */
    public static final String FOREGROUND_COLOR = "color";

    // TODO instead of additional properties, distinguish via value
    // http://www.vogella.com/tutorials/Eclipse4CSS/article.html#css_colors

    /**
     * CSS property for {@link CellStyleAttributes#GRADIENT_BACKGROUND_COLOR}.
     * Triggers usage of the GradientBackgroundPainter.
     */
    public static final String GRADIENT_BACKGROUND_COLOR = "gradient-background-color";

    /**
     * CSS property for {@link CellStyleAttributes#GRADIENT_FOREGROUND_COLOR}.
     * Triggers usage of the GradientBackgroundPainter.
     */
    public static final String GRADIENT__FOREGROUND_COLOR = "gradient-foreground-color";

    /**
     * CSS property for {@link CellStyleAttributes#HORIZONTAL_ALIGNMENT}.
     */
    public static final String HORIZONTAL_ALIGNMENT = "horizontal-align";

    /**
     * CSS property for {@link CellStyleAttributes#VERTICAL_ALIGNMENT}.
     */
    public static final String VERTICAL_ALIGNMENT = "vertical-align";

    /**
     * CSS property for {@link CellStyleAttributes#FONT}.
     */
    public static final String FONT = "font";

    /**
     * CSS property for {@link CellStyleAttributes#IMAGE}. Triggers the usage of
     * the ImagePainter.
     */
    public static final String IMAGE = "image";

    /**
     * CSS property for {@link CellStyleAttributes#BORDER_STYLE}. Triggers the
     * usage of the LineBorderDecorator.
     */
    public static final String BORDER = "border";

    /**
     * CSS property for {@link CellStyleAttributes#PASSWORD_ECHO_CHAR}. Triggers
     * the usage of the PasswordTextPainter.
     */
    public static final String PASSWORD_ECHO_CHAR = "password-echo-char";

    /**
     * CSS property for {@link CellStyleAttributes#TEXT_DECORATION}.
     * <p>
     * Available values: none, underline, line-through
     * </p>
     * <p>
     * Combinations are possible via space separated list.
     * </p>
     */
    public static final String TEXT_DECORATION = "text-decoration";

    // TODO add convenience for font attributes
    // font-family, font-size, font-style, font-weight

    // TODO add support for decorations
    // image, side, padding

    // TODO add support for border side definition via CustomLineBorderDecorator
    // possibly extend CustomLineBorderDecorator to specify side at creation
    // time (border-left, border-right ...)

    // TODO add convenience for border attributes
    // border-color, border-width, border-style

    // TODO word wrapping

    // TODO width/height calculation

    // TODO padding

    // TODO text direction

    // TODO background image (on NatTable or cell)
    // trigger BackgroundImagePainter (add separator color)
    // public static final String BACKGROUND_IMAGE = "background-image";

    @Override
    protected void applyCSSProperty(Control control, String property, CSSValue value, String pseudo, CSSEngine engine)
            throws Exception {

        if (control instanceof NatTable) {
            NatTable natTable = (NatTable) control;

            // check property
            if (BACKGROUND_COLOR.equalsIgnoreCase(property)
                    && (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
                Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
                // apply style
                natTable.setBackground(newColor);
            } else if (CELL_BACKGROUND_COLOR.equalsIgnoreCase(property)
                    && (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
                Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
                // apply style
                applyNatTableStyle(
                        natTable,
                        CellStyleAttributes.BACKGROUND_COLOR,
                        newColor,
                        getDisplayMode(pseudo));
            }

        }
    }

    @Override
    protected String retrieveCSSProperty(Control control, String property, String pseudo, CSSEngine engine)
            throws Exception {

        if (control instanceof NatTable) {
            NatTable natTable = (NatTable) control;

            // check property
            if (BACKGROUND_COLOR.equalsIgnoreCase(property)) {
                ICSSValueConverter cssValueConverter = engine.getCSSValueConverter(String.class);
                return cssValueConverter.convert(
                        natTable.getBackground(), engine, null);
            } else if (CELL_BACKGROUND_COLOR.equalsIgnoreCase(property)) {
                ICSSValueConverter cssValueConverter = engine.getCSSValueConverter(String.class);
                return cssValueConverter.convert(
                        getNatTableStyle(
                                natTable,
                                CellStyleAttributes.BACKGROUND_COLOR,
                                getDisplayMode(pseudo)),
                        engine,
                        null);
            }

        }
        return null;
    }

    protected <T> void applyNatTableStyle(
            NatTable natTable,
            ConfigAttribute<T> styleConfig,
            T value,
            String displayMode) {

        IConfigRegistry configRegistry = natTable.getConfigRegistry();

        // TODO retrieve labels from selector (pseudo?)
        String[] configLabels = new String[0];

        // retrieve the style object for the given selector
        IStyle style = configRegistry.getConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                displayMode,
                configLabels);

        if (style == null) {
            style = new Style();
            if (configLabels != null && configLabels.length > 0) {
                configRegistry.registerConfigAttribute(
                        CellConfigAttributes.CELL_STYLE,
                        style,
                        displayMode,
                        configLabels[0]);
            } else {
                configRegistry.registerConfigAttribute(
                        CellConfigAttributes.CELL_STYLE,
                        style,
                        displayMode);
            }
        }

        // set the value to the style object
        style.setAttributeValue(
                styleConfig,
                value);
    }

    protected <T> T getNatTableStyle(NatTable natTable, ConfigAttribute<T> styleConfig, String displayMode) {
        IConfigRegistry configRegistry = natTable.getConfigRegistry();

        // TODO retrieve labels from selector (pseudo?)
        List<String> configLabels = null;

        IStyle style = new CellStyleProxy(configRegistry, displayMode, configLabels);

        return style.getAttributeValue(styleConfig);
    }

    protected String getDisplayMode(String pseudo) {
        if (pseudo != null) {
            if ("select".equals(pseudo)) {
                return DisplayMode.SELECT;
            } else if ("edit".equals(pseudo)) {
                return DisplayMode.EDIT;
            } else if ("hover".equals(pseudo)) {
                return DisplayMode.HOVER;
            } else if ("select-hover".equals(pseudo)) {
                return DisplayMode.SELECT_HOVER;
            }
        }
        return DisplayMode.NORMAL;
    }

    @Override
    public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine) throws Exception {
        // TODO build and apply painters

    }
}
