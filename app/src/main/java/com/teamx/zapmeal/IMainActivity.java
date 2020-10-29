package com.teamx.zapmeal;

import com.teamx.zapmeal.models.Message;
import com.teamx.zapmeal.models.User;


public interface IMainActivity {

    void inflateViewProfileFragment(User user);

    void onMessageSelected(Message message);

    void onBackPressed();
}
