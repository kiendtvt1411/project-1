package dhbkhn.kien.kienmessenger.Presenter.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by kiend on 9/30/2016.
 */
public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
//        luuTokenVaoCSDLRieng(token);
    }

//    private void luuTokenVaoCSDLRieng(String token) {
//        new FirebaseIDTask().execute(token);
//    }
}
