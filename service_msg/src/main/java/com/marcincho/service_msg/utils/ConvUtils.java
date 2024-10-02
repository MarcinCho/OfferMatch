package com.marcincho.service_msg.utils;

import java.util.UUID;

public class ConvUtils {

    public static String getConvId(UUID user1, UUID user2) {
        return user1.compareTo(user2) > 0 ? user1 + "_" + user2 : user2 + "_" + user1;
    }
}
