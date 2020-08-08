package org.techpleid.springframework.insights.logback.test;

import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.spi.DeferredProcessingAware;
import ch.qos.logback.core.status.ErrorStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Appender to support testing. This appender extendes the {@link ConsoleAppender} class and
 * override its functionality to capture final encoded event into the list {@link TestEncodedMessageCaptureConsoleAppender#encodedMessageList}
 * to support easy assertions of capture encoded message.
 *
 * @param <E>
 */
public class TestEncodedMessageCaptureConsoleAppender<E> extends ConsoleAppender<E> {

    public List<String> encodedMessageList = new ArrayList<String>();

    @Override
    public void doAppend(final E eventObject) {
        super.doAppend(eventObject);
    }

    @Override
    protected void subAppend(final E event) {
        {
            if (!isStarted()) {
                return;
            }
            try {
                // this step avoids LBCLASSIC-139
                if (event instanceof DeferredProcessingAware) {
                    ((DeferredProcessingAware) event).prepareForDeferredProcessing();
                }
                // the synchronization prevents the OutputStream from being closed while we
                // are writing. It also prevents multiple threads from entering the same
                // converter. Converters assume that they are in a synchronized block.
                // lock.lock();

                final byte[] byteArray = this.encoder.encode(event);
                final String encodedMessage = new String(byteArray, "UTF-8");
                encodedMessageList.add(encodedMessage);
                writeBytes(byteArray);

            } catch (final IOException ioe) {
                // as soon as an exception occurs, move to non-started state
                // and add a single ErrorStatus to the SM.
                this.started = false;
                addStatus(new ErrorStatus("IO failure in appender", this, ioe));
            }
        }
    }

    @Override
    protected void append(final E eventObject) {

        super.append(eventObject);
    }

    private void writeBytes(final byte[] byteArray) throws IOException {
        if (byteArray == null || byteArray.length == 0)
            return;

        lock.lock();
        try {
            this.getOutputStream().write(byteArray);
            if (isImmediateFlush()) {
                this.getOutputStream().flush();
            }
        } finally {
            lock.unlock();
        }
    }
}
