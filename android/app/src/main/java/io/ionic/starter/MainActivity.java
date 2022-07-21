package io.ionic.starter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.getcapacitor.BridgeActivity;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends BridgeActivity {
  @PluginMethod
  public void register(PluginCall call) {
    final String topicName = call.getString("userToken");
    Log.d("CallKitVoip","register");

    if(topicName == null){
      call.reject("Topic name hasn't been specified correctly");
      return;
    }
    FirebaseMessaging
      .getInstance()
      .subscribeToTopic(topicName)
      .addOnSuccessListener(unused -> {
        JSObject ret = new JSObject();
        Logger.debug("CallKit: Subscribed");
        ret.put("message", "Subscribed to topic " + topicName);
        call.resolve(ret);

      })
      .addOnFailureListener(e -> {
        Logger.debug("CallKit: Cannot subscribe");
        call.reject("Cant subscribe to topic" + topicName);
      });
    call.resolve();
  }
  public void notifyEvent(String eventName, String username, String connectionId){
    Log.d("notifyEvent",eventName + "  " + username + "   " + connectionId);

//        JSObject data = new JSObject();
//        data.put("username",        username);
//        data.put("connectionId",    connectionId);
//        notifyListeners("callAnswered", data);
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FirebaseMessaging.getInstance().getToken()
      .addOnCompleteListener(new OnCompleteListener<String>() {
        @Override
        public void onComplete(@NonNull Task<String> task) {
          if (!task.isSuccessful()) {
//            Log.d("registrationtokenfailed", task.getException());
            return;
          }

          // Get new FCM registration token
          String token = task.getResult();

          System.out.println("token"+token);

          // Log and toast
//          String msg = getString(R.string.msg_token_fmt, token);
//          Log.d("message", msg);
//          Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
      });
  }
}
