package com.poly.smartfindpro.utils.broadcast;

import android.content.BroadcastReceiver;

public abstract class LocalBroadcastReceiver extends BroadcastReceiver {
    /**
     * Flag indicating whether the broadcast has been consumed already.
     * Analogous to mAbortBroadcast.
     */
    protected boolean mConsumeBroadcast;


    /**
     * Returns the flag indicating whether or not this accountNumber should consume  the current broadcast.
     *
     * @return true if the broadcast should be consumed.s
     */
    public final boolean isBroadcastConsumed() {
        return mConsumeBroadcast;
    }

    /**
     * Sets the flag indicating that this accountNumber should consume the current
     * broadcast; only works with broadcasts sent through
     * {@code OrderEnabledLocalBroadcastManager.sendOrderedBroadcast}.
     * <p>
     * This will prevent any other broadcast receivers from receiving the broadcast.
     */
    public final void consumeBroadcast() {
        mConsumeBroadcast = true;
    }

    /**
     * Clears the flag indicating that this accountNumber should consume the current broadcast.
     */
    public final void clearConsumeBroadcast() {
        mConsumeBroadcast = false;
    }
}
