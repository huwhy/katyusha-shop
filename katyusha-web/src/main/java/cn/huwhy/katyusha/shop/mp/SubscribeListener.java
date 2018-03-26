package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.katyusha.shop.biz.MemberBiz;
import cn.huwhy.wx.sdk.api.UserApi;
import cn.huwhy.wx.sdk.listener.Listener;
import cn.huwhy.wx.sdk.message.Message;
import cn.huwhy.wx.sdk.message.ReplyMsgBuilder;
import cn.huwhy.wx.sdk.message.TextMessage;
import cn.huwhy.wx.sdk.model.Command;
import cn.huwhy.wx.sdk.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscribeListener extends Listener {

    @Autowired
    private MemberBiz memberBiz;
    @Autowired
    private MpConfigUtil mpConfigUtil;

    @Override
    public String handle(Command command) {
        UserInfo userInfo = UserApi.getUserInfo(mpConfigUtil.getAccessToken(), command.getFromUserName());
        memberBiz.saveForMp(mpConfigUtil.toMpUser(userInfo));

        Message message = new TextMessage();
        message.setToUserName(command.getFromUserName());
        message.setFromUserName(command.getToUserName());
        message.setCreateTime(command.getCreateTime());
        message.setContent("欢迎光临！");
        return ReplyMsgBuilder.toXml(message);
    }
}
