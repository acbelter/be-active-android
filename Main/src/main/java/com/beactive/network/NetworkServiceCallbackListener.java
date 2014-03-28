package com.beactive.network;

import android.content.Intent;
import android.os.Bundle;

public interface NetworkServiceCallbackListener {
    void onServiceCallback(int requestId, Intent requestIntent, int resultCode, Bundle data);
}
