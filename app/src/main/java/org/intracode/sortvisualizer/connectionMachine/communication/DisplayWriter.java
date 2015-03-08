package org.intracode.sortvisualizer.connectionMachine.communication;

import android.content.Context;

/**
 * Created by angelvenchev on 3/8/2015.
 */
public class DisplayWriter {
    private LEDMatrixBTConn BT;
    protected static final String REMOTE_BT_DEVICE_NAME = "DIANAKOSTOVA";

    protected static final int X_SIZE = 24;
    protected static final int Y_SIZE = 24;

    protected static final int COLOR_MODE = 0;

    protected static final String APP_NAME = "SortVisualizer";

    public void write(final byte[][] messageFrames, Context context) {
        BT = new LEDMatrixBTConn(context, REMOTE_BT_DEVICE_NAME, X_SIZE, Y_SIZE, COLOR_MODE, APP_NAME);

        if (!BT.prepare() || !BT.checkIfDeviceIsPaired()) {
            return;
        }

        // Start BT sending thread.
        Thread sender = new Thread() {

            int sendDelay;
            public void run() {

                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

                // Try to connect.
                if (!BT.connect()) {
                    BT.closeConnection();
                    return;
                }

                // Connected. Calculate and set send delay from maximum FPS.
                // Negative maxFPS should not happen.
                int maxFPS = BT.getMaxFPS();
                if (maxFPS > 0) {
                    sendDelay = (int) (1000.0 / maxFPS);
                } else {
                    BT.closeConnection();
                    return;
                }

                for(int i = 0; i < messageFrames.length; i++) {
                    for(int k = 0; k < 10; k++) {
                        if (!BT.write(messageFrames[i])) {
                            return;
                        }
                        try {
                            // Delay for a moment.
                            // Note: Delaying the same amount of time every frame will not give you constant FPS.
                            Thread.sleep(sendDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                BT.closeConnection();
            }
        };

        // Start sending thread.
        sender.start();
    }

    public void close() {
        BT.closeConnection();
    }
}
