
package org.eclipse.nebula.widgets.nattable.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Shell;

/**
 * Simple IOutputStreamProvider for a given filepath, no user interaction
 * necessary. 
 *
 * Useful for intergration of a fileselector anywere in the UI.
 *
 * @author Uwe Peuker
 * @since 1.5
 */
public final class FilePathOutputStreamProvider implements IOutputStreamProvider {

    private static final Log LOG = LogFactory.getLog(FilePathOutputStreamProvider.class);

    private final String filePath;
    private OutputStream stream = null;

    public FilePathOutputStreamProvider(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Object getResult() {
        if (this.stream == null) {
            return null;
        }

        return new File(this.filePath);
    }

    @Override
    public OutputStream getOutputStream(final Shell shell) {
        try {
            this.stream = new PrintStream(this.filePath);
        } catch (final FileNotFoundException e) {
            FilePathOutputStreamProvider.LOG.error("Failed to open or create the file: " + this.filePath, e); //$NON-NLS-1$
        }

        return this.stream;
    }
}
