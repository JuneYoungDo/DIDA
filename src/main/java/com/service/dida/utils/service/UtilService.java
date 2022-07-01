package com.service.dida.utils.service;

import com.service.dida.exception.BaseException;
import com.service.dida.user.user.User;
import com.service.dida.utils.Bcrypt;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UtilService {
    private final Bcrypt bcrypt;

    public JSONObject parseBody(HttpResponse<String> response) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }

    public String doubleToString(double d) {
        return String.format("%.6f", Double.valueOf(d * 1000000).longValue() / 1000000f);
    }

    public boolean checkPayPwd(User user, String payPwd) throws BaseException {
        if (user.getWallet() == null || bcrypt.isMatch(payPwd, user.getPayPwd()) == false) {
            return false;
        } else {
            return true;
        }
    }
}
