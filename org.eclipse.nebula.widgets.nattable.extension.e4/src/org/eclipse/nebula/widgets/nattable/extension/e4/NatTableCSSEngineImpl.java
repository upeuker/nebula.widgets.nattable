package org.eclipse.nebula.widgets.nattable.extension.e4;

import org.eclipse.e4.ui.css.core.dom.ChildVisibilityAwareElement;
import org.eclipse.e4.ui.css.core.dom.ExtendedCSSRule;
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;

//TODO I think this can be removed after the child stuff works

@SuppressWarnings("restriction")
public class NatTableCSSEngineImpl extends CSSSWTEngineImpl {

    public NatTableCSSEngineImpl(Display display) {
        super(display);
    }

    public NatTableCSSEngineImpl(Display display, boolean lazyApplyingStyles) {
        super(display, lazyApplyingStyles);
    }

    @Override
    public void applyStyles(Object element, boolean applyStylesToChildNodes,
            boolean computeDefaultStyle) {
        Element elt = getElement(element);
        if (elt != null) {
            if (!isVisible(elt)) {
                return;
            }

            /*
             * Compute new Style to apply.
             */
            // TODO custom viewCSS
            CSSStyleDeclaration style = null;// viewCSS.getComputedStyle(elt,
                                             // null);
            if (computeDefaultStyle) {
                if (applyStylesToChildNodes) {
                    this.computeDefaultStyle = computeDefaultStyle;
                }
                /*
                 * Apply default style.
                 */
                applyDefaultStyleDeclaration(element, false, style, null);
            }

            /*
             * Manage static pseudo instances
             */
            String[] pseudoInstances = getStaticPseudoInstances(elt);
            if (pseudoInstances != null) {
                // there are static pseudo instances definied, loop for it and
                // apply styles for each pseudo instance.
                for (String pseudoInstance : pseudoInstances) {
                    // TODO custom viewCSS
                    CSSStyleDeclaration styleWithPseudoInstance = null;// viewCSS.getComputedStyle(elt,
                                                                       // pseudoInstance);
                    if (computeDefaultStyle) {
                        /*
                         * Apply default style for the current pseudo instance.
                         */
                        applyDefaultStyleDeclaration(element, false,
                                styleWithPseudoInstance, pseudoInstance);
                    }

                    if (styleWithPseudoInstance != null) {
                        CSSRule parentRule = styleWithPseudoInstance.getParentRule();
                        if (parentRule instanceof ExtendedCSSRule) {
                            // TODO inspect and handle pseudo
                            // applyConditionalPseudoStyle((ExtendedCSSRule)
                            // parentRule, pseudoInstance, element,
                            // styleWithPseudoInstance);
                        } else {
                            // applyStyleDeclaration(element,
                            // styleWithPseudoInstance,
                            // pseudoInstance);
                            applyStyleDeclaration(elt, styleWithPseudoInstance, pseudoInstance);
                        }
                    }
                }
            }

            if (style != null) {
                // applyStyleDeclaration(element, style, null);
                applyStyleDeclaration(elt, style, null);
            }
            try {
                // Apply inline style
                applyInlineStyle(elt, false);
            } catch (Exception e) {
                handleExceptions(e);
            }

            if (applyStylesToChildNodes) {
                /*
                 * Style all children recursive.
                 */
                NodeList nodes = elt instanceof ChildVisibilityAwareElement ? ((ChildVisibilityAwareElement) elt)
                        .getVisibleChildNodes() : elt.getChildNodes();
                if (nodes != null) {
                    for (int k = 0; k < nodes.getLength(); k++) {
                        applyStyles(nodes.item(k), applyStylesToChildNodes);
                    }
                    onStylesAppliedToChildNodes(elt, nodes);
                }
            }
        }

    }

}
