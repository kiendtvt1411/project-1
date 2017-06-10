package dhbkhn.kien.kienmessenger.Model.DangNhap_DangKy;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

/**
 * Created by kiend on 10/16/2016.
 */
public class DangNhapFacebookGoogle {
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

    public AccessToken layTokenFacebookDangNhapHienTai(){
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken;
    }

    public void huyTokenTracker(){
        accessTokenTracker.stopTracking();
    }
}
