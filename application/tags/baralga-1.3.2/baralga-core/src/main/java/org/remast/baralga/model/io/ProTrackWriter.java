package org.remast.baralga.model.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.remast.baralga.model.ProTrack;
import org.remast.baralga.model.Project;
import org.remast.baralga.model.ProjectActivity;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Writer for ProTrack data files.
 * @author remast
 */
public class ProTrackWriter {

    /** Encoding of output file. */
    private static final String OUTPUT_ENCODING = "UTF-8";

    /** The data to write. */
    private ProTrack data;

    /**
     * Create a write for given data.
     * @param data the data
     */
    public ProTrackWriter(final ProTrack data) {
        this.data = data;
    }

    /**
     * Write the data to the given file.
     * @param file the file to write to
     * @throws IOException on write error
     */
    public final void write(final File file) throws IOException {
        if (file == null) {
            return;
        }

        synchronized (data) {
            final OutputStream fileOut = new FileOutputStream(file);
            try {
                final XStream xstream = new XStream(new DomDriver(OUTPUT_ENCODING));
                xstream.processAnnotations(
                        new Class[] {ProTrack.class, Project.class, ProjectActivity.class}
                );
                xstream.autodetectAnnotations(true);

                xstream.setMode(XStream.ID_REFERENCES);
                xstream.toXML(data, fileOut);
            } finally {
                IOUtils.closeQuietly(fileOut);
            }
        }
    }
}