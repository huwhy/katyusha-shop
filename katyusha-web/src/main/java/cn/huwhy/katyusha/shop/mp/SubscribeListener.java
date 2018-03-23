package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.common.util.CollectionUtil;
import cn.huwhy.katyusha.shop.biz.MemberBiz;
import cn.huwhy.katyusha.shop.model.MpUser;
import cn.huwhy.wx.sdk.api.UserApi;
import cn.huwhy.wx.sdk.listener.Listener;
import cn.huwhy.wx.sdk.model.Command;
import cn.huwhy.wx.sdk.model.UserInfo;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SubscribeListener extends Listener {

    @Autowired
    private MemberBiz memberBiz;
    @Autowired
    private MpConfigUtil mpConfigUtil;

    @Override
    public String handle(Command command) {
        UserInfo userInfo = UserApi.getUserInfo(mpConfigUtil.getAccessToken(), command.getFromUserName());
        MpUser mpUser = new MpUser();
        mpUser.setOpenId(userInfo.getOpenId());
        mpUser.setUnionId(userInfo.getUnionId());
        mpUser.setSubscribe(userInfo.getSubscribe() != 0);
        mpUser.setNickname(userInfo.getNick());
        mpUser.setSex(userInfo.getSex());
        mpUser.setLanguage(userInfo.getLang());
        mpUser.setCity(userInfo.getCity());
        mpUser.setProvince(userInfo.getProvince());
        mpUser.setCountry(userInfo.getCountry());
        mpUser.setHeadImgUrl(userInfo.getHeadImgUrl());
        mpUser.setSubscribeTime(userInfo.getSubscribeTime());
        mpUser.setRemark(userInfo.getRemark());
        mpUser.setGroupId(userInfo.getGroupid());
        if (CollectionUtil.isNotEmpty(userInfo.getTagIds())) {
            mpUser.setTagIds(Joiner.on(',').join(userInfo.getTagIds()));
        }
        mpUser.setSubscribeScene(userInfo.getSubscribeScene());
        mpUser.setQrScene(userInfo.getQrScene());
        mpUser.setQrSceneStr(userInfo.getQrSceneStr());
        mpUser.setUpdated(new Date());
        memberBiz.saveForMp(mpUser);

        return null;
    }
}
